package com.nhatro.backend.controller;

import com.nhatro.backend.dto.ProfileAppointmentDto;
import com.nhatro.backend.dto.ProfileResponse;
import com.nhatro.backend.entity.LichHen;
import com.nhatro.backend.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Integer userId, Authentication authentication) {
        return ResponseEntity.ok(profileService.getProfile(userId, authentication));
    }

    @PostMapping("/{userId}/saved-rooms/{roomId}")
    public ResponseEntity<Map<String, Object>> saveRoom(@PathVariable Integer userId, @PathVariable Integer roomId, Authentication authentication) {
        profileService.saveRoom(userId, roomId, authentication);
        return ResponseEntity.ok(Map.of("saved", true, "maPhong", roomId));
    }

    @DeleteMapping("/{userId}/saved-rooms/{roomId}")
    public ResponseEntity<Void> unsaveRoom(@PathVariable Integer userId, @PathVariable Integer roomId, Authentication authentication) {
        profileService.unsaveRoom(userId, roomId, authentication);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/viewed-rooms/{roomId}")
    public ResponseEntity<Map<String, Object>> recordView(@PathVariable Integer userId, @PathVariable Integer roomId, Authentication authentication) {
        profileService.recordView(userId, roomId, authentication);
        return ResponseEntity.ok(Map.of("recorded", true, "maPhong", roomId));
    }

    @PutMapping("/{userId}/appointments/{appointmentId}")
    public ResponseEntity<ProfileAppointmentDto> reschedule(@PathVariable Integer userId,
                                                              @PathVariable Integer appointmentId,
                                                              @RequestBody LichHen input,
                                                              Authentication authentication) {
        return ResponseEntity.ok(profileService.reschedule(userId, appointmentId, input, authentication));
    }

    @DeleteMapping("/{userId}/appointments/{appointmentId}")
    public ResponseEntity<Void> cancel(@PathVariable Integer userId, @PathVariable Integer appointmentId, Authentication authentication) {
        profileService.cancelAppointment(userId, appointmentId, authentication);
        return ResponseEntity.noContent().build();
    }
}
