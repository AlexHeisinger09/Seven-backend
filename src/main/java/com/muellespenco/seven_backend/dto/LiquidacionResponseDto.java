package com.muellespenco.seven_backend.dto;

import com.muellespenco.seven_backend.entity.Liquidacion;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LiquidacionResponseDto implements Serializable {
    
    // Información básica de la liquidación
    private Long cnomCod;
    private LocalDate faeFechaInicial;
    private String faeFechaInicialShow;
    private Long traFicha;
    private String trabajadorNombre;
    private String trabajadorApellidoP;
    private String trabajadorApellidoM;
    
    // Información del cargo y sindicato
    private String carNombre;
    private String sinNombre;
    private String traContratoProvision;
    
    // Información de la faena
    private Long faeCod;
    private String tfaDescripcion;
    private String navDescripcion;
    private String estado;
    private String permiso;
    
    // Haberes
    private BigDecimal valorBase;
    private BigDecimal numHorasExtra;
    private BigDecimal valorHoraExtra;
    private BigDecimal tonelajeOCamion;
    private BigDecimal tonPagado;
    private BigDecimal otrosImponibles;
    private BigDecimal totalImponible;
    
    // Haberes no imponibles
    private BigDecimal colacion;
    private BigDecimal movilizacion;
    private BigDecimal movCol;
    private BigDecimal colAdicional;
    private BigDecimal otrosNoImponibles;
    private BigDecimal totalHaber;
    
    // Descuentos previsionales
    private String afpNombre;
    private String afpPorcMasComision;
    private BigDecimal descAfp;
    private BigDecimal descC2;
    private BigDecimal descApv;
    private String salNombre;
    private BigDecimal descSalud;
    private BigDecimal imptoUnico;
    
    // Otros descuentos
    private BigDecimal ptmoMuelle;
    private BigDecimal ptmoCaja;
    private BigDecimal cuotaSindicato;
    private BigDecimal otrosDescuentos;
    private BigDecimal totalDescuentos;
    
    // Líquido
    private BigDecimal liquidoPago;
    
    // Información adicional
    private String fechaLiquidacionShow;
    private String nomino;
    private String finalizo;
    private String liquido;
    private String glosaNomina;
    private String glosaFaena;

    public LiquidacionResponseDto() {}

    public LiquidacionResponseDto(Liquidacion liquidacion) {
        this.cnomCod = liquidacion.getCnomCod();
        this.faeFechaInicial = liquidacion.getFaeFechaInicial();
        this.faeFechaInicialShow = liquidacion.getFaeFechaInicialShow();
        this.traFicha = liquidacion.getTraFichaTrabajador();
        this.trabajadorNombre = liquidacion.getTraNombre();
        this.trabajadorApellidoP = liquidacion.getTraApellidoP();
        this.trabajadorApellidoM = liquidacion.getTraApellidoM();
        
        this.carNombre = liquidacion.getCarNombre();
        this.sinNombre = liquidacion.getSinNombre();
        this.traContratoProvision = liquidacion.getTraContratoProvision();
        
        this.faeCod = liquidacion.getFaeCod();
        this.tfaDescripcion = liquidacion.getTfaDescripcion();
        this.navDescripcion = liquidacion.getNavDescripcion();
        this.estado = liquidacion.getEstado();
        this.permiso = liquidacion.getPermiso();
        
        this.valorBase = liquidacion.getValorBase() != null ? liquidacion.getValorBase() : BigDecimal.ZERO;
        this.numHorasExtra = liquidacion.getNumHorasExtra() != null ? liquidacion.getNumHorasExtra() : BigDecimal.ZERO;
        this.valorHoraExtra = liquidacion.getValorHoraExtra() != null ? liquidacion.getValorHoraExtra() : BigDecimal.ZERO;
        this.tonelajeOCamion = liquidacion.getTonelajeOCamion() != null ? liquidacion.getTonelajeOCamion() : BigDecimal.ZERO;
        this.tonPagado = liquidacion.getTonPagado() != null ? liquidacion.getTonPagado() : BigDecimal.ZERO;
        this.otrosImponibles = liquidacion.getOtrosImponibles() != null ? liquidacion.getOtrosImponibles() : BigDecimal.ZERO;
        this.totalImponible = liquidacion.getTotalImponible() != null ? liquidacion.getTotalImponible() : BigDecimal.ZERO;
        
        this.colacion = liquidacion.getColacion() != null ? liquidacion.getColacion() : BigDecimal.ZERO;
        this.movilizacion = liquidacion.getMovilizacion() != null ? liquidacion.getMovilizacion() : BigDecimal.ZERO;
        this.movCol = liquidacion.getMovCol() != null ? liquidacion.getMovCol() : BigDecimal.ZERO;
        this.colAdicional = liquidacion.getColAdicional() != null ? liquidacion.getColAdicional() : BigDecimal.ZERO;
        this.otrosNoImponibles = liquidacion.getOtrosNoImponibles() != null ? liquidacion.getOtrosNoImponibles() : BigDecimal.ZERO;
        this.totalHaber = liquidacion.getTotalHaber() != null ? liquidacion.getTotalHaber() : BigDecimal.ZERO;
        
        this.afpNombre = liquidacion.getAfpNombre();
        this.afpPorcMasComision = liquidacion.getAfpPorcMasComision();
        this.descAfp = liquidacion.getDescAfp() != null ? liquidacion.getDescAfp() : BigDecimal.ZERO;
        this.descC2 = liquidacion.getDescC2() != null ? liquidacion.getDescC2() : BigDecimal.ZERO;
        this.descApv = liquidacion.getDescApv() != null ? liquidacion.getDescApv() : BigDecimal.ZERO;
        this.salNombre = liquidacion.getSalNombre();
        this.descSalud = liquidacion.getDescSalud() != null ? liquidacion.getDescSalud() : BigDecimal.ZERO;
        this.imptoUnico = liquidacion.getImptoUnico() != null ? liquidacion.getImptoUnico() : BigDecimal.ZERO;
        
        this.ptmoMuelle = liquidacion.getPtmoMuelle() != null ? liquidacion.getPtmoMuelle() : BigDecimal.ZERO;
        this.ptmoCaja = liquidacion.getPtmoCaja() != null ? liquidacion.getPtmoCaja() : BigDecimal.ZERO;
        this.cuotaSindicato = liquidacion.getCuotaSindicato() != null ? liquidacion.getCuotaSindicato() : BigDecimal.ZERO;
        this.otrosDescuentos = liquidacion.getOtrosDescuentos() != null ? liquidacion.getOtrosDescuentos() : BigDecimal.ZERO;
        this.totalDescuentos = liquidacion.getTotalDescuentos() != null ? liquidacion.getTotalDescuentos() : BigDecimal.ZERO;
        
        this.liquidoPago = liquidacion.getLiquidoPago() != null ? liquidacion.getLiquidoPago() : BigDecimal.ZERO;
        
        this.fechaLiquidacionShow = liquidacion.getFechaLiquidacionShow();
        this.nomino = liquidacion.getNomino();
        this.finalizo = liquidacion.getFinalizo();
        this.liquido = liquidacion.getLiquido();
        this.glosaNomina = liquidacion.getGlosaNomina();
        this.glosaFaena = liquidacion.getGlosaFaena();
    }

    public static LiquidacionResponseDto from(Liquidacion liquidacion) {
        return new LiquidacionResponseDto(liquidacion);
    }

    // Getters y Setters
    public Long getCnomCod() { return cnomCod; }
    public void setCnomCod(Long cnomCod) { this.cnomCod = cnomCod; }

    public LocalDate getFaeFechaInicial() { return faeFechaInicial; }
    public void setFaeFechaInicial(LocalDate faeFechaInicial) { this.faeFechaInicial = faeFechaInicial; }

    public String getFaeFechaInicialShow() { return faeFechaInicialShow; }
    public void setFaeFechaInicialShow(String faeFechaInicialShow) { this.faeFechaInicialShow = faeFechaInicialShow; }

    public Long getTraFicha() { return traFicha; }
    public void setTraFicha(Long traFicha) { this.traFicha = traFicha; }

    public String getTrabajadorNombre() { return trabajadorNombre; }
    public void setTrabajadorNombre(String trabajadorNombre) { this.trabajadorNombre = trabajadorNombre; }

    public String getTrabajadorApellidoP() { return trabajadorApellidoP; }
    public void setTrabajadorApellidoP(String trabajadorApellidoP) { this.trabajadorApellidoP = trabajadorApellidoP; }

    public String getTrabajadorApellidoM() { return trabajadorApellidoM; }
    public void setTrabajadorApellidoM(String trabajadorApellidoM) { this.trabajadorApellidoM = trabajadorApellidoM; }

    public String getCarNombre() { return carNombre; }
    public void setCarNombre(String carNombre) { this.carNombre = carNombre; }

    public String getSinNombre() { return sinNombre; }
    public void setSinNombre(String sinNombre) { this.sinNombre = sinNombre; }

    public String getTraContratoProvision() { return traContratoProvision; }
    public void setTraContratoProvision(String traContratoProvision) { this.traContratoProvision = traContratoProvision; }

    public Long getFaeCod() { return faeCod; }
    public void setFaeCod(Long faeCod) { this.faeCod = faeCod; }

    public String getTfaDescripcion() { return tfaDescripcion; }
    public void setTfaDescripcion(String tfaDescripcion) { this.tfaDescripcion = tfaDescripcion; }

    public String getNavDescripcion() { return navDescripcion; }
    public void setNavDescripcion(String navDescripcion) { this.navDescripcion = navDescripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getPermiso() { return permiso; }
    public void setPermiso(String permiso) { this.permiso = permiso; }

    public BigDecimal getValorBase() { return valorBase; }
    public void setValorBase(BigDecimal valorBase) { this.valorBase = valorBase; }

    public BigDecimal getNumHorasExtra() { return numHorasExtra; }
    public void setNumHorasExtra(BigDecimal numHorasExtra) { this.numHorasExtra = numHorasExtra; }

    public BigDecimal getValorHoraExtra() { return valorHoraExtra; }
    public void setValorHoraExtra(BigDecimal valorHoraExtra) { this.valorHoraExtra = valorHoraExtra; }

    public BigDecimal getTonelajeOCamion() { return tonelajeOCamion; }
    public void setTonelajeOCamion(BigDecimal tonelajeOCamion) { this.tonelajeOCamion = tonelajeOCamion; }

    public BigDecimal getTonPagado() { return tonPagado; }
    public void setTonPagado(BigDecimal tonPagado) { this.tonPagado = tonPagado; }

    public BigDecimal getOtrosImponibles() { return otrosImponibles; }
    public void setOtrosImponibles(BigDecimal otrosImponibles) { this.otrosImponibles = otrosImponibles; }

    public BigDecimal getTotalImponible() { return totalImponible; }
    public void setTotalImponible(BigDecimal totalImponible) { this.totalImponible = totalImponible; }

    public BigDecimal getColacion() { return colacion; }
    public void setColacion(BigDecimal colacion) { this.colacion = colacion; }

    public BigDecimal getMovilizacion() { return movilizacion; }
    public void setMovilizacion(BigDecimal movilizacion) { this.movilizacion = movilizacion; }

    public BigDecimal getMovCol() { return movCol; }
    public void setMovCol(BigDecimal movCol) { this.movCol = movCol; }

    public BigDecimal getColAdicional() { return colAdicional; }
    public void setColAdicional(BigDecimal colAdicional) { this.colAdicional = colAdicional; }

    public BigDecimal getOtrosNoImponibles() { return otrosNoImponibles; }
    public void setOtrosNoImponibles(BigDecimal otrosNoImponibles) { this.otrosNoImponibles = otrosNoImponibles; }

    public BigDecimal getTotalHaber() { return totalHaber; }
    public void setTotalHaber(BigDecimal totalHaber) { this.totalHaber = totalHaber; }

    public String getAfpNombre() { return afpNombre; }
    public void setAfpNombre(String afpNombre) { this.afpNombre = afpNombre; }

    public String getAfpPorcMasComision() { return afpPorcMasComision; }
    public void setAfpPorcMasComision(String afpPorcMasComision) { this.afpPorcMasComision = afpPorcMasComision; }

    public BigDecimal getDescAfp() { return descAfp; }
    public void setDescAfp(BigDecimal descAfp) { this.descAfp = descAfp; }

    public BigDecimal getDescC2() { return descC2; }
    public void setDescC2(BigDecimal descC2) { this.descC2 = descC2; }

    public BigDecimal getDescApv() { return descApv; }
    public void setDescApv(BigDecimal descApv) { this.descApv = descApv; }

    public String getSalNombre() { return salNombre; }
    public void setSalNombre(String salNombre) { this.salNombre = salNombre; }

    public BigDecimal getDescSalud() { return descSalud; }
    public void setDescSalud(BigDecimal descSalud) { this.descSalud = descSalud; }

    public BigDecimal getImptoUnico() { return imptoUnico; }
    public void setImptoUnico(BigDecimal imptoUnico) { this.imptoUnico = imptoUnico; }

    public BigDecimal getPtmoMuelle() { return ptmoMuelle; }
    public void setPtmoMuelle(BigDecimal ptmoMuelle) { this.ptmoMuelle = ptmoMuelle; }

    public BigDecimal getPtmoCaja() { return ptmoCaja; }
    public void setPtmoCaja(BigDecimal ptmoCaja) { this.ptmoCaja = ptmoCaja; }

    public BigDecimal getCuotaSindicato() { return cuotaSindicato; }
    public void setCuotaSindicato(BigDecimal cuotaSindicato) { this.cuotaSindicato = cuotaSindicato; }

    public BigDecimal getOtrosDescuentos() { return otrosDescuentos; }
    public void setOtrosDescuentos(BigDecimal otrosDescuentos) { this.otrosDescuentos = otrosDescuentos; }

    public BigDecimal getTotalDescuentos() { return totalDescuentos; }
    public void setTotalDescuentos(BigDecimal totalDescuentos) { this.totalDescuentos = totalDescuentos; }

    public BigDecimal getLiquidoPago() { return liquidoPago; }
    public void setLiquidoPago(BigDecimal liquidoPago) { this.liquidoPago = liquidoPago; }

    public String getFechaLiquidacionShow() { return fechaLiquidacionShow; }
    public void setFechaLiquidacionShow(String fechaLiquidacionShow) { this.fechaLiquidacionShow = fechaLiquidacionShow; }

    public String getNomino() { return nomino; }
    public void setNomino(String nomino) { this.nomino = nomino; }

    public String getFinalizo() { return finalizo; }
    public void setFinalizo(String finalizo) { this.finalizo = finalizo; }

    public String getLiquido() { return liquido; }
    public void setLiquido(String liquido) { this.liquido = liquido; }

    public String getGlosaNomina() { return glosaNomina; }
    public void setGlosaNomina(String glosaNomina) { this.glosaNomina = glosaNomina; }

    public String getGlosaFaena() { return glosaFaena; }
    public void setGlosaFaena(String glosaFaena) { this.glosaFaena = glosaFaena; }
}
