package com.nhatro.backend.controller;

import com.nhatro.backend.service.GeminiAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final GeminiAiService geminiAiService;

    public AiController(GeminiAiService geminiAiService) {
        this.geminiAiService = geminiAiService;
    }

    @GetMapping("/test")
    public ResponseEntity<?> testAI(
            @RequestParam(defaultValue = "Xin chào Gemini, hãy giới thiệu ngắn gọn về bạn.") String prompt
    ) {

        try {

            String result = geminiAiService.generate(prompt);

            return ResponseEntity.ok(
                    Map.of(
                            "success", true,
                            "prompt", prompt,
                            "result", result
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.internalServerError()
                    .body(
                            Map.of(
                                    "success", false,
                                    "message", e.getMessage()
                            )
                    );
        }
    }
}