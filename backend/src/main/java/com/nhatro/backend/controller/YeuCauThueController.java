package com.nhatro.backend.controller;

import com.nhatro.backend.entity.YeuCauThue;
import com.nhatro.backend.service.YeuCauThueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/yeu-cau-thue")
public class YeuCauThueController {

    private final YeuCauThueService yeuCauThueService;

    public YeuCauThueController(YeuCauThueService yeuCauThueService) {
        this.yeuCauThueService = yeuCauThueService;
    }

    @GetMapping
    public List<YeuCauThue> getAll() {
        return yeuCauThueService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<YeuCauThue> getById(@PathVariable Integer id) {
        return yeuCauThueService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<YeuCauThue> create(@RequestBody YeuCauThue yeuCauThue) {
        return ResponseEntity.ok(yeuCauThueService.create(yeuCauThue));
    }

    @PutMapping("/{id}")
    public ResponseEntity<YeuCauThue> update(@PathVariable Integer id, @RequestBody YeuCauThue yeuCauThue) {
        return yeuCauThueService.update(id, yeuCauThue)
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