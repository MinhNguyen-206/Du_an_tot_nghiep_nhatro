package com.nhatro.backend.controller;

import com.nhatro.backend.entity.TinNhan;
import com.nhatro.backend.service.TinNhanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tin nhắn", description = "Quản lý tin nhắn giữa người dùng")
@RestController
@RequestMapping("/api/tin-nhan")
public class TinNhanController {

    private final TinNhanService tinNhanService;

    public TinNhanController(TinNhanService tinNhanService) {
        this.tinNhanService = tinNhanService;
    }

    @GetMapping
    public List<TinNhan> getAll() {
        return tinNhanService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TinNhan> getById(@PathVariable Long id) {
        return tinNhanService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TinNhan> create(@RequestBody TinNhan tinNhan) {
        return ResponseEntity.ok(tinNhanService.create(tinNhan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TinNhan> update(@PathVariable Long id, @RequestBody TinNhan tinNhan) {
        return tinNhanService.update(id, tinNhan)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return tinNhanService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}