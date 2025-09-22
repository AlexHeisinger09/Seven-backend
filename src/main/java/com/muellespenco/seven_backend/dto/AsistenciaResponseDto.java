package com.muellespenco.seven_backend.dto;

import com.muellespenco.seven_backend.entity.Asistencia;

import java.io.Serializable;
import java.time.LocalDate;

public class AsistenciaResponseDto implements Serializable {
    
    private Long faeCod;
    private Long cnomCod;
    private Integer faeTipoFaena;
    private String tfaDescripcion;
    private LocalDate faeFechaInicialPlanif;
    private String showFechaPlanif;
    private String faeHoraInicialPlanif;
    private Integer faeTurnoCod;
    private String turnoNombre;
    private Long traFicha;
    private Long traFichaTrabajador;
    private String traNombre;
    private String traApellidoP;
    private String traApellidoM;
    private String nombreCompleto;
    private Integer traSindicatoCod;
    private String sinNombre;
    private String dasiEntrada;
    private String horaEntrada;
    private String dasiSalida;
    private String horaSalida;
    private String dasiAusencia;
    private Integer carCodAsociaFaena;
    private String carNombre;
    private boolean tieneAsistencia;
    
    // Constructor vacío
    public AsistenciaResponseDto() {}
    
    // Constructor desde entidad
    public AsistenciaResponseDto(Asistencia asistencia) {
        this.faeCod = asistencia.getFaeCod();
        this.cnomCod = asistencia.getCnomCod();
        this.faeTipoFaena = asistencia.getFaeTipoFaena();
        this.tfaDescripcion = asistencia.getTfaDescripcion();
        this.faeFechaInicialPlanif = asistencia.getFaeFechaInicialPlanif();
        this.showFechaPlanif = asistencia.getShowFechaPlanif();
        this.faeHoraInicialPlanif = asistencia.getFaeHoraInicialPlanif();
        this.faeTurnoCod = asistencia.getFaeTurnoCod();
        this.turnoNombre = asistencia.getTurnoNombre();
        this.traFicha = asistencia.getTraFicha();
        this.traFichaTrabajador = asistencia.getTraFichaTrabajador();
        this.traNombre = asistencia.getTraNombre();
        this.traApellidoP = asistencia.getTraApellidoP();
        this.traApellidoM = asistencia.getTraApellidoM();
        this.nombreCompleto = asistencia.getNombreCompleto();
        this.traSindicatoCod = asistencia.getTraSindicatoCod();
        this.sinNombre = asistencia.getSinNombre();
        this.dasiEntrada = asistencia.getDasiEntrada();
        this.horaEntrada = asistencia.getHoraEntrada();
        this.dasiSalida = asistencia.getDasiSalida();
        this.horaSalida = asistencia.getHoraSalida();
        this.dasiAusencia = asistencia.getDasiAusencia();
        this.carCodAsociaFaena = asistencia.getCarCodAsociaFaena();
        this.carNombre = asistencia.getCarNombre();
        this.tieneAsistencia = asistencia.tieneAsistencia();
    }
    
    // Método estático para crear desde entidad
    public static AsistenciaResponseDto from(Asistencia asistencia) {
        return new AsistenciaResponseDto(asistencia);
    }
    
    // Getters y Setters
    public Long getFaeCod() { return faeCod; }
    public void setFaeCod(Long faeCod) { this.faeCod = faeCod; }
    
    public Long getCnomCod() { return cnomCod; }
    public void setCnomCod(Long cnomCod) { this.cnomCod = cnomCod; }
    
    public Integer getFaeTipoFaena() { return faeTipoFaena; }
    public void setFaeTipoFaena(Integer faeTipoFaena) { this.faeTipoFaena = faeTipoFaena; }
    
    public String getTfaDescripcion() { return tfaDescripcion; }
    public void setTfaDescripcion(String tfaDescripcion) { this.tfaDescripcion = tfaDescripcion; }
    
    public LocalDate getFaeFechaInicialPlanif() { return faeFechaInicialPlanif; }
    public void setFaeFechaInicialPlanif(LocalDate faeFechaInicialPlanif) { this.faeFechaInicialPlanif = faeFechaInicialPlanif; }
    
    public String getShowFechaPlanif() { return showFechaPlanif; }
    public void setShowFechaPlanif(String showFechaPlanif) { this.showFechaPlanif = showFechaPlanif; }
    
    public String getFaeHoraInicialPlanif() { return faeHoraInicialPlanif; }
    public void setFaeHoraInicialPlanif(String faeHoraInicialPlanif) { this.faeHoraInicialPlanif = faeHoraInicialPlanif; }
    
    public Integer getFaeTurnoCod() { return faeTurnoCod; }
    public void setFaeTurnoCod(Integer faeTurnoCod) { this.faeTurnoCod = faeTurnoCod; }
    
    public String getTurnoNombre() { return turnoNombre; }
    public void setTurnoNombre(String turnoNombre) { this.turnoNombre = turnoNombre; }
    
    public Long getTraFicha() { return traFicha; }
    public void setTraFicha(Long traFicha) { this.traFicha = traFicha; }
    
    public Long getTraFichaTrabajador() { return traFichaTrabajador; }
    public void setTraFichaTrabajador(Long traFichaTrabajador) { this.traFichaTrabajador = traFichaTrabajador; }
    
    public String getTraNombre() { return traNombre; }
    public void setTraNombre(String traNombre) { this.traNombre = traNombre; }
    
    public String getTraApellidoP() { return traApellidoP; }
    public void setTraApellidoP(String traApellidoP) { this.traApellidoP = traApellidoP; }
    
    public String getTraApellidoM() { return traApellidoM; }
    public void setTraApellidoM(String traApellidoM) { this.traApellidoM = traApellidoM; }
    
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    
    public Integer getTraSindicatoCod() { return traSindicatoCod; }
    public void setTraSindicatoCod(Integer traSindicatoCod) { this.traSindicatoCod = traSindicatoCod; }
    
    public String getSinNombre() { return sinNombre; }
    public void setSinNombre(String sinNombre) { this.sinNombre = sinNombre; }
    
    public String getDasiEntrada() { return dasiEntrada; }
    public void setDasiEntrada(String dasiEntrada) { this.dasiEntrada = dasiEntrada; }
    
    public String getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(String horaEntrada) { this.horaEntrada = horaEntrada; }
    
    public String getDasiSalida() { return dasiSalida; }
    public void setDasiSalida(String dasiSalida) { this.dasiSalida = dasiSalida; }
    
    public String getHoraSalida() { return horaSalida; }
    public void setHoraSalida(String horaSalida) { this.horaSalida = horaSalida; }
    
    public String getDasiAusencia() { return dasiAusencia; }
    public void setDasiAusencia(String dasiAusencia) { this.dasiAusencia = dasiAusencia; }
    
    public Integer getCarCodAsociaFaena() { return carCodAsociaFaena; }
    public void setCarCodAsociaFaena(Integer carCodAsociaFaena) { this.carCodAsociaFaena = carCodAsociaFaena; }
    
    public String getCarNombre() { return carNombre; }
    public void setCarNombre(String carNombre) { this.carNombre = carNombre; }
    
    public boolean isTieneAsistencia() { return tieneAsistencia; }
    public void setTieneAsistencia(boolean tieneAsistencia) { this.tieneAsistencia = tieneAsistencia; }
    
    @Override
    public String toString() {
        return "AsistenciaResponseDto{" +
                "faeCod=" + faeCod +
                ", faeFechaInicialPlanif=" + faeFechaInicialPlanif +
                ", turnoNombre='" + turnoNombre + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", horaEntrada='" + horaEntrada + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", carNombre='" + carNombre + '\'' +
                ", tieneAsistencia=" + tieneAsistencia +
                '}';
    }
}