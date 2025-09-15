package com.muellespenco.seven_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class ChangePasswordRequestDto {
    
    @NotBlank(message = "La contraseña actual no puede estar vacía")
    private String currentPassword;
    
    @NotBlank(message = "La nueva contraseña no puede estar vacía")
    @Size(min = 1, max = 100, message = "La nueva contraseña debe tener entre 1 y 100 caracteres")
    private String newPassword;
    
    @NotBlank(message = "La confirmación de contraseña no puede estar vacía")
    private String confirmPassword;
    
    // Constructor vacío
    public ChangePasswordRequestDto() {}
    
    // Constructor con parámetros
    public ChangePasswordRequestDto(String currentPassword, String newPassword, String confirmPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
    
    // Getters y Setters
    public String getCurrentPassword() {
        return currentPassword;
    }
    
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    // Método de validación
    public boolean isPasswordsMatching() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
    
    @Override
    public String toString() {
        return "ChangePasswordRequestDto{" +
                "currentPassword='[PROTECTED]'" +
                ", newPassword='[PROTECTED]'" +
                ", confirmPassword='[PROTECTED]'" +
                '}';
    }
}