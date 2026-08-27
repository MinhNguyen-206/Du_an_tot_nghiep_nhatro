package com.nhatro.backend.controller;

import com.nhatro.backend.entity.NhatKyHoatDong;
import com.nhatro.backend.service.NhatKyHoatDongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin - Nhat ky", description = "Nhat ky hoat dong he thong")
@RestController
@RequestMapping("/api/admin/nhat-ky")
public class AdminNhatKyController {

    private final NhatKyHoatDongService nhatKyService;

    public AdminNhatKyController(NhatKyHoatDongService nhatKyService) {
        this.nhatKyService = nhatKyService;
    }

    @Operation(summary = "Danh sach nhat ky hoat dong co phan trang")
    @GetMapping
    public ResponseEntity<Page<NhatKyHoatDong>> danhSach(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(nhatKyService.getAllPaged(pageable));
    }

    @Operation(summary = "Chi tiet nhat ky")
    @GetMapping("/{id}")
    public ResponseEntity<NhatKyHoatDong> chiTiet(@PathVariable Integer id) {
        return nhatKyService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
