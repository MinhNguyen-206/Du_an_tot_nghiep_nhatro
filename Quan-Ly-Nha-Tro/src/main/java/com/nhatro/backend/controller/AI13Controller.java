package com.nhatro.backend.controller;

import com.nhatro.backend.dto.admin.AdminAiDtos;
import com.nhatro.backend.service.AI13Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ai")
@Tag(
        name = "AI Admin",
        description = "Chức năng AI dành cho quản trị viên"
)
public class AI13Controller {

    private final AI13Service ai13Service;

    public AI13Controller(
            AI13Service ai13Service
    ) {
        this.ai13Service = ai13Service;
    }

    /**
     * UC-ADMIN-AI-13
     * AI gợi ý khu vực hot.
     */
    @Operation(
            summary = "AI gợi ý khu vực hot",
            description = "Phân tích lượt xem, lượt yêu thích và số phòng để xác định khu vực có nhu cầu cao."
    )
    @GetMapping("/hot-areas")
    public ResponseEntity<List<AdminAiDtos.HotArea>> goiYKhuVucHot() {

        return ResponseEntity.ok(
                ai13Service.goiYKhuVucHot()
        );
    }
}