package com.muellespenco.seven_backend.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "v_detalle_pago_x_periodo", schema = "eventuales")
public class Liquidacion implements Serializable {

    @Id
    @Column(name = "cnom_cod")
    private Long cnomCod;

    @Column(name = "fae_fecha_inicial")
    private LocalDate faeFechaInicial;

    @Column(name = "fae_fecha_inicial_show")
    private String faeFechaInicialShow;

    @Column(name = "tra_ficha")
    private Long traFicha;

    @Column(name = "tra_ficha_trabajador")
    private Long traFichaTrabajador;

    @Column(name = "tra_apellidop")
    private String traApellidoP;

    @Column(name = "tra_apellidom")
    private String traApellidoM;

    @Column(name = "tra_nombre")
    private String traNombre;

    @Column(name = "sin_cod")
    private Integer sinCod;

    @Column(name = "sin_nombre")
    private String sinNombre;

    @Column(name = "tra_contrato_provision")
    private String traContratoProvision;

    @Column(name = "car_cod")
    private Long carCod;

    @Column(name = "car_nombre")
    private String carNombre;

    @Column(name = "fae_cod")
    private Long faeCod;

    @Column(name = "fae_tipo_faena")
    private Integer faeTipoFaena;

    @Column(name = "tfa_descripcion")
    private String tfaDescripcion;

    @Column(name = "fae_nave_cod")
    private Integer faeNaveCod;

    @Column(name = "nav_descripcion")
    private String navDescripcion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "permiso")
    private String permiso;

    // Haberes
    @Column(name = "valor_base")
    private BigDecimal valorBase;

    @Column(name = "num_horas_extra")
    private BigDecimal numHorasExtra;

    @Column(name = "valor_hora_extra")
    private BigDecimal valorHoraExtra;

    @Column(name = "tonelaje_o_camion")
    private BigDecimal tonelajeOCamion;

    @Column(name = "tonpagado")
    private BigDecimal tonPagado;

    @Column(name = "otros_imponibles")
    private BigDecimal otrosImponibles;

    @Column(name = "total_imponible")
    private BigDecimal totalImponible;

    @Column(name = "fecha_liquidacion_show")
    private String fechaLiquidacionShow;

    @Column(name = "fecha_liquidacion")
    private String fechaLiquidacion;

    // Haberes no imponibles
    @Column(name = "colacion")
    private BigDecimal colacion;

    @Column(name = "movilizacion")
    private BigDecimal movilizacion;

    @Column(name = "mov_col")
    private BigDecimal movCol;

    @Column(name = "col_adicional")
    private BigDecimal colAdicional;

    @Column(name = "otros_no_imponibles")
    private BigDecimal otrosNoImponibles;

    @Column(name = "total_haber")
    private BigDecimal totalHaber;

    // Descuentos previsionales
    @Column(name = "afp_cod")
    private Integer afpCod;

    @Column(name = "afp_nombre")
    private String afpNombre;

    @Column(name = "afp_porc_mas_comision")
    private String afpPorcMasComision;

    @Column(name = "desc_afp")
    private BigDecimal descAfp;

    @Column(name = "desc_c2")
    private BigDecimal descC2;

    @Column(name = "desc_apv")
    private BigDecimal descApv;

    @Column(name = "sal_cod")
    private Integer salCod;

    @Column(name = "sal_nombre")
    private String salNombre;

    @Column(name = "desc_salud")
    private BigDecimal descSalud;

    @Column(name = "impto_unico")
    private BigDecimal imptoUnico;

    // Otros descuentos
    @Column(name = "ptmo_muelle")
    private BigDecimal ptmoMuelle;

    @Column(name = "ptmo_caja")
    private BigDecimal ptmoCaja;

    @Column(name = "cuota_sindicato")
    private BigDecimal cuotaSindicato;

    @Column(name = "otros_descuentos")
    private BigDecimal otrosDescuentos;

    @Column(name = "total_descuentos")
    private BigDecimal totalDescuentos;

    // Líquido
    @Column(name = "liquido_pago")
    private BigDecimal liquidoPago;

    @Column(name = "nomino")
    private String nomino;

    @Column(name = "finalizo")
    private String finalizo;

    @Column(name = "liquido")
    private String liquido;

    @Column(name = "glosa_nomina")
    private String glosaNomina;

    @Column(name = "glosa_faena")
    private String glosaFaena;

    // Constructor vacío
    public Liquidacion() {}

    // Getters y Setters
    public Long getCnomCod() {
        return cnomCod;
    }

    public void setCnomCod(Long cnomCod) {
        this.cnomCod = cnomCod;
    }

    public LocalDate getFaeFechaInicial() {
        return faeFechaInicial;
    }

    public void setFaeFechaInicial(LocalDate faeFechaInicial) {
        this.faeFechaInicial = faeFechaInicial;
    }

    public String getFaeFechaInicialShow() {
        return faeFechaInicialShow;
    }

    public void setFaeFechaInicialShow(String faeFechaInicialShow) {
        this.faeFechaInicialShow = faeFechaInicialShow;
    }

    public Long getTraFicha() {
        return traFicha;
    }

    public void setTraFicha(Long traFicha) {
        this.traFicha = traFicha;
    }

    public Long getTraFichaTrabajador() {
        return traFichaTrabajador;
    }

    public void setTraFichaTrabajador(Long traFichaTrabajador) {
        this.traFichaTrabajador = traFichaTrabajador;
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

    public String getTraNombre() {
        return traNombre;
    }

    public void setTraNombre(String traNombre) {
        this.traNombre = traNombre;
    }

    public Integer getSinCod() {
        return sinCod;
    }

    public void setSinCod(Integer sinCod) {
        this.sinCod = sinCod;
    }

    public String getSinNombre() {
        return sinNombre;
    }

    public void setSinNombre(String sinNombre) {
        this.sinNombre = sinNombre;
    }

    public String getTraContratoProvision() {
        return traContratoProvision;
    }

    public void setTraContratoProvision(String traContratoProvision) {
        this.traContratoProvision = traContratoProvision;
    }

    public Long getCarCod() {
        return carCod;
    }

    public void setCarCod(Long carCod) {
        this.carCod = carCod;
    }

    public String getCarNombre() {
        return carNombre;
    }

    public void setCarNombre(String carNombre) {
        this.carNombre = carNombre;
    }

    public Long getFaeCod() {
        return faeCod;
    }

    public void setFaeCod(Long faeCod) {
        this.faeCod = faeCod;
    }

    public Integer getFaeTipoFaena() {
        return faeTipoFaena;
    }

    public void setFaeTipoFaena(Integer faeTipoFaena) {
        this.faeTipoFaena = faeTipoFaena;
    }

    public String getTfaDescripcion() {
        return tfaDescripcion;
    }

    public void setTfaDescripcion(String tfaDescripcion) {
        this.tfaDescripcion = tfaDescripcion;
    }

    public Integer getFaeNaveCod() {
        return faeNaveCod;
    }

    public void setFaeNaveCod(Integer faeNaveCod) {
        this.faeNaveCod = faeNaveCod;
    }

    public String getNavDescripcion() {
        return navDescripcion;
    }

    public void setNavDescripcion(String navDescripcion) {
        this.navDescripcion = navDescripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public BigDecimal getValorBase() {
        return valorBase;
    }

    public void setValorBase(BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    public BigDecimal getNumHorasExtra() {
        return numHorasExtra;
    }

    public void setNumHorasExtra(BigDecimal numHorasExtra) {
        this.numHorasExtra = numHorasExtra;
    }

    public BigDecimal getValorHoraExtra() {
        return valorHoraExtra;
    }

    public void setValorHoraExtra(BigDecimal valorHoraExtra) {
        this.valorHoraExtra = valorHoraExtra;
    }

    public BigDecimal getTonelajeOCamion() {
        return tonelajeOCamion;
    }

    public void setTonelajeOCamion(BigDecimal tonelajeOCamion) {
        this.tonelajeOCamion = tonelajeOCamion;
    }

    public BigDecimal getTonPagado() {
        return tonPagado;
    }

    public void setTonPagado(BigDecimal tonPagado) {
        this.tonPagado = tonPagado;
    }

    public BigDecimal getOtrosImponibles() {
        return otrosImponibles;
    }

    public void setOtrosImponibles(BigDecimal otrosImponibles) {
        this.otrosImponibles = otrosImponibles;
    }

    public BigDecimal getTotalImponible() {
        return totalImponible;
    }

    public void setTotalImponible(BigDecimal totalImponible) {
        this.totalImponible = totalImponible;
    }

    public String getFechaLiquidacionShow() {
        return fechaLiquidacionShow;
    }

    public void setFechaLiquidacionShow(String fechaLiquidacionShow) {
        this.fechaLiquidacionShow = fechaLiquidacionShow;
    }

    public String getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(String fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
    }

    public BigDecimal getColacion() {
        return colacion;
    }

    public void setColacion(BigDecimal colacion) {
        this.colacion = colacion;
    }

    public BigDecimal getMovilizacion() {
        return movilizacion;
    }

    public void setMovilizacion(BigDecimal movilizacion) {
        this.movilizacion = movilizacion;
    }

    public BigDecimal getMovCol() {
        return movCol;
    }

    public void setMovCol(BigDecimal movCol) {
        this.movCol = movCol;
    }

    public BigDecimal getColAdicional() {
        return colAdicional;
    }

    public void setColAdicional(BigDecimal colAdicional) {
        this.colAdicional = colAdicional;
    }

    public BigDecimal getOtrosNoImponibles() {
        return otrosNoImponibles;
    }

    public void setOtrosNoImponibles(BigDecimal otrosNoImponibles) {
        this.otrosNoImponibles = otrosNoImponibles;
    }

    public BigDecimal getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(BigDecimal totalHaber) {
        this.totalHaber = totalHaber;
    }

    public Integer getAfpCod() {
        return afpCod;
    }

    public void setAfpCod(Integer afpCod) {
        this.afpCod = afpCod;
    }

    public String getAfpNombre() {
        return afpNombre;
    }

    public void setAfpNombre(String afpNombre) {
        this.afpNombre = afpNombre;
    }

    public String getAfpPorcMasComision() {
        return afpPorcMasComision;
    }

    public void setAfpPorcMasComision(String afpPorcMasComision) {
        this.afpPorcMasComision = afpPorcMasComision;
    }

    public BigDecimal getDescAfp() {
        return descAfp;
    }

    public void setDescAfp(BigDecimal descAfp) {
        this.descAfp = descAfp;
    }

    public BigDecimal getDescC2() {
        return descC2;
    }

    public void setDescC2(BigDecimal descC2) {
        this.descC2 = descC2;
    }

    public BigDecimal getDescApv() {
        return descApv;
    }

    public void setDescApv(BigDecimal descApv) {
        this.descApv = descApv;
    }

    public Integer getSalCod() {
        return salCod;
    }

    public void setSalCod(Integer salCod) {
        this.salCod = salCod;
    }

    public String getSalNombre() {
        return salNombre;
    }

    public void setSalNombre(String salNombre) {
        this.salNombre = salNombre;
    }

    public BigDecimal getDescSalud() {
        return descSalud;
    }

    public void setDescSalud(BigDecimal descSalud) {
        this.descSalud = descSalud;
    }

    public BigDecimal getImptoUnico() {
        return imptoUnico;
    }

    public void setImptoUnico(BigDecimal imptoUnico) {
        this.imptoUnico = imptoUnico;
    }

    public BigDecimal getPtmoMuelle() {
        return ptmoMuelle;
    }

    public void setPtmoMuelle(BigDecimal ptmoMuelle) {
        this.ptmoMuelle = ptmoMuelle;
    }

    public BigDecimal getPtmoCaja() {
        return ptmoCaja;
    }

    public void setPtmoCaja(BigDecimal ptmoCaja) {
        this.ptmoCaja = ptmoCaja;
    }

    public BigDecimal getCuotaSindicato() {
        return cuotaSindicato;
    }

    public void setCuotaSindicato(BigDecimal cuotaSindicato) {
        this.cuotaSindicato = cuotaSindicato;
    }

    public BigDecimal getOtrosDescuentos() {
        return otrosDescuentos;
    }

    public void setOtrosDescuentos(BigDecimal otrosDescuentos) {
        this.otrosDescuentos = otrosDescuentos;
    }

    public BigDecimal getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(BigDecimal totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public BigDecimal getLiquidoPago() {
        return liquidoPago;
    }

    public void setLiquidoPago(BigDecimal liquidoPago) {
        this.liquidoPago = liquidoPago;
    }

    public String getNomino() {
        return nomino;
    }

    public void setNomino(String nomino) {
        this.nomino = nomino;
    }

    public String getFinalizo() {
        return finalizo;
    }

    public void setFinalizo(String finalizo) {
        this.finalizo = finalizo;
    }

    public String getLiquido() {
        return liquido;
    }

    public void setLiquido(String liquido) {
        this.liquido = liquido;
    }

    public String getGlosaNomina() {
        return glosaNomina;
    }

    public void setGlosaNomina(String glosaNomina) {
        this.glosaNomina = glosaNomina;
    }

    public String getGlosaFaena() {
        return glosaFaena;
    }

    public void setGlosaFaena(String glosaFaena) {
        this.glosaFaena = glosaFaena;
    }
}