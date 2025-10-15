package com.muellespenco.seven_backend.controller;

import com.muellespenco.seven_backend.config.JwtUtil;
import com.muellespenco.seven_backend.dto.ApiResponseDto;
import com.muellespenco.seven_backend.dto.LiquidacionResponseDto;
import com.muellespenco.seven_backend.dto.LiquidacionesListaResponseDto;
import com.muellespenco.seven_backend.service.LiquidacionService;
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
@RequestMapping("/liquidaciones")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
@Tag(name = "Liquidaciones", description = "Endpoints para gestión de liquidaciones de sueldo")
public class LiquidacionController {

    @Autowired
    private LiquidacionService liquidacionService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Obtener mis liquidaciones con paginación y filtros
     */
    @GetMapping("/me")
    @Operation(summary = "Obtener mis liquidaciones", description = "Obtiene las liquidaciones del trabajador autenticado con paginación y filtros opcionales")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<LiquidacionesListaResponseDto>> getMisLiquidaciones(
            @Parameter(description = "Número de página (inicia en 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Cantidad de resultados por página", example = "10")
            @RequestParam(defaultValue = "10") int limit,
            @Parameter(description = "Filtro por período (formato: YYYYMM, ej: 202501)", required = false)
            @RequestParam(required = false) Integer periodo,
            @Parameter(description = "Fecha desde (formato: yyyy-MM-dd)", required = false)
            @RequestParam(required = false) String fechaDesde,
            @Parameter(description = "Fecha hasta (formato: yyyy-MM-dd)", required = false)
            @RequestParam(required = false) String fechaHasta,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_REQUERIDO", "Token de autorización requerido"));
            }

            Integer usuCod = jwtUtil.extractUsuCod(token);
            
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido - no se pudo obtener el usuario"));
            }

            LiquidacionesListaResponseDto liquidaciones = liquidacionService.getMisLiquidaciones(usuCod, page, limit, periodo, fechaDesde, fechaHasta);

            ApiResponseDto<LiquidacionesListaResponseDto> response = ApiResponseDto.success(
                    liquidaciones,
                    "Se obtuvieron " + liquidaciones.getLiquidaciones().size() + " liquidaciones");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo liquidaciones: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor: " + e.getMessage()));
        }
    }

    /**
     * Obtener detalle de una liquidación específica
     */
    @GetMapping("/{cnomCod}")
    @Operation(summary = "Obtener detalle de liquidación", description = "Obtiene el detalle completo de una liquidación específica")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<LiquidacionResponseDto>> getLiquidacionDetail(
            @Parameter(description = "Código de nómina", required = true)
            @PathVariable Long cnomCod,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            LiquidacionResponseDto liquidacion = liquidacionService.getLiquidacionDetail(cnomCod);

            ApiResponseDto<LiquidacionResponseDto> response = ApiResponseDto.success(
                    liquidacion,
                    "Detalle de liquidación obtenido exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo detalle de liquidación: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener períodos disponibles para el trabajador
     */
    @GetMapping("/periodos")
    @Operation(summary = "Obtener períodos disponibles", description = "Obtiene la lista de períodos disponibles para el trabajador autenticado")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<Integer>>> getMisPeriodosDisponibles(
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            Integer usuCod = jwtUtil.extractUsuCod(token);
            
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido - no se pudo obtener el usuario"));
            }

            List<Integer> periodos = liquidacionService.getMisPeriodosDisponibles(usuCod);

            ApiResponseDto<List<Integer>> response = ApiResponseDto.success(
                    periodos,
                    "Se encontraron " + periodos.size() + " períodos disponibles");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo períodos disponibles: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Health check para liquidaciones
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica el estado del servicio de liquidaciones")
    public ResponseEntity<ApiResponseDto<String>> health() {
        ApiResponseDto<String> response = ApiResponseDto.success(
                "Liquidaciones service funcionando correctamente",
                "Health check exitoso");
        return ResponseEntity.ok(response);
    }
}