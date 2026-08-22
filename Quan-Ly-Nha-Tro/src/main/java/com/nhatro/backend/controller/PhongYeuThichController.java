package com.nhatro.backend.controller;

import com.nhatro.backend.dto.PhongCardDto;
import com.nhatro.backend.entity.PhongYeuThich;
import com.nhatro.backend.repository.NguoiDungRepository;
import com.nhatro.backend.repository.PhongTroRepository;
import com.nhatro.backend.repository.PhongYeuThichRepository;
import com.nhatro.backend.service.PhongCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/phong-yeu-thich")
public class PhongYeuThichController {
    private final PhongYeuThichRepository repository;
    private final NguoiDungRepository nguoiDungRepository;
    private final PhongTroRepository phongTroRepository;
    private final PhongCardService phongCardService;

    public PhongYeuThichController(PhongYeuThichRepository repository, NguoiDungRepository nguoiDungRepository,
                                   PhongTroRepository phongTroRepository, PhongCardService phongCardService) {
        this.repository = repository;
        this.nguoiDungRepository = nguoiDungRepository;
        this.phongTroRepository = phongTroRepository;
        this.phongCardService = phongCardService;
    }

    @GetMapping("/nguoi-dung/{userId}")
    public ResponseEntity<List<PhongCardDto>> getMine(@PathVariable Integer userId, Authentication auth) {
        checkSelf(userId, auth);
        return ResponseEntity.ok(repository.findByNguoiDung_MaNguoiDungOrderByNgayLuuDesc(userId).stream()
                .map(x -> phongCardService.toCard(x.getPhong(), x.getNgayLuu())).toList());
    }

    @PostMapping("/nguoi-dung/{userId}/phong/{roomId}")
    public ResponseEntity<PhongCardDto> save(@PathVariable Integer userId, @PathVariable Integer roomId, Authentication auth) {
        checkSelf(userId, auth);
        PhongYeuThich item = repository.findByNguoiDung_MaNguoiDungAndPhong_MaPhong(userId, roomId).orElseGet(() -> {
            var user = nguoiDungRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            var room = phongTroRepository.findById(roomId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return repository.save(PhongYeuThich.builder().nguoiDung(user).phong(room).build());
        });
        return ResponseEntity.ok(phongCardService.toCard(item.getPhong(), item.getNgayLuu()));
    }

    @DeleteMapping("/nguoi-dung/{userId}/phong/{roomId}")
    public ResponseEntity<Void> delete(@PathVariable Integer userId, @PathVariable Integer roomId, Authentication auth) {
        checkSelf(userId, auth);
        repository.findByNguoiDung_MaNguoiDungAndPhong_MaPhong(userId, roomId).ifPresent(repository::delete);
        return ResponseEntity.noContent().build();
    }

    private void checkSelf(Integer userId, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        boolean admin = auth.getAuthorities().stream().anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()) || "ADMIN".equals(a.getAuthority()));
        if (!admin && nguoiDungRepository.findById(userId).map(u -> u.getEmail() == null || !u.getEmail().equalsIgnoreCase(auth.getName())).orElse(true)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
