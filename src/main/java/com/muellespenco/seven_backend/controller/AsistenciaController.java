package com.muellespenco.seven_backend.controller;

import com.muellespenco.seven_backend.config.JwtUtil;
import com.muellespenco.seven_backend.dto.ApiResponseDto;
import com.muellespenco.seven_backend.dto.AsistenciaResponseDto;
import com.muellespenco.seven_backend.entity.Trabajador;
import com.muellespenco.seven_backend.entity.Usuario;
import com.muellespenco.seven_backend.repository.TrabajadorRepository;
import com.muellespenco.seven_backend.repository.UsuarioRepository;
import com.muellespenco.seven_backend.service.AsistenciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/asistencia")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
@Tag(name = "Asistencia", description = "Endpoints para gestión de asistencia de trabajadores")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Obtener todas las asistencias del trabajador autenticado
     */
    @GetMapping("/me/todas")
    @Operation(summary = "Obtener todas mis asistencias", description = "Obtiene todas las asistencias del trabajador autenticado ordenadas por fecha descendente")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<AsistenciaResponseDto>>> getTodasMisAsistencias(
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            // Obtener usuario del token
            Integer usuCod = jwtUtil.extractUsuCod(token);
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "No se pudo obtener el usuario del token"));
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuCod);
            if (usuarioOpt.isEmpty() || usuarioOpt.get().getUsuFicha() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("FICHA_NO_ENCONTRADA",
                                "No se encontró ficha de trabajador asociada"));
            }

            Long UsuFicha = usuarioOpt.get().getUsuFicha();
            Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByTraFichaTrabajador(UsuFicha);
            if (trabajadorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO",
                                "No existe trabajador para la ficha " + UsuFicha));
            }
            Long traFicha = trabajadorOpt.get().getTraFicha();

            // Usar el método existente del servicio
            List<AsistenciaResponseDto> asistencias = asistenciaService.findByTraFicha(traFicha);

            ApiResponseDto<List<AsistenciaResponseDto>> response = ApiResponseDto.success(
                    asistencias,
                    "Se obtuvieron " + asistencias.size() + " registros de asistencia totales");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo todas las asistencias: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener mi asistencia por mes y año
     */
    @GetMapping("/me/mes/{anio}/{mes}")
    @Operation(summary = "Obtener mi asistencia por mes", description = "Obtiene la asistencia del trabajador autenticado para un mes específico")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<AsistenciaResponseDto>>> getMiAsistenciaMensual(
            @Parameter(description = "Año", required = true) @PathVariable int anio,
            @Parameter(description = "Mes (1-12)", required = true) @PathVariable int mes,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            // Validar parámetros
            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("MES_INVALIDO", "El mes debe estar entre 1 y 12"));
            }

            if (anio < 2000 || anio > LocalDate.now().getYear() + 1) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("ANIO_INVALIDO",
                                "El año debe estar entre 2000 y " + (LocalDate.now().getYear() + 1)));
            }

            // Obtener usuario del token
            Integer usuCod = jwtUtil.extractUsuCod(token);
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "No se pudo obtener el usuario del token"));
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuCod);
            if (usuarioOpt.isEmpty() || usuarioOpt.get().getUsuFicha() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("FICHA_NO_ENCONTRADA",
                                "No se encontró ficha de trabajador asociada"));
            }

            Long UsuFicha = usuarioOpt.get().getUsuFicha();
            Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByTraFichaTrabajador(UsuFicha);
            if (trabajadorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO",
                                "No existe trabajador para la ficha " + UsuFicha));
            }
            Long traFicha = trabajadorOpt.get().getTraFicha();

            // Obtener asistencias
            List<AsistenciaResponseDto> asistencias = asistenciaService.findByTraFichaAndMonthYear(traFicha, mes, anio);

            ApiResponseDto<List<AsistenciaResponseDto>> response = ApiResponseDto.success(
                    asistencias,
                    "Se obtuvieron " + asistencias.size() + " registros de asistencia para " + mes + "/" + anio);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo asistencia mensual: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener mi asistencia para una fecha específica
     */
    @GetMapping("/me/fecha/{fecha}")
    @Operation(summary = "Obtener mi asistencia por fecha", description = "Obtiene la asistencia del trabajador autenticado para una fecha específica")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<AsistenciaResponseDto>>> getMiAsistenciaFecha(
            @Parameter(description = "Fecha (yyyy-MM-dd)", required = true) @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            // Obtener usuario del token
            Integer usuCod = jwtUtil.extractUsuCod(token);
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "No se pudo obtener el usuario del token"));
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuCod);
            if (usuarioOpt.isEmpty() || usuarioOpt.get().getUsuFicha() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("FICHA_NO_ENCONTRADA",
                                "No se encontró ficha de trabajador asociada"));
            }

            Long UsuFicha = usuarioOpt.get().getUsuFicha();
            Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByTraFichaTrabajador(UsuFicha);
            if (trabajadorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO",
                                "No existe trabajador para la ficha " + UsuFicha));
            }
            Long traFicha = trabajadorOpt.get().getTraFicha();

            // Obtener asistencias para la fecha
            List<AsistenciaResponseDto> asistencias = asistenciaService.findByTraFichaAndFecha(traFicha, fecha);

            ApiResponseDto<List<AsistenciaResponseDto>> response = ApiResponseDto.success(
                    asistencias,
                    "Se obtuvieron " + asistencias.size() + " registros de asistencia para " + fecha);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo asistencia por fecha: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener días con asistencia en un mes
     */
    @GetMapping("/me/dias-asistencia/{anio}/{mes}")
    @Operation(summary = "Obtener días con asistencia", description = "Obtiene los días del mes en los que el trabajador tiene asistencia registrada")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<Integer>>> getDiasConAsistencia(
            @Parameter(description = "Año", required = true) @PathVariable int anio,
            @Parameter(description = "Mes (1-12)", required = true) @PathVariable int mes,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            // Validar parámetros
            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("MES_INVALIDO", "El mes debe estar entre 1 y 12"));
            }

            // Obtener usuario del token
            Integer usuCod = jwtUtil.extractUsuCod(token);
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "No se pudo obtener el usuario del token"));
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuCod);
            if (usuarioOpt.isEmpty() || usuarioOpt.get().getUsuFicha() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("FICHA_NO_ENCONTRADA",
                                "No se encontró ficha de trabajador asociada"));
            }

            Long UsuFicha = usuarioOpt.get().getUsuFicha();
            Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByTraFichaTrabajador(UsuFicha);
            if (trabajadorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO",
                                "No existe trabajador para la ficha " + UsuFicha));
            }
            Long traFicha = trabajadorOpt.get().getTraFicha();

            // Obtener días con asistencia
            List<Integer> diasConAsistencia = asistenciaService.getDiasConAsistencia(traFicha, mes, anio);

            ApiResponseDto<List<Integer>> response = ApiResponseDto.success(
                    diasConAsistencia,
                    "Se encontraron " + diasConAsistencia.size() + " días con asistencia en " + mes + "/" + anio);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo días con asistencia: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener estadísticas de asistencia mensual
     */
    @GetMapping("/me/estadisticas/{anio}/{mes}")
    @Operation(summary = "Obtener estadísticas de asistencia mensual", description = "Obtiene estadísticas de asistencia del trabajador para un mes específico")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<AsistenciaService.EstadisticasAsistenciaDto>> getEstadisticasMensuales(
            @Parameter(description = "Año", required = true) @PathVariable int anio,
            @Parameter(description = "Mes (1-12)", required = true) @PathVariable int mes,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            // Validar parámetros
            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("MES_INVALIDO", "El mes debe estar entre 1 y 12"));
            }

            // Obtener usuario del token
            Integer usuCod = jwtUtil.extractUsuCod(token);
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "No se pudo obtener el usuario del token"));
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuCod);
            if (usuarioOpt.isEmpty() || usuarioOpt.get().getUsuFicha() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("FICHA_NO_ENCONTRADA",
                                "No se encontró ficha de trabajador asociada"));
            }

            Long UsuFicha = usuarioOpt.get().getUsuFicha();
            Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByTraFichaTrabajador(UsuFicha);
            if (trabajadorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO",
                                "No existe trabajador para la ficha " + UsuFicha));
            }
            Long traFicha = trabajadorOpt.get().getTraFicha();

            // Obtener estadísticas
            AsistenciaService.EstadisticasAsistenciaDto estadisticas = asistenciaService
                    .getEstadisticasMensual(traFicha, mes, anio);

            ApiResponseDto<AsistenciaService.EstadisticasAsistenciaDto> response = ApiResponseDto.success(
                    estadisticas,
                    "Estadísticas de asistencia obtenidas para " + mes + "/" + anio);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo estadísticas de asistencia: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener últimas asistencias
     */
    @GetMapping("/me/ultimas")
    @Operation(summary = "Obtener últimas asistencias", description = "Obtiene las últimas asistencias del trabajador autenticado")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<AsistenciaResponseDto>>> getUltimasAsistencias(
            @Parameter(description = "Número de registros a obtener", required = false) @RequestParam(defaultValue = "10") int limite,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            // Validar límite
            if (limite < 1 || limite > 100) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("LIMITE_INVALIDO", "El límite debe estar entre 1 y 100"));
            }

            // Obtener usuario del token
            Integer usuCod = jwtUtil.extractUsuCod(token);
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "No se pudo obtener el usuario del token"));
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuCod);
            if (usuarioOpt.isEmpty() || usuarioOpt.get().getUsuFicha() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("FICHA_NO_ENCONTRADA",
                                "No se encontró ficha de trabajador asociada"));
            }

            Long UsuFicha = usuarioOpt.get().getUsuFicha();
            Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByTraFichaTrabajador(UsuFicha);
            if (trabajadorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO",
                                "No existe trabajador para la ficha " + UsuFicha));
            }
            Long traFicha = trabajadorOpt.get().getTraFicha();

            // Obtener últimas asistencias
            List<AsistenciaResponseDto> asistencias = asistenciaService.getUltimasAsistencias(traFicha, limite);

            ApiResponseDto<List<AsistenciaResponseDto>> response = ApiResponseDto.success(
                    asistencias,
                    "Se obtuvieron " + asistencias.size() + " registros de asistencia recientes");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo últimas asistencias: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Health check para asistencia
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica el estado del servicio de asistencia")
    public ResponseEntity<ApiResponseDto<String>> health() {
        ApiResponseDto<String> response = ApiResponseDto.success(
                "Servicio de asistencia funcionando correctamente",
                "Health check exitoso");
        return ResponseEntity.ok(response);
    }
}