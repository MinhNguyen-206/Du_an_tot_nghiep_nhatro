package com.nhatro.backend.controller;

import com.nhatro.backend.entity.BoDieuKhienAi;
import com.nhatro.backend.service.BoDieuKhienAiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AI", description = "Bộ điều khiển AI")
@RestController
@RequestMapping("/api/bo-dieu-khien-ai")
public class BoDieuKhienAiController {

    private final BoDieuKhienAiService boDieuKhienAiService;

    public BoDieuKhienAiController(BoDieuKhienAiService boDieuKhienAiService) {
        this.boDieuKhienAiService = boDieuKhienAiService;
    }

    @GetMapping
    public List<BoDieuKhienAi> getAll() {
        return boDieuKhienAiService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoDieuKhienAi> getById(@PathVariable Integer id) {
        return boDieuKhienAiService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BoDieuKhienAi> create(@RequestBody BoDieuKhienAi boDieuKhienAi) {
        return ResponseEntity.ok(boDieuKhienAiService.create(boDieuKhienAi));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoDieuKhienAi> update(@PathVariable Integer id, @RequestBody BoDieuKhienAi boDieuKhienAi) {
        return boDieuKhienAiService.update(id, boDieuKhienAi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return boDieuKhienAiService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
