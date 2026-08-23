package com.nhatro.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Du lieu gui len tu form "Lien he" (trang /lien-he).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {
    private String hoTen;
    private String email;
    private String noiDung;
}
