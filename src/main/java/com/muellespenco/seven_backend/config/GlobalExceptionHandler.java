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

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Manejo de excepciones de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        
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
    public ResponseEntity<ApiResponseDto<Void>> handleUsuarioException(UsuarioException ex) {
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
    public ResponseEntity<ApiResponseDto<Void>> handleBadCredentialsException(BadCredentialsException ex) {
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
    public ResponseEntity<ApiResponseDto<Void>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
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
            org.springframework.security.access.AccessDeniedException ex) {
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
    public ResponseEntity<ApiResponseDto<Void>> handleGenericException(Exception ex, WebRequest request) {
        
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
    public ResponseEntity<ApiResponseDto<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponseDto<Void> response = ApiResponseDto.error(
            "ARGUMENTO_INVALIDO", 
            ex.getMessage()
        );
        return ResponseEntity.badRequest().body(response);
    }
}