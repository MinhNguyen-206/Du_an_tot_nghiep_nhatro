package com.nhatro.backend.controller;

import com.nhatro.backend.entity.LichSuXemPhong;
import com.nhatro.backend.service.LichSuXemPhongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lich-su-xem-phong")
public class LichSuXemPhongController {

    private final LichSuXemPhongService lichSuXemPhongService;

    public LichSuXemPhongController(LichSuXemPhongService lichSuXemPhongService) {
        this.lichSuXemPhongService = lichSuXemPhongService;
    }

    @GetMapping
    public List<LichSuXemPhong> getAll() {
        return lichSuXemPhongService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichSuXemPhong> getById(@PathVariable Integer id) {
        return lichSuXemPhongService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LichSuXemPhong> create(@RequestBody LichSuXemPhong lichSuXemPhong) {
        return ResponseEntity.ok(lichSuXemPhongService.create(lichSuXemPhong));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichSuXemPhong> update(@PathVariable Integer id, @RequestBody LichSuXemPhong lichSuXemPhong) {
        return lichSuXemPhongService.update(id, lichSuXemPhong)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return lichSuXemPhongService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
