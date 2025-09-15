package com.muellespenco.seven_backend.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiResponseDto<T> {
    
    private boolean success;
    private String message;
    private T data;
    private String timestamp; // CAMBIAR A STRING
    private String error;
    
    // Formatter para las fechas
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // Constructor vacío
    public ApiResponseDto() {
        this.timestamp = LocalDateTime.now().format(FORMATTER); // FORMATEAR A STRING
    }
    
    // Constructor para respuesta exitosa
    public ApiResponseDto(T data, String message) {
        this();
        this.success = true;
        this.data = data;
        this.message = message;
    }
    
    // Constructor para respuesta de error
    public ApiResponseDto(String error, String message) {
        this();
        this.success = false;
        this.error = error;
        this.message = message;
    }
    
    // Métodos estáticos para crear respuestas
    public static <T> ApiResponseDto<T> success(T data) {
        return new ApiResponseDto<>(data, "Operación exitosa");
    }
    
    public static <T> ApiResponseDto<T> success(T data, String message) {
        return new ApiResponseDto<>(data, message);
    }
    
    public static <T> ApiResponseDto<T> error(String error) {
        return new ApiResponseDto<>(error, "Ha ocurrido un error");
    }
    
    public static <T> ApiResponseDto<T> error(String error, String message) {
        return new ApiResponseDto<>(error, message);
    }
    
    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    @Override
    public String toString() {
        return "ApiResponseDto{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", hasData=" + (data != null) +
                ", error='" + error + '\'' +
                '}';
    }
}