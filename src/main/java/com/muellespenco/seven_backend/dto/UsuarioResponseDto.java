package com.muellespenco.seven_backend.dto;

import com.muellespenco.seven_backend.entity.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioResponseDto {
    
    private Integer usuCod;
    private String usuUser;
    private String usuRut;
    private String usuNombre;
    private String usuApellidoP;
    private String usuApellidoM;
    private String usuEmail;
    private LocalDate usuVigDesde;
    private LocalDate usuVigHasta;
    private Integer usuTipo;
    private LocalDateTime usuUltimaConexion;
    private Long usuFicha;
    
    // Campos calculados
    private String nombreCompleto;
    private boolean vigente;
    
    // Constructor vacío
    public UsuarioResponseDto() {}
    
    // Constructor desde entidad Usuario
    public UsuarioResponseDto(Usuario usuario) {
        this.usuCod = usuario.getUsuCod();
        this.usuUser = usuario.getUsuUser();
        this.usuRut = usuario.getUsuRut();
        this.usuNombre = usuario.getUsuNombre();
        this.usuApellidoP = usuario.getUsuApellidoP();
        this.usuApellidoM = usuario.getUsuApellidoM();
        this.usuEmail = usuario.getUsuEmail();
        this.usuVigDesde = usuario.getUsuVigDesde();
        this.usuVigHasta = usuario.getUsuVigHasta();
        this.usuTipo = usuario.getUsuTipo();
        this.usuUltimaConexion = usuario.getUsuUltimaConexion();
        this.usuFicha = usuario.getUsuFicha();
        
        // Campos calculados
        this.nombreCompleto = usuario.getNombreCompleto();
        this.vigente = usuario.isVigente();
    }
    
    // Método estático para crear desde Usuario
    public static UsuarioResponseDto from(Usuario usuario) {
        return new UsuarioResponseDto(usuario);
    }
    
    // Getters y Setters
    public Integer getUsuCod() {
        return usuCod;
    }
    
    public void setUsuCod(Integer usuCod) {
        this.usuCod = usuCod;
    }
    
    public String getUsuUser() {
        return usuUser;
    }
    
    public void setUsuUser(String usuUser) {
        this.usuUser = usuUser;
    }
    
    public String getUsuRut() {
        return usuRut;
    }
    
    public void setUsuRut(String usuRut) {
        this.usuRut = usuRut;
    }
    
    public String getUsuNombre() {
        return usuNombre;
    }
    
    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }
    
    public String getUsuApellidoP() {
        return usuApellidoP;
    }
    
    public void setUsuApellidoP(String usuApellidoP) {
        this.usuApellidoP = usuApellidoP;
    }
    
    public String getUsuApellidoM() {
        return usuApellidoM;
    }
    
    public void setUsuApellidoM(String usuApellidoM) {
        this.usuApellidoM = usuApellidoM;
    }
    
    public String getUsuEmail() {
        return usuEmail;
    }
    
    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }
    
    public LocalDate getUsuVigDesde() {
        return usuVigDesde;
    }
    
    public void setUsuVigDesde(LocalDate usuVigDesde) {
        this.usuVigDesde = usuVigDesde;
    }
    
    public LocalDate getUsuVigHasta() {
        return usuVigHasta;
    }
    
    public void setUsuVigHasta(LocalDate usuVigHasta) {
        this.usuVigHasta = usuVigHasta;
    }
    
    public Integer getUsuTipo() {
        return usuTipo;
    }
    
    public void setUsuTipo(Integer usuTipo) {
        this.usuTipo = usuTipo;
    }
    
    public LocalDateTime getUsuUltimaConexion() {
        return usuUltimaConexion;
    }
    
    public void setUsuUltimaConexion(LocalDateTime usuUltimaConexion) {
        this.usuUltimaConexion = usuUltimaConexion;
    }
    
    public Long getUsuFicha() {
        return usuFicha;
    }
    
    public void setUsuFicha(Long usuFicha) {
        this.usuFicha = usuFicha;
    }
    
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public boolean isVigente() {
        return vigente;
    }
    
    public void setVigente(boolean vigente) {
        this.vigente = vigente;
    }
    
    @Override
    public String toString() {
        return "UsuarioResponseDto{" +
                "usuCod=" + usuCod +
                ", usuUser='" + usuUser + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", usuEmail='" + usuEmail + '\'' +
                ", vigente=" + vigente +
                '}';
    }
}