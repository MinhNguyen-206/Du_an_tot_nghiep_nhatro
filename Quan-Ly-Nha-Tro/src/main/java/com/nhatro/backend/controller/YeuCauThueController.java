package com.nhatro.backend.controller;

import com.nhatro.backend.entity.YeuCauThue;
import com.nhatro.backend.service.YeuCauThueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Yêu cầu thuê", description = "Quản lý yêu cầu thuê phòng")
@RestController
@RequestMapping("/api/yeu-cau-thue")
public class YeuCauThueController {

    private final YeuCauThueService yeuCauThueService;

    public YeuCauThueController(YeuCauThueService yeuCauThueService) {
        this.yeuCauThueService = yeuCauThueService;
    }

    @GetMapping
    public ResponseEntity<List<YeuCauThue>> getAll() {
        return ResponseEntity.ok(yeuCauThueService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<YeuCauThue> getById(@PathVariable Integer id) {
        return yeuCauThueService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nguoi-thue/{maNguoiDung}")
    public ResponseEntity<List<YeuCauThue>> getByNguoiThue(@PathVariable Integer maNguoiDung) {
        return ResponseEntity.ok(yeuCauThueService.getByNguoiThue(maNguoiDung));
    }

    @GetMapping("/phong/{maPhong}")
    public ResponseEntity<List<YeuCauThue>> getByPhong(@PathVariable Integer maPhong) {
        return ResponseEntity.ok(yeuCauThueService.getByPhong(maPhong));
    }

    @PostMapping
    public ResponseEntity<YeuCauThue> create(@RequestBody YeuCauThue yeuCauThue) {
        return ResponseEntity.ok(yeuCauThueService.create(yeuCauThue));
    }

    @PutMapping("/{id}")
    public ResponseEntity<YeuCauThue> update(@PathVariable Integer id, @RequestBody YeuCauThue duLieuMoi) {
        return yeuCauThueService.update(id, duLieuMoi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return yeuCauThueService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}