package com.nhatro.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class GeminiAiService {

    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    @Value("${gemini.api-key:}")
    private String apiKey;

    @Value("${gemini.model:gemini-2.5-flash}")
    private String model;

    public GeminiAiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();
    }

    /**
     * Gửi prompt tới Gemini và lấy text trả về.
     */
    public String generate(String prompt) {

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Chưa cấu hình GEMINI_API_KEY. " +
                    "Hãy tạo API key và đặt biến môi trường GEMINI_API_KEY."
            );
        }

        try {

            Map<String, Object> body = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "parts",
                                    List.of(
                                            Map.of("text", prompt)
                                    )
                            )
                    ),

                    "generationConfig", Map.of(
                            "temperature", 0.2,
                            "maxOutputTokens", 3000
                    )
            );

            String json = objectMapper.writeValueAsString(body);

            String url =
                    "https://generativelanguage.googleapis.com/v1beta/models/"
                            + model
                            + ":generateContent";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(60))
                    .header("Content-Type", "application/json")
                    .header("x-goog-api-key", apiKey)
                    .POST(
                            HttpRequest.BodyPublishers.ofString(
                                    json,
                                    StandardCharsets.UTF_8
                            )
                    )
                    .build();

            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString(
                                    StandardCharsets.UTF_8
                            )
                    );

            if (response.statusCode() < 200 ||
                    response.statusCode() >= 300) {

                throw new IllegalStateException(
                        "Gemini API lỗi HTTP "
                                + response.statusCode()
                                + ": "
                                + response.body()
                );
            }

            JsonNode root =
                    objectMapper.readTree(response.body());

            JsonNode text =
                    root.path("candidates")
                            .path(0)
                            .path("content")
                            .path("parts")
                            .path(0)
                            .path("text");

            if (text.isMissingNode() ||
                    text.asText().isBlank()) {

                throw new IllegalStateException(
                        "Gemini không trả về nội dung."
                );
            }

            return text.asText();

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new IllegalStateException(
                    "Yêu cầu Gemini bị gián đoạn.",
                    e
            );

        } catch (Exception e) {

            if (e instanceof IllegalStateException ise) {
                throw ise;
            }

            throw new IllegalStateException(
                    "Không gọi được Gemini AI: "
                            + e.getMessage(),
                    e
            );
        }
    }

    /**
     * Gọi Gemini và yêu cầu trả về JSON.
     */
    public JsonNode generateJson(String prompt) {

        String text = generate(prompt);

        String json = cleanJson(text);

        try {

            return objectMapper.readTree(json);

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Gemini trả về JSON không hợp lệ: "
                            + text,
                    e
            );
        }
    }

    /**
     * Loại bỏ ```json ... ``` nếu Gemini trả về markdown.
     */
    private String cleanJson(String text) {

        String s = text == null
                ? ""
                : text.trim();

        if (s.startsWith("```json")) {
            s = s.substring(7).trim();

        } else if (s.startsWith("```")) {
            s = s.substring(3).trim();
        }

        if (s.endsWith("```")) {
            s = s.substring(
                    0,
                    s.length() - 3
            ).trim();
        }

        int objectStart = s.indexOf('{');
        int arrayStart = s.indexOf('[');

        int start;

        if (objectStart < 0) {
            start = arrayStart;

        } else if (arrayStart < 0) {
            start = objectStart;

        } else {
            start = Math.min(
                    objectStart,
                    arrayStart
            );
        }

        int objectEnd = s.lastIndexOf('}');
        int arrayEnd = s.lastIndexOf(']');

        int end = Math.max(
                objectEnd,
                arrayEnd
        );

        if (start >= 0 && end > start) {

            return s.substring(
                    start,
                    end + 1
            ).trim();
        }

        return s;
    }
}