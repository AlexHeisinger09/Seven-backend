package com.muellespenco.seven_backend.controller;

import com.muellespenco.seven_backend.config.JwtUtil;
import com.muellespenco.seven_backend.dto.ApiResponseDto;
import com.muellespenco.seven_backend.dto.NombradaResponseDto;
import com.muellespenco.seven_backend.service.NombradaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nombradas")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
@Tag(name = "Nombradas", description = "Endpoints para gestión de nombradas de faenas")
public class NombradaController {

    @Autowired
    private NombradaService nombradaService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Obtener mis nombradas por fecha y turno
     */
    @GetMapping("/me")
    @Operation(summary = "Obtener mis nombradas", description = "Obtiene las nombradas del trabajador autenticado por fecha y turno")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<NombradaResponseDto>>> getMisNombradas(
            @Parameter(description = "Fecha en formato yyyy-MM-dd", required = true, example = "2025-10-14")
            @RequestParam String fecha,
            @Parameter(description = "Código del turno (1, 2 o 3)", required = true)
            @RequestParam Integer turno,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_REQUERIDO", "Token de autorización requerido"));
            }

            // Obtener el código de usuario del token
            Integer usuCod = jwtUtil.extractUsuCod(token);
            
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido - no se pudo obtener el usuario"));
            }

            // Validar turno
            if (turno < 1 || turno > 3) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("TURNO_INVALIDO", "El turno debe ser 1, 2 o 3"));
            }

            // Buscar nombradas
            List<NombradaResponseDto> nombradas = nombradaService.getMisNombradas(usuCod, fecha, turno);

            ApiResponseDto<List<NombradaResponseDto>> response = ApiResponseDto.success(
                    nombradas,
                    "Se encontraron " + nombradas.size() + " nombradas");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo nombradas: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener todas las nombradas por fecha y turno (para administradores)
     */
    @GetMapping
    @Operation(summary = "Obtener todas las nombradas", description = "Obtiene todas las nombradas por fecha y turno")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<NombradaResponseDto>>> getAllNombradas(
            @Parameter(description = "Fecha en formato yyyy-MM-dd", required = true, example = "2025-10-14")
            @RequestParam String fecha,
            @Parameter(description = "Código del turno (1, 2 o 3)", required = true)
            @RequestParam Integer turno,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            // Validar turno
            if (turno < 1 || turno > 3) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("TURNO_INVALIDO", "El turno debe ser 1, 2 o 3"));
            }

            // Buscar nombradas
            List<NombradaResponseDto> nombradas = nombradaService.getAllNombradas(fecha, turno);

            ApiResponseDto<List<NombradaResponseDto>> response = ApiResponseDto.success(
                    nombradas,
                    "Se encontraron " + nombradas.size() + " nombradas");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo nombradas: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Health check para nombradas
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica el estado del servicio de nombradas")
    public ResponseEntity<ApiResponseDto<String>> health() {
        ApiResponseDto<String> response = ApiResponseDto.success(
                "Nombradas service funcionando correctamente",
                "Health check exitoso");
        return ResponseEntity.ok(response);
    }
}