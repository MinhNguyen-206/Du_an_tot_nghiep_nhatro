package com.nhatro.backend.controller;

import com.nhatro.backend.entity.XacThucOtp;
import com.nhatro.backend.service.XacThucOtpService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OTP", description = "Xác thực bằng OTP")
@RestController
@RequestMapping("/api/xac-thuc-otp")
public class XacThucOtpController {

    private final XacThucOtpService xacThucOtpService;

    public XacThucOtpController(XacThucOtpService xacThucOtpService) {
        this.xacThucOtpService = xacThucOtpService;
    }

    @GetMapping
    public List<XacThucOtp> getAll() {
        return xacThucOtpService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<XacThucOtp> getById(@PathVariable Integer id) {
        return xacThucOtpService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<XacThucOtp> create(@RequestBody XacThucOtp xacThucOtp) {
        return ResponseEntity.ok(xacThucOtpService.create(xacThucOtp));
    }

    @PutMapping("/{id}")
    public ResponseEntity<XacThucOtp> update(@PathVariable Integer id, @RequestBody XacThucOtp xacThucOtp) {
        return xacThucOtpService.update(id, xacThucOtp)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return xacThucOtpService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
