package com.nhatro.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private static final long EXPIRATION_MS = 86400000;
    // Token "quen mat khau" song rat ngan (15 phut) va co claim rieng "purpose"
    // de KHONG the dung nham lam token dang nhap thong thuong.
    private static final long RESET_EXPIRATION_MS = 15 * 60 * 1000;
    private static final String RESET_PURPOSE = "reset_password";
    private static final String SECRET = "nhatro-secret-key-2026-very-long-password";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String email, Integer role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(KEY)
                .compact();
    }

    // Giai ma va kiem tra chu ky/han su dung cua token.
    // Nem JwtException (het han, sai chu ky, sai dinh dang...) neu token khong hop
    // le.
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmailFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public Integer getRoleFromToken(String token) {
        return parseClaims(token).get("role", Integer.class);
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ===================== Token QUEN MAT KHAU =====================

    public String generateResetToken(String email) {
        return Jwts.builder()
                .subject(email)
                .claim("purpose", RESET_PURPOSE)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + RESET_EXPIRATION_MS))
                .signWith(KEY)
                .compact();
    }

    // Tra ve email neu token hop le, con han va DUNG muc dich reset mat khau.
    // Nem JwtException neu token het han/sai chu ky/khong dung muc dich.
    public String getResetEmailFromToken(String token) {
        Claims claims = parseClaims(token);
        String purpose = claims.get("purpose", String.class);
        if (!RESET_PURPOSE.equals(purpose)) {
            throw new JwtException("Token khong dung muc dich khoi phuc mat khau");
        }
        return claims.getSubject();
    }
}