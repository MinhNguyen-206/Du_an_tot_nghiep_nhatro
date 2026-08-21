package com.nhatro.backend.dto;

import com.nhatro.backend.entity.NguoiDung;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type;
    private NguoiDung user;

    public AuthResponse(String token, NguoiDung user) {
        this.token = token;
        this.type = "Bearer";
        this.user = user;
    }
}
