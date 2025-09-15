package com.muellespenco.seven_backend.controller;

import com.muellespenco.seven_backend.config.JwtUtil;
import com.muellespenco.seven_backend.dto.ApiResponseDto;
import com.muellespenco.seven_backend.dto.LoginRequestDto;
import com.muellespenco.seven_backend.dto.LoginResponseDto;
import com.muellespenco.seven_backend.dto.UsuarioResponseDto;
import com.muellespenco.seven_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    /**
     * Endpoint de login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto loginRequest) {
        
        try {
            LoginResponseDto loginResponse = usuarioService.authenticate(loginRequest);
            
            if (loginResponse.getToken() != null) {
                // Login exitoso
                ApiResponseDto<LoginResponseDto> response = ApiResponseDto.success(
                    loginResponse, 
                    "Login exitoso"
                );
                return ResponseEntity.ok(response);
            } else {
                // Login fallido
                ApiResponseDto<LoginResponseDto> response = ApiResponseDto.error(
                    "CREDENCIALES_INVALIDAS",
                    loginResponse.getMessage()
                );
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            ApiResponseDto<LoginResponseDto> response = ApiResponseDto.error(
                "ERROR_LOGIN",
                "Error interno durante el login"
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Endpoint para validar token
     */
    @PostMapping("/validate")
    public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> validateToken(
            @RequestHeader("Authorization") String authHeader) {
        
        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);
            
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDto.error("TOKEN_REQUERIDO", "Token de autorización requerido"));
            }
            
            if (!usuarioService.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }
            
            // Obtener información del usuario
            var usuarioOpt = usuarioService.getUserFromToken(token);
            
            if (usuarioOpt.isPresent()) {
                ApiResponseDto<UsuarioResponseDto> response = ApiResponseDto.success(
                    usuarioOpt.get(), 
                    "Token válido"
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error("USUARIO_NO_ENCONTRADO", "Usuario no encontrado"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error("ERROR_VALIDACION", "Error validando token"));
        }
    }
    
    /**
     * Endpoint para obtener información del usuario actual
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> getCurrentUser(
            @RequestHeader("Authorization") String authHeader) {
        
        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);
            
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseDto.error("TOKEN_REQUERIDO", "Token de autorización requerido"));
            }
            
            String username = jwtUtil.extractUsername(token);
            var usuarioOpt = usuarioService.getCurrentUser(username);
            
            if (usuarioOpt.isPresent()) {
                ApiResponseDto<UsuarioResponseDto> response = ApiResponseDto.success(
                    usuarioOpt.get(), 
                    "Información de usuario obtenida"
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponseDto.error("USUARIO_NO_ENCONTRADO", "Usuario no encontrado"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error("ERROR_USUARIO", "Error obteniendo información del usuario"));
        }
    }
    
    /**
     * Endpoint para logout (invalidar token - opcional)
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto<String>> logout(
            @RequestHeader("Authorization") String authHeader) {
        
        // En una implementación JWT stateless, el logout se maneja en el frontend
        // eliminando el token. Este endpoint es más bien informativo.
        
        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);
            
            if (token != null) {
                // Aquí podrías agregar el token a una blacklist si implementas esa funcionalidad
                ApiResponseDto<String> response = ApiResponseDto.success(
                    "Sesión cerrada exitosamente", 
                    "Logout exitoso"
                );
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseDto.error("TOKEN_REQUERIDO", "Token de autorización requerido"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error("ERROR_LOGOUT", "Error durante logout"));
        }
    }
    
    /**
     * Endpoint de health check
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponseDto<String>> health() {
        ApiResponseDto<String> response = ApiResponseDto.success(
            "Auth service funcionando correctamente", 
            "Health check exitoso"
        );
        return ResponseEntity.ok(response);
    }
}