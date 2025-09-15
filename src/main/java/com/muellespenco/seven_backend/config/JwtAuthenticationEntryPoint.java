package com.muellespenco.seven_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muellespenco.seven_backend.dto.ApiResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void commence(HttpServletRequest request, 
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException, ServletException {
        
        // Configurar respuesta
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // Determinar el mensaje de error
        String message = determineErrorMessage(request, authException);
        String errorCode = determineErrorCode(request, authException);
        
        // Crear respuesta de error
        ApiResponseDto<Void> errorResponse = ApiResponseDto.error(errorCode, message);
        
        // Escribir respuesta JSON
        response.getOutputStream().println(objectMapper.writeValueAsString(errorResponse));
    }
    
    private String determineErrorMessage(HttpServletRequest request, AuthenticationException ex) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "Token de autorización requerido";
        }
        
        if (authHeader.equals("Bearer ")) {
            return "Token de autorización vacío";
        }
        
        // Si hay token pero es inválido
        return "Token de autorización inválido o expirado";
    }
    
    private String determineErrorCode(HttpServletRequest request, AuthenticationException ex) {
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return "TOKEN_REQUERIDO";
        }
        
        if (authHeader.equals("Bearer ")) {
            return "TOKEN_VACIO";
        }
        
        return "TOKEN_INVALIDO";
    }
}