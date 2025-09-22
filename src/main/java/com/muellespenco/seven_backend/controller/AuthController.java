package com.muellespenco.seven_backend.controller;

import com.muellespenco.seven_backend.config.JwtUtil;
import com.muellespenco.seven_backend.dto.ApiResponseDto;
import com.muellespenco.seven_backend.dto.ChangePasswordRequestDto;
import com.muellespenco.seven_backend.dto.LoginRequestDto;
import com.muellespenco.seven_backend.dto.LoginResponseDto;
import com.muellespenco.seven_backend.dto.UsuarioResponseDto;
import com.muellespenco.seven_backend.entity.Usuario;
import com.muellespenco.seven_backend.repository.UsuarioRepository;
import com.muellespenco.seven_backend.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Optional;


import org.springframework.core.env.Environment;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
@Tag(name = "Autenticación", description = "Endpoints para autenticación y gestión de usuarios")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private Environment environment;

    /**
     * Endpoint de login
     */
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y devuelve un token JWT")
    public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto loginRequest) {

        try {
            LoginResponseDto loginResponse = usuarioService.authenticate(loginRequest);

            if (loginResponse.getToken() != null) {
                // Login exitoso
                ApiResponseDto<LoginResponseDto> response = ApiResponseDto.success(
                        loginResponse,
                        "Login exitoso");
                return ResponseEntity.ok(response);
            } else {
                // Login fallido
                ApiResponseDto<LoginResponseDto> response = ApiResponseDto.error(
                        "CREDENCIALES_INVALIDAS",
                        loginResponse.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (Exception e) {
            ApiResponseDto<LoginResponseDto> response = ApiResponseDto.error(
                    "ERROR_LOGIN",
                    "Error interno durante el login");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint para validar token
     */
    @PostMapping("/validate")
    @Operation(summary = "Validar token", description = "Valida si un token JWT es válido")
    @SecurityRequirement(name = "Bearer Authentication")
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
                        "Token válido");
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
    @Operation(summary = "Obtener usuario actual", description = "Obtiene la información del usuario autenticado")
    @SecurityRequirement(name = "Bearer Authentication")
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
                        "Información de usuario obtenida");
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
     * Endpoint para cambiar contraseña
     */
    @PostMapping("/change-password")
    @Operation(summary = "Cambiar contraseña", description = "Permite al usuario cambiar su contraseña")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<String>> changePassword(
            @RequestHeader("Authorization") String authHeader,
            @Valid @RequestBody ChangePasswordRequestDto changePasswordRequest) {

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

            // Validar que las contraseñas nuevas coincidan
            if (!changePasswordRequest.isPasswordsMatching()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDto.error("PASSWORDS_NO_COINCIDEN", "Las contraseñas nuevas no coinciden"));
            }

            // Llamar al servicio para cambiar la contraseña
            String result = usuarioService.changePassword(usuCod, changePasswordRequest);

            // Analizar el resultado para determinar si fue exitoso
            if (result.contains("alert-success")) {
                return ResponseEntity.ok(ApiResponseDto.success(result, "Contraseña cambiada exitosamente"));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponseDto.error("CAMBIO_PASSWORD_FALLIDO", result));
            }

        } catch (Exception e) {
            System.err.println("Error en cambio de contraseña: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_CAMBIO_PASSWORD", "Error interno al cambiar la contraseña"));
        }
    }

    /**
     * Endpoint para logout (invalidar token - opcional)
     */
    @PostMapping("/logout")
    @Operation(summary = "Cerrar sesión", description = "Cierra la sesión del usuario")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<String>> logout(
            @RequestHeader("Authorization") String authHeader) {

        // En una implementación JWT stateless, el logout se maneja en el frontend
        // eliminando el token. Este endpoint es más bien informativo.

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token != null) {
                // Aquí podrías agregar el token a una blacklist si implementas esa
                // funcionalidad
                ApiResponseDto<String> response = ApiResponseDto.success(
                        "Sesión cerrada exitosamente",
                        "Logout exitoso");
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

    @PostMapping("/debug-login")
    @Operation(summary = "Debug del proceso de login")
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> debugLogin(
            @Valid @RequestBody LoginRequestDto loginRequest) {

        Map<String, Object> debugInfo = new HashMap<>();

        try {
            // 1. Verificar qué credential se está buscando
            debugInfo.put("credential_received", loginRequest.getCredential());
            debugInfo.put("password_received", loginRequest.getPassword());
            debugInfo.put("password_length", loginRequest.getPassword().length());

            // 2. Buscar usuario por credential
            Optional<Usuario> usuarioOpt = usuarioRepository.findByCredentialAndVigente(
                    loginRequest.getCredential(),
                    LocalDate.now());

            if (usuarioOpt.isEmpty()) {
                debugInfo.put("user_found", false);
                debugInfo.put("message", "Usuario no encontrado con esa credencial");

                // Verificar si existe el usuario sin filtro de vigencia
                Optional<Usuario> usuarioSinVigencia = usuarioRepository.findByUsuEmail(loginRequest.getCredential());
                if (usuarioSinVigencia.isEmpty()) {
                    usuarioSinVigencia = usuarioRepository.findByUsuUser(loginRequest.getCredential());
                }

                if (usuarioSinVigencia.isPresent()) {
                    Usuario u = usuarioSinVigencia.get();
                    debugInfo.put("user_exists_but_not_vigente", true);
                    debugInfo.put("user_vig_desde", u.getUsuVigDesde().toString());
                    debugInfo.put("user_vig_hasta", u.getUsuVigHasta().toString());
                    debugInfo.put("today", LocalDate.now().toString());
                    debugInfo.put("is_vigente", u.isVigente());
                } else {
                    debugInfo.put("user_exists_anywhere", false);
                }
            } else {
                Usuario usuario = usuarioOpt.get();
                debugInfo.put("user_found", true);
                debugInfo.put("user_cod", usuario.getUsuCod());
                debugInfo.put("user_name", usuario.getUsuUser());
                debugInfo.put("user_email", usuario.getUsuEmail());
                debugInfo.put("user_vigente", usuario.isVigente());
                debugInfo.put("stored_password_length", usuario.getUsuPass().length());
                debugInfo.put("stored_password", usuario.getUsuPass()); // Para debug, luego lo quitas

                // 3. Probar validación de contraseña
                try {
                    // Generar MD5 de "123"
                    String md5Of123 = generateMD5(loginRequest.getPassword());
                    debugInfo.put("md5_of_received_password", md5Of123);
                    debugInfo.put("stored_password_in_db", usuario.getUsuPass());
                    debugInfo.put("passwords_match", md5Of123.equals(usuario.getUsuPass()));

                    boolean passwordValid = validateMD5Password(loginRequest.getPassword(), usuario.getUsuPass());
                    debugInfo.put("password_validation_result", passwordValid);

                } catch (Exception e) {
                    debugInfo.put("password_validation_error", e.getMessage());
                    e.printStackTrace();
                }
            }

            ApiResponseDto<Map<String, Object>> response = ApiResponseDto.success(debugInfo, "Debug info");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            debugInfo.put("general_error", e.getMessage());
            debugInfo.put("error_type", e.getClass().getSimpleName());
            e.printStackTrace();

            ApiResponseDto<Map<String, Object>> response = ApiResponseDto.error("DEBUG_ERROR", e.getMessage());
            response.setData(debugInfo);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/config-info")
    @Operation(summary = "Verificar configuración de la aplicación")
    public ResponseEntity<ApiResponseDto<Map<String, Object>>> getConfigInfo() {
        Map<String, Object> config = new HashMap<>();

        try {
            // Información de base de datos (sin mostrar password completa)
            config.put("db_host", environment.getProperty("spring.datasource.url"));
            config.put("db_username", environment.getProperty("spring.datasource.username"));
            config.put("db_schema", environment.getProperty("spring.jpa.properties.hibernate.default_schema"));
            config.put("jwt_secret_length", environment.getProperty("app.jwt.secret", "").length());
            config.put("server_port", environment.getProperty("server.port"));
            config.put("log_level", environment.getProperty("logging.level.com.muellespenco"));
            config.put("show_sql", environment.getProperty("spring.jpa.show-sql"));

            // Variables de entorno directas
            config.put("env_db_host", System.getenv("DB_HOST"));
            config.put("env_db_name", System.getenv("DB_NAME"));
            config.put("env_db_username", System.getenv("DB_USERNAME"));
            config.put("env_db_password_set", System.getenv("DB_PASSWORD") != null);

            ApiResponseDto<Map<String, Object>> response = ApiResponseDto.success(config, "Configuración obtenida");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponseDto<Map<String, Object>> response = ApiResponseDto.error("CONFIG_ERROR", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Método auxiliar para generar MD5
    private String generateMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(input.getBytes());
            return bytesToHex(hash);
        } catch (Exception e) {
            return "ERROR_GENERATING_MD5";
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    /**
     * Validar contraseña MD5
     */
    private boolean validateMD5Password(String plainPassword, String hashedPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(plainPassword.getBytes());
            String md5Hash = bytesToHex(hash);
            return md5Hash.equals(hashedPassword);
        } catch (Exception e) {
            System.err.println("Error validando contraseña MD5: " + e.getMessage());
            return false;
        }
    }

    /**
     * Endpoint de health check
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica el estado del servicio de autenticación")
    public ResponseEntity<ApiResponseDto<String>> health() {
        ApiResponseDto<String> response = ApiResponseDto.success(
                "Auth service funcionando correctamente",
                "Health check exitoso");
        return ResponseEntity.ok(response);
    }
}