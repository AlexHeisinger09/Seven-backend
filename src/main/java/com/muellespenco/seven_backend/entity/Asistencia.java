package com.muellespenco.seven_backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "v_asistencia_trabajador_faena_cerrada", schema = "eventuales")
public class Asistencia {
    
    @Id
    @Column(name = "fae_cod")
    private Long faeCod;
    
    @Column(name = "cnom_cod")
    private Long cnomCod;
    
    @Column(name = "fae_tipo_faena")
    private Integer faeTipoFaena;
    
    @Column(name = "tfa_descripcion")
    private String tfaDescripcion;
    
    @Column(name = "fae_fecha_inicial_planif")
    private LocalDate faeFechaInicialPlanif;
    
    @Column(name = "show_fecha_planif")
    private String showFechaPlanif;
    
    @Column(name = "fae_hora_inicial_planif")
    private LocalTime faeHoraInicialPlanif;
    
    @Column(name = "fae_turno_cod")
    private Integer faeTurnoCod;
    
    @Column(name = "tra_ficha")
    private Long traFicha;
    
    @Column(name = "tra_ficha_trabajador")
    private Long traFichaTrabajador;
    
    @Column(name = "tra_nombre")
    private String traNombre;
    
    @Column(name = "tra_apellidop")
    private String traApellidoP;
    
    @Column(name = "tra_apellidom")
    private String traApellidoM;
    
    @Column(name = "tra_sindicato_cod")
    private Integer traSindicatoCod;
    
    @Column(name = "sin_nombre")
    private String sinNombre;
    
    @Column(name = "dasi_entrada")
    private String dasiEntrada;
    
    @Column(name = "hora_entrada")
    private String horaEntrada;
    
    @Column(name = "dasi_salida")
    private String dasiSalida;
    
    @Column(name = "hora_salida")
    private String horaSalida;
    
    @Column(name = "dasi_ausencia")
    private String dasiAusencia;
    
    @Column(name = "car_cod_asocia_faena")
    private Integer carCodAsociaFaena;
    
    @Column(name = "car_nombre")
    private String carNombre;
    
    // Constructor vacío
    public Asistencia() {}
    
    // Métodos de utilidad
    public String getNombreCompleto() {
        StringBuilder nombreCompleto = new StringBuilder();
        if (traNombre != null) nombreCompleto.append(traNombre);
        if (traApellidoP != null) nombreCompleto.append(" ").append(traApellidoP);
        if (traApellidoM != null) nombreCompleto.append(" ").append(traApellidoM);
        return nombreCompleto.toString().trim();
    }
    
    public String getTurnoNombre() {
        if (faeTurnoCod == null) return "Sin turno";
        switch (faeTurnoCod) {
            case 1: return "Turno 1";
            case 2: return "Turno 2";
            case 3: return "Turno 3";
            default: return "Turno " + faeTurnoCod;
        }
    }
    
    public boolean tieneAsistencia() {
        return horaEntrada != null && !horaEntrada.trim().isEmpty();
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
    
    public LocalTime getFaeHoraInicialPlanif() { return faeHoraInicialPlanif; }
    public void setFaeHoraInicialPlanif(LocalTime faeHoraInicialPlanif) { this.faeHoraInicialPlanif = faeHoraInicialPlanif; }
    
    public Integer getFaeTurnoCod() { return faeTurnoCod; }
    public void setFaeTurnoCod(Integer faeTurnoCod) { this.faeTurnoCod = faeTurnoCod; }
    
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
    
    @Override
    public String toString() {
        return "Asistencia{" +
                "faeCod=" + faeCod +
                ", faeFechaInicialPlanif=" + faeFechaInicialPlanif +
                ", faeTurnoCod=" + faeTurnoCod +
                ", traFicha=" + traFicha +
                ", nombreCompleto='" + getNombreCompleto() + '\'' +
                ", horaEntrada='" + horaEntrada + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", carNombre='" + carNombre + '\'' +
                '}';
    }
}