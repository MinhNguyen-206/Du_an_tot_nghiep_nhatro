package com.nhatro.backend.controller;

import com.nhatro.backend.entity.LichHen;
import com.nhatro.backend.service.LichHenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lich-hen")
public class LichHenController {

    private final LichHenService lichHenService;

    public LichHenController(LichHenService lichHenService) {
        this.lichHenService = lichHenService;
    }

    @GetMapping
    public List<LichHen> getAll() {
        return lichHenService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichHen> getById(@PathVariable Integer id) {
        return lichHenService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LichHen> create(@RequestBody LichHen lichHen) {
        return ResponseEntity.ok(lichHenService.create(lichHen));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichHen> update(@PathVariable Integer id, @RequestBody LichHen lichHen) {
        return lichHenService.update(id, lichHen)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return lichHenService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}