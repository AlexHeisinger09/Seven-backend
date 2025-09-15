package com.muellespenco.seven_backend.dto;

public class LoginResponseDto {
    
    private String token;
    private String tokenType;
    private Long expiresIn; // En segundos
    private UsuarioResponseDto usuario;
    private String message;
    
    // Constructor vacío
    public LoginResponseDto() {
        this.tokenType = "Bearer";
    }
    
    // Constructor con parámetros
    public LoginResponseDto(String token, Long expiresIn, UsuarioResponseDto usuario) {
        this.token = token;
        this.tokenType = "Bearer";
        this.expiresIn = expiresIn;
        this.usuario = usuario;
        this.message = "Login exitoso";
    }
    
    // Constructor para respuesta exitosa
    public static LoginResponseDto success(String token, Long expiresIn, UsuarioResponseDto usuario) {
        return new LoginResponseDto(token, expiresIn, usuario);
    }
    
    // Constructor para respuesta de error
    public static LoginResponseDto error(String message) {
        LoginResponseDto response = new LoginResponseDto();
        response.setMessage(message);
        return response;
    }
    
    // Getters y Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getTokenType() {
        return tokenType;
    }
    
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public Long getExpiresIn() {
        return expiresIn;
    }
    
    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
    
    public UsuarioResponseDto getUsuario() {
        return usuario;
    }
    
    public void setUsuario(UsuarioResponseDto usuario) {
        this.usuario = usuario;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "LoginResponseDto{" +
                "tokenType='" + tokenType + '\'' +
                ", expiresIn=" + expiresIn +
                ", usuario=" + usuario +
                ", message='" + message + '\'' +
                ", hasToken=" + (token != null && !token.isEmpty()) +
                '}';
    }
}