package com.nhatro.backend.controller;

import com.nhatro.backend.entity.XacThucEkyc;
import com.nhatro.backend.service.XacThucEkycService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Xác thực eKYC", description = "Quản lý xác thực danh tính eKYC")
@RestController
@RequestMapping("/api/xac-thuc-ekyc")
public class XacThucEkycController {

    private final XacThucEkycService ekycService;

    public XacThucEkycController(XacThucEkycService ekycService) {
        this.ekycService = ekycService;
    }

    @GetMapping
    public ResponseEntity<List<XacThucEkyc>> getAll() {
        return ResponseEntity.ok(ekycService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<XacThucEkyc> getById(@PathVariable Integer id) {
        return ekycService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nguoi-dung/{maNguoiDung}")
    public ResponseEntity<List<XacThucEkyc>> getByNguoiDung(@PathVariable Integer maNguoiDung) {
        return ResponseEntity.ok(ekycService.getByNguoiDung(maNguoiDung));
    }

    @PostMapping
    public ResponseEntity<XacThucEkyc> create(@RequestBody XacThucEkyc ekyc) {
        return ResponseEntity.ok(ekycService.create(ekyc));
    }

    @PutMapping("/{id}/duyet")
    public ResponseEntity<XacThucEkyc> duyet(@PathVariable Integer id) {
        return ekycService.duyet(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return ekycService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
