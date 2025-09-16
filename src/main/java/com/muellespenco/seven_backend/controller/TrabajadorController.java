package com.muellespenco.seven_backend.controller;

import com.muellespenco.seven_backend.config.JwtUtil;
import com.muellespenco.seven_backend.dto.ApiResponseDto;
import com.muellespenco.seven_backend.dto.TrabajadorResponseDto;
import com.muellespenco.seven_backend.entity.Usuario;
import com.muellespenco.seven_backend.repository.UsuarioRepository;
import com.muellespenco.seven_backend.service.TrabajadorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trabajadores")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
@Tag(name = "Trabajadores", description = "Endpoints para gestión de información de trabajadores")
public class TrabajadorController {

    @Autowired
    private TrabajadorService trabajadorService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Obtener información del trabajador actual (basado en el token)
     */
    @GetMapping("/me")
    @Operation(summary = "Obtener mi información de trabajador", description = "Obtiene la información completa del trabajador autenticado")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<TrabajadorResponseDto>> getMiInformacion(
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

            // Buscar el usuario para obtener la ficha
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuCod);
            
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("USUARIO_NO_ENCONTRADO", "Usuario no encontrado"));
            }

            Usuario usuario = usuarioOpt.get();
            Long usuFicha = usuario.getUsuFicha();

            if (usuFicha == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("FICHA_NO_ENCONTRADA", "No se encontró ficha de trabajador asociada al usuario"));
            }

            // Buscar la información del trabajador
            Optional<TrabajadorResponseDto> trabajadorOpt = trabajadorService.findByFichaTrabajador(usuFicha);

            if (trabajadorOpt.isPresent()) {
                ApiResponseDto<TrabajadorResponseDto> response = ApiResponseDto.success(
                        trabajadorOpt.get(),
                        "Información de trabajador obtenida exitosamente");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO", "No se encontró información del trabajador"));
            }

        } catch (Exception e) {
            System.err.println("Error obteniendo información del trabajador: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener trabajador por ficha de trabajador (solo para administradores)
     */
    @GetMapping("/ficha/{fichaTrabajador}")
    @Operation(summary = "Obtener trabajador por ficha", description = "Obtiene información de un trabajador específico por su ficha")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<TrabajadorResponseDto>> getTrabajadorByFicha(
            @Parameter(description = "Ficha del trabajador", required = true)
            @PathVariable Long fichaTrabajador,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            Optional<TrabajadorResponseDto> trabajadorOpt = trabajadorService.findByFichaTrabajador(fichaTrabajador);

            if (trabajadorOpt.isPresent()) {
                ApiResponseDto<TrabajadorResponseDto> response = ApiResponseDto.success(
                        trabajadorOpt.get(),
                        "Información de trabajador obtenida exitosamente");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO", "No se encontró el trabajador con la ficha especificada"));
            }

        } catch (Exception e) {
            System.err.println("Error obteniendo trabajador por ficha: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener trabajador por RUT
     */
    @GetMapping("/rut/{rut}")
    @Operation(summary = "Obtener trabajador por RUT", description = "Obtiene información de un trabajador por su RUT")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<TrabajadorResponseDto>> getTrabajadorByRut(
            @Parameter(description = "RUT del trabajador", required = true)
            @PathVariable String rut,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            Optional<TrabajadorResponseDto> trabajadorOpt = trabajadorService.findByRut(rut);

            if (trabajadorOpt.isPresent()) {
                ApiResponseDto<TrabajadorResponseDto> response = ApiResponseDto.success(
                        trabajadorOpt.get(),
                        "Información de trabajador obtenida exitosamente");
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO", "No se encontró el trabajador con el RUT especificado"));
            }

        } catch (Exception e) {
            System.err.println("Error obteniendo trabajador por RUT: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Buscar trabajadores por nombre
     */
    @GetMapping("/search")
    @Operation(summary = "Buscar trabajadores por nombre", description = "Busca trabajadores que coincidan con el nombre especificado")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<TrabajadorResponseDto>>> searchTrabajadores(
            @Parameter(description = "Nombre o parte del nombre a buscar", required = true)
            @RequestParam String nombre,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            if (nombre == null || nombre.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("PARAMETRO_REQUERIDO", "El parámetro 'nombre' es requerido"));
            }

            List<TrabajadorResponseDto> trabajadores = trabajadorService.searchByNombre(nombre);

            ApiResponseDto<List<TrabajadorResponseDto>> response = ApiResponseDto.success(
                    trabajadores,
                    "Búsqueda completada. Se encontraron " + trabajadores.size() + " trabajadores");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error buscando trabajadores: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener todos los trabajadores vigentes
     */
    @GetMapping("/vigentes")
    @Operation(summary = "Obtener trabajadores vigentes", description = "Obtiene la lista de todos los trabajadores vigentes")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<TrabajadorResponseDto>>> getTrabajadoresVigentes(
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            List<TrabajadorResponseDto> trabajadores = trabajadorService.findAllVigentes();

            ApiResponseDto<List<TrabajadorResponseDto>> response = ApiResponseDto.success(
                    trabajadores,
                    "Se obtuvieron " + trabajadores.size() + " trabajadores vigentes");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo trabajadores vigentes: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener cumpleañeros del mes
     */
    @GetMapping("/cumpleanos/mes/{mes}")
    @Operation(summary = "Obtener cumpleañeros del mes", description = "Obtiene trabajadores que cumplen años en el mes especificado")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<TrabajadorResponseDto>>> getCumpleanerosByMes(
            @Parameter(description = "Mes (1-12)", required = true)
            @PathVariable int mes,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            if (mes < 1 || mes > 12) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("MES_INVALIDO", "El mes debe estar entre 1 y 12"));
            }

            List<TrabajadorResponseDto> cumpleaneros = trabajadorService.findCumpleanerosByMes(mes);

            ApiResponseDto<List<TrabajadorResponseDto>> response = ApiResponseDto.success(
                    cumpleaneros,
                    "Se encontraron " + cumpleaneros.size() + " cumpleañeros en el mes " + mes);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo cumpleañeros del mes: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener cumpleaños de hoy
     */
    @GetMapping("/cumpleanos/hoy")
    @Operation(summary = "Obtener cumpleaños de hoy", description = "Obtiene trabajadores que cumplen años hoy")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<TrabajadorResponseDto>>> getCumpleanosHoy(
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            List<TrabajadorResponseDto> cumpleaneros = trabajadorService.findCumpleanosHoy();

            ApiResponseDto<List<TrabajadorResponseDto>> response = ApiResponseDto.success(
                    cumpleaneros,
                    "Se encontraron " + cumpleaneros.size() + " cumpleaños hoy");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo cumpleaños de hoy: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener trabajadores nuevos
     */
    @GetMapping("/nuevos")
    @Operation(summary = "Obtener trabajadores nuevos", description = "Obtiene trabajadores que ingresaron en los últimos días")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<List<TrabajadorResponseDto>>> getTrabajadoresNuevos(
            @Parameter(description = "Número de días hacia atrás para considerar como 'nuevos'", required = false)
            @RequestParam(defaultValue = "30") int dias,
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            if (dias < 1 || dias > 365) {
                return ResponseEntity.badRequest()
                        .body(ApiResponseDto.error("DIAS_INVALIDO", "Los días deben estar entre 1 y 365"));
            }

            List<TrabajadorResponseDto> trabajadoresNuevos = trabajadorService.findTrabajadoresNuevos(dias);

            ApiResponseDto<List<TrabajadorResponseDto>> response = ApiResponseDto.success(
                    trabajadoresNuevos,
                    "Se encontraron " + trabajadoresNuevos.size() + " trabajadores nuevos en los últimos " + dias + " días");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo trabajadores nuevos: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener estadísticas de trabajadores
     */
    @GetMapping("/estadisticas")
    @Operation(summary = "Obtener estadísticas de trabajadores", description = "Obtiene estadísticas básicas de los trabajadores")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<TrabajadorService.EstadisticasDto>> getEstadisticas(
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            TrabajadorService.EstadisticasDto estadisticas = trabajadorService.getEstadisticasBasicas();

            ApiResponseDto<TrabajadorService.EstadisticasDto> response = ApiResponseDto.success(
                    estadisticas,
                    "Estadísticas obtenidas exitosamente");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo estadísticas: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Endpoint de health check para trabajadores
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica el estado del servicio de trabajadores")
    public ResponseEntity<ApiResponseDto<String>> health() {
        try {
            long count = trabajadorService.countVigentes();
            ApiResponseDto<String> response = ApiResponseDto.success(
                    "Trabajadores service funcionando correctamente. " + count + " trabajadores vigentes.",
                    "Health check exitoso");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponseDto<String> response = ApiResponseDto.error(
                    "ERROR_HEALTH_CHECK", 
                    "Error en health check: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}