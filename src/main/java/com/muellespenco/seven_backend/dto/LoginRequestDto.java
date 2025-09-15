package com.muellespenco.seven_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDto {
    
    @NotBlank(message = "El usuario/email no puede estar vacío")
    @Size(max = 100, message = "El usuario/email no puede tener más de 100 caracteres")
    private String credential; // Puede ser username o email
    
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 1, max = 100, message = "La contraseña debe tener entre 1 y 100 caracteres")
    private String password;
    
    // Constructor vacío
    public LoginRequestDto() {}
    
    // Constructor con parámetros
    public LoginRequestDto(String credential, String password) {
        this.credential = credential;
        this.password = password;
    }
    
    // Getters y Setters
    public String getCredential() {
        return credential;
    }
    
    public void setCredential(String credential) {
        this.credential = credential;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "LoginRequestDto{" +
                "credential='" + credential + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}