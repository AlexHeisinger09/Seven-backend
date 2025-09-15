package com.muellespenco.seven_backend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    
    @Value("${app.jwt.expiration}")
    private int jwtExpirationMs;
    
    /**
     * Generar token JWT para un usuario
     */
    public String generateToken(String username, Integer usuCod, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("usuCod", usuCod);
        claims.put("email", email);
        return createToken(claims, username);
    }
    
    /**
     * Crear token con claims personalizados
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }
    
    /**
     * Obtener username del token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * Obtener fecha de expiración del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * Obtener usuario código del token
     */
    public Integer extractUsuCod(String token) {
        return extractClaim(token, claims -> claims.get("usuCod", Integer.class));
    }
    
    /**
     * Obtener email del token
     */
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }
    
    /**
     * Extraer cualquier claim del token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Obtener todos los claims del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    /**
     * Verificar si el token ha expirado
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * Validar token
     */
    public Boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Validar token sin username específico
     */
    public Boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Obtener tiempo de expiración en segundos
     */
    public Long getExpirationTimeInSeconds() {
        return (long) (jwtExpirationMs / 1000);
    }
    
    /**
     * Obtener clave de firma
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    /**
     * Extraer token del header Authorization
     */
    public String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
    
    /**
     * Verificar si el token es válido y obtener información
     */
    public boolean isValidToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            System.err.println("Token expirado: " + e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            System.err.println("Token no soportado: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            System.err.println("Token malformado: " + e.getMessage());
            return false;
        } catch (SecurityException | IllegalArgumentException e) {
            System.err.println("Token inválido: " + e.getMessage());
            return false;
        }
    }
}