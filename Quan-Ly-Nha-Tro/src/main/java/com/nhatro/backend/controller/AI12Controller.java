package com.nhatro.backend.controller;

import com.nhatro.backend.dto.AI12Response;
import com.nhatro.backend.service.AI12Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ai")
@Tag(name = "AI Admin", description = "Chức năng AI dành cho quản trị viên")
public class AI12Controller {

    private final AI12Service ai12Service;

    public AI12Controller(AI12Service ai12Service) {
        this.ai12Service = ai12Service;
    }

    /**
     * UC-ADMIN-AI-12
     * Dự đoán các bài đăng có tiềm năng thu hút người thuê.
     */
    @Operation(
            summary = "AI dự đoán bài đăng tiềm năng",
            description = "Phân tích lượt xem, yêu thích, giá phòng, diện tích và trạng thái bài đăng để chấm điểm."
    )
    @GetMapping("/potential-posts")
    public ResponseEntity<List<AI12Response>> duDoanBaiDang() {

        return ResponseEntity.ok(
                ai12Service.duDoanBaiDang()
        );
    }
}