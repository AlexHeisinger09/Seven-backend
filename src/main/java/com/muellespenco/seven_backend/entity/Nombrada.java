package com.muellespenco.seven_backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "v_web_nombrada", schema = "eventuales")
public class Nombrada implements Serializable {
    
    @Id
    @Column(name = "tra_ficha_trabajador")
    private Long traFichaTrabajador;
    
    @Column(name = "tra_nombre")
    private String traNombre;
    
    @Column(name = "tra_apellidop")
    private String traApellidoP;
    
    @Column(name = "tra_apellidom")
    private String traApellidoM;
    
    @Column(name = "tnom_descripcion")
    private String tnomDescripcion;
    
    @Column(name = "tfa_descripcion")
    private String tfaDescripcion;
    
    @Column(name = "fae_turno_cod")
    private Integer faeTurnoCod;
    
    @Column(name = "fae_fecha_inicial_planif")
    private String faeFechaInicialPlanif;
    
    @Column(name = "fae_hora_inicial_planif")
    private String faeHoraInicialPlanif;
    
    @Column(name = "fae_hora_final_planif")
    private String faeHoraFinalPlanif;
    
    // Constructor vacío
    public Nombrada() {}
    
    // Getters y Setters
    public Long getTraFichaTrabajador() {
        return traFichaTrabajador;
    }
    
    public void setTraFichaTrabajador(Long traFichaTrabajador) {
        this.traFichaTrabajador = traFichaTrabajador;
    }
    
    public String getTraNombre() {
        return traNombre;
    }
    
    public void setTraNombre(String traNombre) {
        this.traNombre = traNombre;
    }
    
    public String getTraApellidoP() {
        return traApellidoP;
    }
    
    public void setTraApellidoP(String traApellidoP) {
        this.traApellidoP = traApellidoP;
    }
    
    public String getTraApellidoM() {
        return traApellidoM;
    }
    
    public void setTraApellidoM(String traApellidoM) {
        this.traApellidoM = traApellidoM;
    }
    
    public String getTnomDescripcion() {
        return tnomDescripcion;
    }
    
    public void setTnomDescripcion(String tnomDescripcion) {
        this.tnomDescripcion = tnomDescripcion;
    }
    
    public String getTfaDescripcion() {
        return tfaDescripcion;
    }
    
    public void setTfaDescripcion(String tfaDescripcion) {
        this.tfaDescripcion = tfaDescripcion;
    }
    
    public Integer getFaeTurnoCod() {
        return faeTurnoCod;
    }
    
    public void setFaeTurnoCod(Integer faeTurnoCod) {
        this.faeTurnoCod = faeTurnoCod;
    }
    
    public String getFaeFechaInicialPlanif() {
        return faeFechaInicialPlanif;
    }
    
    public void setFaeFechaInicialPlanif(String faeFechaInicialPlanif) {
        this.faeFechaInicialPlanif = faeFechaInicialPlanif;
    }
    
    public String getFaeHoraInicialPlanif() {
        return faeHoraInicialPlanif;
    }
    
    public void setFaeHoraInicialPlanif(String faeHoraInicialPlanif) {
        this.faeHoraInicialPlanif = faeHoraInicialPlanif;
    }
    
    public String getFaeHoraFinalPlanif() {
        return faeHoraFinalPlanif;
    }
    
    public void setFaeHoraFinalPlanif(String faeHoraFinalPlanif) {
        this.faeHoraFinalPlanif = faeHoraFinalPlanif;
    }
    
    public String getNombreCompleto() {
        return traNombre + " " + traApellidoP + " " + traApellidoM;
    }
}