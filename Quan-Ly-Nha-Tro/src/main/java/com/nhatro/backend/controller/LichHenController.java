package com.nhatro.backend.controller;

import com.nhatro.backend.dto.DatLichXemPhongRequest;
import com.nhatro.backend.dto.DatLichXemPhongResponse;
import com.nhatro.backend.entity.LichHen;
import com.nhatro.backend.service.LichHenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lịch hẹn", description = "Quản lý lịch hẹn xem phòng")
@RestController
@RequestMapping("/api/lich-hen")
public class LichHenController {

    private final LichHenService lichHenService;

    public LichHenController(LichHenService lichHenService) {
        this.lichHenService = lichHenService;
    }

    @GetMapping
    public ResponseEntity<List<LichHen>> getAll() {
        return ResponseEntity.ok(lichHenService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichHen> getById(@PathVariable Integer id) {
        return lichHenService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nguoi-dung/{maNguoiDung}")
    public ResponseEntity<List<LichHen>> getByNguoiDung(@PathVariable Integer maNguoiDung) {
        return ResponseEntity.ok(lichHenService.getByNguoiDung(maNguoiDung));
    }

    @GetMapping("/phong/{maPhong}")
    public ResponseEntity<List<LichHen>> getByPhong(@PathVariable Integer maPhong) {
        return ResponseEntity.ok(lichHenService.getByPhong(maPhong));
    }

    /**
     * Người tìm trọ đặt lịch xem phòng từ trang chi tiết phòng.
     * Người dùng được lấy từ JWT, không tin maNguoiDung do client gửi lên.
     */
    @PostMapping("/dat-lich")
    public ResponseEntity<DatLichXemPhongResponse> datLich(
            @RequestBody DatLichXemPhongRequest request,
            Authentication authentication) {
        return ResponseEntity.ok(lichHenService.datLichXemPhong(request, authentication));
    }

    @PostMapping
    public ResponseEntity<LichHen> create(@RequestBody LichHen lichHen) {
        return ResponseEntity.ok(lichHenService.create(lichHen));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichHen> update(@PathVariable Integer id, @RequestBody LichHen duLieuMoi) {
        return lichHenService.update(id, duLieuMoi)
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
