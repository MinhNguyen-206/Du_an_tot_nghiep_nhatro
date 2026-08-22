package com.nhatro.backend.controller;

import com.nhatro.backend.entity.PhongTro;
import com.nhatro.backend.service.PhongTroService;
import com.nhatro.backend.service.PhongCardService;
import com.nhatro.backend.dto.PhongCardDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Phòng trọ", description = "Quản lý phòng trọ")
@RestController
@RequestMapping("/api/phong-tro")
public class PhongTroController {

    private final PhongTroService phongTroService;
    private final PhongCardService phongCardService;

    public PhongTroController(PhongTroService phongTroService, PhongCardService phongCardService) {
        this.phongTroService = phongTroService;
        this.phongCardService = phongCardService;
    }

    @GetMapping
    public ResponseEntity<List<PhongTro>> getAll() {
        return ResponseEntity.ok(phongTroService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhongTro> getById(@PathVariable Integer id) {
        return phongTroService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/card")
    public ResponseEntity<PhongCardDto> getCard(@PathVariable Integer id) {
        return phongTroService.getById(id)
                .map(room -> ResponseEntity.ok(phongCardService.toCard(room, null)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nha-tro/{maNhaTro}")
    public ResponseEntity<List<PhongTro>> getByNhaTro(@PathVariable Integer maNhaTro) {
        return ResponseEntity.ok(phongTroService.getByNhaTro(maNhaTro));
    }

    @GetMapping("/trong")
    public ResponseEntity<List<PhongTro>> getPhongTrong() {
        return ResponseEntity.ok(phongTroService.getByTrangThai(true));
    }

    @PostMapping
    public ResponseEntity<PhongTro> create(@RequestBody PhongTro phongTro) {
        return ResponseEntity.ok(phongTroService.create(phongTro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhongTro> update(@PathVariable Integer id, @RequestBody PhongTro duLieuMoi) {
        return phongTroService.update(id, duLieuMoi)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return phongTroService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}