package com.muellespenco.seven_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_usuario", schema = "web_eventuales")
public class Usuario {
    
    @Id
    @Column(name = "usu_cod")
    private Integer usuCod;
    
    @Column(name = "usu_user", nullable = false, length = 20)
    private String usuUser;
    
    @Column(name = "usu_pass", nullable = false, length = 1000)
    private String usuPass;
    
    @Column(name = "usu_rut", nullable = false, length = 15)
    private String usuRut;
    
    @Column(name = "usu_nombre", nullable = false, length = 30)
    private String usuNombre;
    
    @Column(name = "usu_apellidop", nullable = false, length = 30)
    private String usuApellidoP;
    
    @Column(name = "usu_apellidom", nullable = false, length = 30)
    private String usuApellidoM;
    
    @Column(name = "usu_email", nullable = false, length = 100)
    private String usuEmail;
    
    @Column(name = "usu_vig_desde", nullable = false)
    private LocalDate usuVigDesde;
    
    @Column(name = "usu_vig_hasta", nullable = false)
    private LocalDate usuVigHasta;
    
    @Column(name = "usu_user_ultimo", nullable = false)
    private Integer usuUserUltimo;
    
    @Column(name = "usu_fecha_ultimo", nullable = false)
    private LocalDateTime usuFechaUltimo;
    
    @Column(name = "usu_tipo")
    private Integer usuTipo;
    
    @Column(name = "usu_ultima_conexion")
    private LocalDateTime usuUltimaConexion;
    
    @Column(name = "usu_ficha")
    private Long usuFicha;
    
    // Constructor vacío (requerido por JPA)
    public Usuario() {}
    
    // Constructor para crear usuarios
    public Usuario(String usuUser, String usuPass, String usuRut, String usuNombre, 
                   String usuApellidoP, String usuApellidoM, String usuEmail, 
                   LocalDate usuVigDesde, LocalDate usuVigHasta) {
        this.usuUser = usuUser;
        this.usuPass = usuPass;
        this.usuRut = usuRut;
        this.usuNombre = usuNombre;
        this.usuApellidoP = usuApellidoP;
        this.usuApellidoM = usuApellidoM;
        this.usuEmail = usuEmail;
        this.usuVigDesde = usuVigDesde;
        this.usuVigHasta = usuVigHasta;
    }
    
    // Métodos de utilidad
    public String getNombreCompleto() {
        return usuNombre + " " + usuApellidoP + " " + usuApellidoM;
    }
    
    public boolean isVigente() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(usuVigDesde) && !hoy.isAfter(usuVigHasta);
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
    
    public String getUsuPass() {
        return usuPass;
    }
    
    public void setUsuPass(String usuPass) {
        this.usuPass = usuPass;
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
    
    public Integer getUsuUserUltimo() {
        return usuUserUltimo;
    }
    
    public void setUsuUserUltimo(Integer usuUserUltimo) {
        this.usuUserUltimo = usuUserUltimo;
    }
    
    public LocalDateTime getUsuFechaUltimo() {
        return usuFechaUltimo;
    }
    
    public void setUsuFechaUltimo(LocalDateTime usuFechaUltimo) {
        this.usuFechaUltimo = usuFechaUltimo;
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
    
    @Override
    public String toString() {
        return "Usuario{" +
                "usuCod=" + usuCod +
                ", usuUser='" + usuUser + '\'' +
                ", usuRut='" + usuRut + '\'' +
                ", usuNombre='" + usuNombre + '\'' +
                ", usuApellidoP='" + usuApellidoP + '\'' +
                ", usuApellidoM='" + usuApellidoM + '\'' +
                ", usuEmail='" + usuEmail + '\'' +
                ", vigente=" + isVigente() +
                '}';
    }
}