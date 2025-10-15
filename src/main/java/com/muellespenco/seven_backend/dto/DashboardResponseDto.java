package com.muellespenco.seven_backend.dto;

import java.util.List;

public class DashboardResponseDto {
    
    private InfoTurnosDto infoTurnos;
    private InfoVencimientoPaseDto vencimientoPase;
    private List<CumpleaneroDto> proximosCumpleanos;
    
    public DashboardResponseDto() {}
    
    public DashboardResponseDto(InfoTurnosDto infoTurnos, InfoVencimientoPaseDto vencimientoPase, List<CumpleaneroDto> proximosCumpleanos) {
        this.infoTurnos = infoTurnos;
        this.vencimientoPase = vencimientoPase;
        this.proximosCumpleanos = proximosCumpleanos;
    }
    
    // Getters y Setters
    public InfoTurnosDto getInfoTurnos() { return infoTurnos; }
    public void setInfoTurnos(InfoTurnosDto infoTurnos) { this.infoTurnos = infoTurnos; }
    
    public InfoVencimientoPaseDto getVencimientoPase() { return vencimientoPase; }
    public void setVencimientoPase(InfoVencimientoPaseDto vencimientoPase) { this.vencimientoPase = vencimientoPase; }
    
    public List<CumpleaneroDto> getProximosCumpleanos() { return proximosCumpleanos; }
    public void setProximosCumpleanos(List<CumpleaneroDto> proximosCumpleanos) { this.proximosCumpleanos = proximosCumpleanos; }
    
    // Clases internas
    public static class InfoTurnosDto {
        private Integer totalTurnos;
        private String periodo;
        private Integer mes;
        private Integer anio;
        
        public InfoTurnosDto() {}
        
        public InfoTurnosDto(Integer totalTurnos, String periodo, Integer mes, Integer anio) {
            this.totalTurnos = totalTurnos;
            this.periodo = periodo;
            this.mes = mes;
            this.anio = anio;
        }
        
        public Integer getTotalTurnos() { return totalTurnos; }
        public void setTotalTurnos(Integer totalTurnos) { this.totalTurnos = totalTurnos; }
        
        public String getPeriodo() { return periodo; }
        public void setPeriodo(String periodo) { this.periodo = periodo; }
        
        public Integer getMes() { return mes; }
        public void setMes(Integer mes) { this.mes = mes; }
        
        public Integer getAnio() { return anio; }
        public void setAnio(Integer anio) { this.anio = anio; }
    }
    
    public static class InfoVencimientoPaseDto {
        private String fechaVencimiento;
        private Integer diasParaVencer;
        private boolean vencido;
        private boolean proximoAVencer;
        
        public InfoVencimientoPaseDto() {}
        
        public InfoVencimientoPaseDto(String fechaVencimiento, Integer diasParaVencer, boolean vencido, boolean proximoAVencer) {
            this.fechaVencimiento = fechaVencimiento;
            this.diasParaVencer = diasParaVencer;
            this.vencido = vencido;
            this.proximoAVencer = proximoAVencer;
        }
        
        public String getFechaVencimiento() { return fechaVencimiento; }
        public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
        
        public Integer getDiasParaVencer() { return diasParaVencer; }
        public void setDiasParaVencer(Integer diasParaVencer) { this.diasParaVencer = diasParaVencer; }
        
        public boolean isVencido() { return vencido; }
        public void setVencido(boolean vencido) { this.vencido = vencido; }
        
        public boolean isProximoAVencer() { return proximoAVencer; }
        public void setProximoAVencer(boolean proximoAVencer) { this.proximoAVencer = proximoAVencer; }
    }
    
    public static class CumpleaneroDto {
        private String nombreCompleto;
        private String fechaCumpleanos;
        private Integer diasParaCumpleanos;
        private String traFoto;
        private String tseNombre;
        
        public CumpleaneroDto() {}
        
        public CumpleaneroDto(String nombreCompleto, String fechaCumpleanos, Integer diasParaCumpleanos, String traFoto, String tseNombre) {
            this.nombreCompleto = nombreCompleto;
            this.fechaCumpleanos = fechaCumpleanos;
            this.diasParaCumpleanos = diasParaCumpleanos;
            this.traFoto = traFoto;
            this.tseNombre = tseNombre;
        }
        
        public String getNombreCompleto() { return nombreCompleto; }
        public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
        
        public String getFechaCumpleanos() { return fechaCumpleanos; }
        public void setFechaCumpleanos(String fechaCumpleanos) { this.fechaCumpleanos = fechaCumpleanos; }
        
        public Integer getDiasParaCumpleanos() { return diasParaCumpleanos; }
        public void setDiasParaCumpleanos(Integer diasParaCumpleanos) { this.diasParaCumpleanos = diasParaCumpleanos; }
        
        public String getTraFoto() { return traFoto; }
        public void setTraFoto(String traFoto) { this.traFoto = traFoto; }
        
        public String getTseNombre() { return tseNombre; }
        public void setTseNombre(String tseNombre) { this.tseNombre = tseNombre; }
    }
}