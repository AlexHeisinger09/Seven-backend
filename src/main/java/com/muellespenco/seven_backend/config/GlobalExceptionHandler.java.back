package com.muellespenco.seven_backend.config;

import com.muellespenco.seven_backend.dto.ApiResponseDto;
import com.muellespenco.seven_backend.exception.UsuarioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Manejo de excepciones de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        // No manejar errores de rutas de Swagger/OpenAPI
        if (isSwaggerPath(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Dejar que Spring maneje nativamente
        }
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ApiResponseDto<Map<String, String>> response = ApiResponseDto.error(
            "VALIDATION_ERROR", 
            "Errores de validación en los datos enviados"
        );
        response.setData(errors);
        
        return ResponseEntity.badRequest().body(response);
    }
    
    /**
     * Manejo de excepciones de usuario personalizada
     */
    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleUsuarioException(UsuarioException ex, HttpServletRequest request) {
        
        // No manejar errores de rutas de Swagger/OpenAPI
        if (isSwaggerPath(request)) {
            throw ex; // Re-lanzar para que Spring maneje nativamente
        }
        
        HttpStatus status;
        
        switch (ex.getCodigo()) {
            case "USUARIO_NO_ENCONTRADO":
                status = HttpStatus.NOT_FOUND;
                break;
            case "CREDENCIALES_INVALIDAS":
                status = HttpStatus.UNAUTHORIZED;
                break;
            case "USUARIO_NO_VIGENTE":
                status = HttpStatus.FORBIDDEN;
                break;
            case "TOKEN_INVALIDO":
                status = HttpStatus.UNAUTHORIZED;
                break;
            default:
                status = HttpStatus.BAD_REQUEST;
        }
        
        ApiResponseDto<Void> response = ApiResponseDto.error(ex.getCodigo(), ex.getMessage());
        return ResponseEntity.status(status).body(response);
    }
    
    /**
     * Manejo de credenciales incorrectas
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        
        // No manejar errores de rutas de Swagger/OpenAPI
        if (isSwaggerPath(request)) {
            throw ex; // Re-lanzar para que Spring maneje nativamente
        }
        
        ApiResponseDto<Void> response = ApiResponseDto.error(
            "CREDENCIALES_INVALIDAS", 
            "Usuario o contraseña incorrectos"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    /**
     * Manejo de usuario no encontrado
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleUsernameNotFoundException(UsernameNotFoundException ex, HttpServletRequest request) {
        
        // No manejar errores de rutas de Swagger/OpenAPI
        if (isSwaggerPath(request)) {
            throw ex; // Re-lanzar para que Spring maneje nativamente
        }
        
        ApiResponseDto<Void> response = ApiResponseDto.error(
            "USUARIO_NO_ENCONTRADO", 
            "Usuario no encontrado"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    /**
     * Manejo de acceso denegado
     */
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleAccessDeniedException(
            org.springframework.security.access.AccessDeniedException ex, HttpServletRequest request) {
        
        // No manejar errores de rutas de Swagger/OpenAPI
        if (isSwaggerPath(request)) {
            throw ex; // Re-lanzar para que Spring maneje nativamente
        }
        
        ApiResponseDto<Void> response = ApiResponseDto.error(
            "ACCESO_DENEGADO", 
            "No tienes permisos para acceder a este recurso"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
    
    /**
     * Manejo de errores genéricos
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGenericException(Exception ex, WebRequest request, HttpServletRequest httpRequest) {
        
        // No manejar errores de rutas de Swagger/OpenAPI
        if (isSwaggerPath(httpRequest)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponseDto.error("ERROR_INTERNO", "Ha ocurrido un error interno del servidor")
            );
        }
        
        // Log del error para debugging
        System.err.println("Error no manejado: " + ex.getMessage());
        ex.printStackTrace();
        
        ApiResponseDto<Void> response = ApiResponseDto.error(
            "ERROR_INTERNO", 
            "Ha ocurrido un error interno del servidor"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    /**
     * Manejo de errores de argumentos ilegales
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        
        // No manejar errores de rutas de Swagger/OpenAPI
        if (isSwaggerPath(request)) {
            throw ex; // Re-lanzar para que Spring maneje nativamente
        }
        
        ApiResponseDto<Void> response = ApiResponseDto.error(
            "ARGUMENTO_INVALIDO", 
            ex.getMessage()
        );
        return ResponseEntity.badRequest().body(response);
    }
    
    /**
     * Verificar si la ruta es de Swagger/OpenAPI
     */
    private boolean isSwaggerPath(HttpServletRequest request) {
        if (request == null) return false;
        
        String path = request.getRequestURI();
        return path != null && (
            path.contains("/v3/api-docs") ||
            path.contains("/swagger-ui") ||
            path.contains("/swagger-resources") ||
            path.contains("/webjars") ||
            path.endsWith("/swagger-ui.html")
        );
    }
}