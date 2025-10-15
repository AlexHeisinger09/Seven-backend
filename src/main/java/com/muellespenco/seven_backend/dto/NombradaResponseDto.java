package com.muellespenco.seven_backend.dto;

import com.muellespenco.seven_backend.entity.Nombrada;
import java.io.Serializable;

public class NombradaResponseDto implements Serializable {
    
    private Long traFichaTrabajador;
    private String traNombre;
    private String traApellidoP;
    private String traApellidoM;
    private String tnomDescripcion;
    private String tfaDescripcion;
    private Integer faeTurnoCod;
    private String faeFechaInicialPlanif;
    private String faeHoraInicialPlanif;
    private String faeHoraFinalPlanif;
    private String nombreCompleto;
    
    public NombradaResponseDto() {}
    
    public NombradaResponseDto(Nombrada nombrada) {
        this.traFichaTrabajador = nombrada.getTraFichaTrabajador();
        this.traNombre = nombrada.getTraNombre();
        this.traApellidoP = nombrada.getTraApellidoP();
        this.traApellidoM = nombrada.getTraApellidoM();
        this.tnomDescripcion = nombrada.getTnomDescripcion();
        this.tfaDescripcion = nombrada.getTfaDescripcion();
        this.faeTurnoCod = nombrada.getFaeTurnoCod();
        this.faeFechaInicialPlanif = nombrada.getFaeFechaInicialPlanif();
        this.faeHoraInicialPlanif = nombrada.getFaeHoraInicialPlanif();
        this.faeHoraFinalPlanif = nombrada.getFaeHoraFinalPlanif();
        this.nombreCompleto = nombrada.getNombreCompleto();
    }
    
    public static NombradaResponseDto from(Nombrada nombrada) {
        return new NombradaResponseDto(nombrada);
    }
    
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
        return nombreCompleto;
    }
    
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
}
