package com.nhatro.backend.controller;

import com.nhatro.backend.entity.NhaTro;
import com.nhatro.backend.entity.NguoiDung;
import com.nhatro.backend.service.NhaTroService;
import com.nhatro.backend.service.NguoiDungService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nha-tro")
public class NhaTroApiController {

    private final NhaTroService nhaTroService;
    private final NguoiDungService nguoiDungService;

    private static final String DEMO_EMAIL =
            "chutro1@nhatro.vn";

    public NhaTroApiController(
            NhaTroService nhaTroService,
            NguoiDungService nguoiDungService
    ) {
        this.nhaTroService = nhaTroService;
        this.nguoiDungService = nguoiDungService;
    }

    // =========================================================
    // GET NHÀ TRỌ CỦA TÔI
    // =========================================================

    @GetMapping("/mine")
    public ResponseEntity<?> getMine(
            HttpSession session
    ) {

        try {

            NguoiDung owner =
                    currentOwner(session);

            List<NhaTro> list =
                    nhaTroService.getByNguoiDung(
                            owner.getMaNguoiDung()
                    );

            return ResponseEntity.ok(
                    list.stream()
                            .map(this::toResponse)
                            .toList()
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }

    // =========================================================
    // GET THEO ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Integer id
    ) {

        return nhaTroService
                .getById(id)
                .map(nt ->
                        ResponseEntity.ok(
                                toResponse(nt)
                        )
                )
                .orElseGet(() ->
                        ResponseEntity
                                .notFound()
                                .build()
                );
    }

    // =========================================================
    // THÊM
    // =========================================================

    @PostMapping("/mine")
    public ResponseEntity<?> create(
            @RequestBody NhaTroService.NhaTroRequest request,
            HttpSession session
    ) {

        try {

            NguoiDung owner =
                    currentOwner(session);

            NhaTro nhaTro =
                    nhaTroService.createForOwner(
                            request,
                            owner
                    );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            toResponse(nhaTro)
                    );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Map.of(
                                    "message",
                                    "Không thể thêm nhà trọ: "
                                            + e.getMessage()
                            )
                    );
        }
    }

    // =========================================================
    // SỬA
    // =========================================================

    @PutMapping("/mine/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,

            @RequestBody
            NhaTroService.NhaTroRequest request,

            HttpSession session
    ) {

        try {

            NguoiDung owner =
                    currentOwner(session);

            NhaTro nhaTro =
                    nhaTroService.updateForOwner(
                            id,
                            request,
                            owner
                    );

            return ResponseEntity.ok(
                    toResponse(nhaTro)
            );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "message",
                                    e.getMessage()
                            )
                    );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Map.of(
                                    "message",
                                    "Không thể sửa nhà trọ: "
                                            + e.getMessage()
                            )
                    );
        }
    }

    // =========================================================
    // XÓA
    // =========================================================

    @DeleteMapping("/mine/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Integer id,
            HttpSession session
    ) {

        try {

            NguoiDung owner =
                    currentOwner(session);

            nhaTroService.deleteForOwner(
                    id,
                    owner
            );

            return ResponseEntity.ok(
                    Map.of(
                            "success",
                            true,
                            "message",
                            "Xóa nhà trọ thành công."
                    )
            );

        } catch (IllegalStateException e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            Map.of(
                                    "success",
                                    false,
                                    "message",
                                    e.getMessage()
                            )
                    );

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(
                            Map.of(
                                    "success",
                                    false,
                                    "message",
                                    e.getMessage()
                            )
                    );
        }
    }

    // =========================================================
    // LẤY CHỦ TRỌ ĐANG ĐĂNG NHẬP
    // =========================================================

    private NguoiDung currentOwner(
            HttpSession session
    ) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = null;

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getName() != null
                && !"anonymousUser"
                .equals(authentication.getName())) {

            email =
                    authentication.getName();
        }

        if (email == null || email.isBlank()) {

            Object sessionEmail =
                    session.getAttribute(
                            "landlordEmail"
                    );

            if (sessionEmail != null) {
                email = sessionEmail.toString();
            }
        }

        if (email == null || email.isBlank()) {
            email = DEMO_EMAIL;
        }

        NguoiDung user =
                nguoiDungService
                        .getByEmail(email)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Không tìm thấy tài khoản chủ trọ."
                                )
                        );

        if (user.getVaiTro() == null
                || user.getVaiTro()
                .getMaVaiTro() == null
                || user.getVaiTro()
                .getMaVaiTro() != 2) {

            throw new IllegalArgumentException(
                    "Tài khoản hiện tại không phải chủ trọ."
            );
        }

        session.setAttribute(
                "landlordEmail",
                user.getEmail()
        );

        return user;
    }

    // =========================================================
    // RESPONSE
    // =========================================================

    private Map<String, Object> toResponse(
            NhaTro nt
    ) {

        return Map.ofEntries(

                Map.entry(
                        "maNhaTro",
                        nt.getMaNhaTro()
                ),

                Map.entry(
                        "maNguoiDung",
                        nt.getNguoiDung()
                                .getMaNguoiDung()
                ),

                Map.entry(
                        "tenNhaTro",
                        value(nt.getTenNhaTro())
                ),

                Map.entry(
                        "diaChi",
                        value(nt.getDiaChi())
                ),

                Map.entry(
                        "soSao",
                        nt.getSoSao() == null
                                ? 0
                                : nt.getSoSao()
                ),

                Map.entry(
                        "giaPhong",
                        nt.getGiaPhong() == null
                                ? 0
                                : nt.getGiaPhong()
                ),

                Map.entry(
                        "loaiPhong",
                        value(nt.getLoaiPhong())
                ),

                Map.entry(
                        "moTa",
                        value(nt.getMoTa())
                ),

                Map.entry(
                        "hinhAnh",
                        value(nt.getHinhAnh())
                )
        );
    }

    private String value(String value) {
        return value == null ? "" : value;
    }
}