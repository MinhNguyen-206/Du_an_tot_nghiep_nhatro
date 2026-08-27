package com.nhatro.backend.controller;

import com.nhatro.backend.entity.XacThucEkyc;
import com.nhatro.backend.service.XacThucEkycService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Admin - eKYC", description = "Quan ly xac thuc eKYC")
@RestController
@RequestMapping("/api/admin/ekyc")
public class AdminEkycController {

    private final XacThucEkycService ekycService;

    public AdminEkycController(XacThucEkycService ekycService) {
        this.ekycService = ekycService;
    }

    @Operation(summary = "Danh sach ho so eKYC co phan trang")
    @GetMapping
    public ResponseEntity<Page<XacThucEkyc>> danhSach(
            @RequestParam(value = "trangThai", required = false) String trangThai,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<XacThucEkyc> all = (trangThai == null || trangThai.isBlank())
                ? ekycService.getAll()
                : ekycService.getAll().stream()
                    .filter(e -> trangThai.equals(e.getTrangThai()))
                    .toList();
        int start = (int) pageable.getOffset();
        int end   = Math.min(start + pageable.getPageSize(), all.size());
        List<XacThucEkyc> pageContent = (start > all.size()) ? List.of() : all.subList(start, end);
        return ResponseEntity.ok(new PageImpl<>(pageContent, pageable, all.size()));
    }

    @Operation(summary = "Chi tiet ho so eKYC")
    @GetMapping("/{id}")
    public ResponseEntity<XacThucEkyc> chiTiet(@PathVariable Integer id) {
        return ekycService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Xac minh eKYC")
    @PutMapping("/{id}/duyet")
    public ResponseEntity<?> duyet(@PathVariable Integer id) {
        return ekycService.duyet(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Tu choi eKYC voi ly do")
    @PutMapping("/{id}/tu-choi")
    public ResponseEntity<?> tuChoi(@PathVariable Integer id,
                                    @RequestBody(required = false) Map<String, String> body) {
        String lyDo = body != null ? body.get("lyDo") : null;
        return ekycService.tuChoi(id, lyDo)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Dem ho so theo trang thai")
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> count() {
        return ResponseEntity.ok(Map.of(
                "choDuyet", ekycService.countPending(),
                "tatCa",    (long) ekycService.getAll().size()
        ));
    }
}
