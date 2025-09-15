package com.muellespenco.seven_backend.config;

import com.muellespenco.seven_backend.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    
    private final Usuario usuario;
    
    public CustomUserDetails(Usuario usuario) {
        this.usuario = usuario;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por ahora, todos los usuarios tienen rol USER
        // Más adelante puedes implementar roles basados en usuTipo
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    
    @Override
    public String getPassword() {
        return usuario.getUsuPass();
    }
    
    @Override
    public String getUsername() {
        return usuario.getUsuUser();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return usuario.isVigente();
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return usuario.isVigente();
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return usuario.isVigente();
    }
    
    @Override
    public boolean isEnabled() {
        return usuario.isVigente();
    }
    
    // Método para obtener el usuario completo
    public Usuario getUsuario() {
        return usuario;
    }
    
    // Métodos de utilidad
    public Integer getUsuCod() {
        return usuario.getUsuCod();
    }
    
    public String getEmail() {
        return usuario.getUsuEmail();
    }
    
    public String getNombreCompleto() {
        return usuario.getNombreCompleto();
    }
}