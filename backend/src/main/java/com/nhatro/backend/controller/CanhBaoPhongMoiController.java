package com.nhatro.backend.controller;

import com.nhatro.backend.entity.CanhBaoPhongMoi;
import com.nhatro.backend.service.CanhBaoPhongMoiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cảnh báo phòng", description = "Quản lý cảnh báo phòng mới")
@RestController
@RequestMapping("/api/canh-bao-phong-moi")
public class CanhBaoPhongMoiController {

    private final CanhBaoPhongMoiService canhBaoPhongMoiService;

    public CanhBaoPhongMoiController(CanhBaoPhongMoiService canhBaoPhongMoiService) {
        this.canhBaoPhongMoiService = canhBaoPhongMoiService;
    }

    @GetMapping
    public List<CanhBaoPhongMoi> getAll() {
        return canhBaoPhongMoiService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CanhBaoPhongMoi> getById(@PathVariable Integer id) {
        return canhBaoPhongMoiService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CanhBaoPhongMoi> create(@RequestBody CanhBaoPhongMoi canhBao) {
        return ResponseEntity.ok(canhBaoPhongMoiService.create(canhBao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CanhBaoPhongMoi> update(@PathVariable Integer id, @RequestBody CanhBaoPhongMoi canhBao) {
        return canhBaoPhongMoiService.update(id, canhBao)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return canhBaoPhongMoiService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
