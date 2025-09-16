package com.muellespenco.seven_backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "v_full_trabajador", schema = "eventuales")
public class Trabajador {
    
    @Id
    @Column(name = "tra_ficha")
    private Long traFicha;
    
    @Column(name = "tra_ficha_trabajador")
    private Long traFichaTrabajador;
    
    @Column(name = "tra_rut")
    private String traRut;

    @Column(name = "tra_nombre")
    private String traNombre;

    @Column(name = "tra_fecha_ing_mdp")
    private LocalDate traFechaIngMdp;

    @Column(name = "show_tra_fecha_ing_mdp")
    private String showTraFechaIngMdp;

    @Column(name = "tra_apellidop")
    private String traApellidoP;

    @Column(name = "tra_apellidom")
    private String traApellidoM;

    @Column(name = "tra_fecha_nacimiento")
    private LocalDate traFechaNacimiento;

    @Column(name = "show_tra_fecha_nacimiento")
    private String showTraFechaNacimiento;

    @Column(name = "tra_direccion")
    private String traDireccion;

    @Column(name = "tra_movil")
    private String traMovil;

    @Column(name = "tra_educacion_cod")
    private Integer traEducacionCod;

    @Column(name = "edu_nombre")
    private String eduNombre;

    @Column(name = "tra_sindicato_cod")
    private Integer traSindicatoCod;

    @Column(name = "sin_nombre")
    private String sinNombre;

    @Column(name = "tra_licencia_cod")
    private Integer traLicenciaCod;

    @Column(name = "lic_tipo")
    private String licTipo;

    @Column(name = "lic_descripcion")
    private String licDescripcion;

    @Column(name = "tra_cargas_simple")
    private Integer traCargasSimple;

    @Column(name = "tra_carga_por_invalides")
    private Integer traCargaPorInvalides;

    @Column(name = "tra_contrato_provision")
    private Integer traContratoProvision;

    @Column(name = "tra_acceso_vehicular")
    private Integer traAccesoVehicular;

    @Column(name = "tra_curso_rendido")
    private Integer traCursoRendido;

    @Column(name = "tra_pase_portuario")
    private Integer traPasePortuario;

    @Column(name = "tra_reglamento_interno")
    private Integer traReglamentoInterno;

    @Column(name = "tra_talla_ropa")
    private String traTallaRopa;

    @Column(name = "tra_num_calzado")
    private String traNumCalzado;

    @Column(name = "tra_libreta_inp")
    private String traLibretaInp;

    @Column(name = "tra_subsidio_cesantia")
    private Integer traSubsidioCesantia;

    @Column(name = "tra_vencimiento_pase")
    private LocalDate traVencimientoPase;

    @Column(name = "show_tra_vencimiento_pase")
    private String showTraVencimientoPase;

    @Column(name = "tra_afp_cod")
    private Integer traAfpCod;

    @Column(name = "afp_nombre")
    private String afpNombre;

    @Column(name = "tra_afp_apv_cod")
    private Integer traAfpApvCod;

    @Column(name = "afp_apv")
    private String afpApv;

    @Column(name = "tra_ncontrato_apv")
    private String traNcontratoApv;

    @Column(name = "tra_apv_tipo_valor")
    private Integer traApvTipoValor;

    @Column(name = "tipo_valor_apv")
    private String tipoValorApv;

    @Column(name = "tra_afp_apv_porc")
    private BigDecimal traAfpApvPorc;

    @Column(name = "afp_apv_porc")
    private String afpApvPorc;

    @Column(name = "tra_forma_pago_apv")
    private String traFormaPagoApv;

    @Column(name = "tra_afp_c2_cod")
    private Integer traAfpC2Cod;

    @Column(name = "afp_c2")
    private String afpC2;

    @Column(name = "tra_c2_tipo_valor")
    private Integer traC2TipoValor;

    @Column(name = "tipo_valor_c2")
    private String tipoValorC2;

    @Column(name = "tra_afp_c2_porc")
    private BigDecimal traAfpC2Porc;

    @Column(name = "afp_c2_porc")
    private String afpC2Porc;

    @Column(name = "tra_salud_cod")
    private Integer traSaludCod;

    @Column(name = "sal_nombre")
    private String salNombre;

    @Column(name = "tra_uf_isapre")
    private BigDecimal traUfIsapre;

    @Column(name = "uf_isapre")
    private String ufIsapre;

    @Column(name = "tra_foto")
    private String traFoto;

    @Column(name = "tra_forma_pago_cod")
    private Integer traFormaPagoCod;

    @Column(name = "fopa_descripcion")
    private String fopaDescripcion;

    @Column(name = "ban_cod")
    private Integer banCod;

    @Column(name = "ban_nombre")
    private String banNombre;

    @Column(name = "tra_ncuenta")
    private String traNcuenta;

    @Column(name = "tra_vig_desde")
    private LocalDate traVigDesde;

    @Column(name = "show_tra_vig_desde")
    private String showTraVigDesde;

    @Column(name = "tra_vig_hasta")
    private LocalDate traVigHasta;

    @Column(name = "show_tra_vig_hasta")
    private String showTraVigHasta;

    @Column(name = "tra_user_ultimo")
    private Integer traUserUltimo;

    @Column(name = "usu_user")
    private String usuUser;

    @Column(name = "tra_fecha_ultimo")
    private LocalDateTime traFechaUltimo;

    @Column(name = "show_tra_fecha_ultimo")
    private String showTraFechaUltimo;

    @Column(name = "tra_sexo")
    private Integer traSexo;

    @Column(name = "tse_nombre")
    private String tseNombre;

    @Column(name = "nac_cod")
    private Integer nacCod;

    @Column(name = "nac_descripcion")
    private String nacDescripcion;

    @Column(name = "ciu_cod")
    private Integer ciuCod;

    @Column(name = "ciu_nombre")
    private String ciuNombre;

    @Column(name = "tra_numcasa")
    private String traNumCasa;

    @Column(name = "tra_depto")
    private String traDepto;

    @Column(name = "tra_block")
    private String traBlock;

    @Column(name = "tra_email")
    private String traEmail;

    @Column(name = "tra_estadocivil")
    private Integer traEstadoCivil;

    @Column(name = "eci_nombre")
    private String eciNombre;

    @Column(name = "tra_pensionado")
    private Integer traPensionado;

    @Column(name = "pensionado")
    private String pensionado;

    @Column(name = "tra_cronico")
    private Integer traCronico;

    @Column(name = "cronico")
    private String cronico;

    @Column(name = "tra_enfermedad")
    private String traEnfermedad;

    @Column(name = "tra_req_contrato")
    private Integer traReqContrato;

    @Column(name = "req_contrato")
    private String reqContrato;
    
    // Constructor vacío
    public Trabajador() {}
    
    // Métodos de utilidad
    public String getNombreCompleto() {
        StringBuilder nombreCompleto = new StringBuilder();
        if (traNombre != null) nombreCompleto.append(traNombre);
        if (traApellidoP != null) nombreCompleto.append(" ").append(traApellidoP);
        if (traApellidoM != null) nombreCompleto.append(" ").append(traApellidoM);
        return nombreCompleto.toString().trim();
    }
    
    public boolean isVigente() {
        if (traVigDesde == null || traVigHasta == null) return false;
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(traVigDesde) && !hoy.isAfter(traVigHasta);
    }
    
    public int getEdad() {
        if (traFechaNacimiento == null) return 0;
        return LocalDate.now().getYear() - traFechaNacimiento.getYear();
    }
    
    public String getDireccionCompleta() {
        StringBuilder direccion = new StringBuilder();
        if (traDireccion != null) direccion.append(traDireccion);
        if (traNumCasa != null) direccion.append(", Casa ").append(traNumCasa);
        if (traDepto != null) direccion.append(", Depto ").append(traDepto);
        if (traBlock != null) direccion.append(", Block ").append(traBlock);
        return direccion.toString();
    }
    
    // Getters y Setters (generados automáticamente - incluyo solo algunos como ejemplo)
    public Long getTraFichaTrabajador() { return traFichaTrabajador; }
    public void setTraFichaTrabajador(Long traFichaTrabajador) { this.traFichaTrabajador = traFichaTrabajador; }

    public Long getTraFicha() { return traFicha; }
    public void setTraFicha(Long traFicha) { this.traFicha = traFicha; }

    public String getTraRut() { return traRut; }
    public void setTraRut(String traRut) { this.traRut = traRut; }

    public String getTraNombre() { return traNombre; }
    public void setTraNombre(String traNombre) { this.traNombre = traNombre; }

    public LocalDate getTraFechaIngMdp() { return traFechaIngMdp; }
    public void setTraFechaIngMdp(LocalDate traFechaIngMdp) { this.traFechaIngMdp = traFechaIngMdp; }

    public String getShowTraFechaIngMdp() { return showTraFechaIngMdp; }
    public void setShowTraFechaIngMdp(String showTraFechaIngMdp) { this.showTraFechaIngMdp = showTraFechaIngMdp; }

    public String getTraApellidoP() { return traApellidoP; }
    public void setTraApellidoP(String traApellidoP) { this.traApellidoP = traApellidoP; }

    public String getTraApellidoM() { return traApellidoM; }
    public void setTraApellidoM(String traApellidoM) { this.traApellidoM = traApellidoM; }

    public LocalDate getTraFechaNacimiento() { return traFechaNacimiento; }
    public void setTraFechaNacimiento(LocalDate traFechaNacimiento) { this.traFechaNacimiento = traFechaNacimiento; }

    public String getShowTraFechaNacimiento() { return showTraFechaNacimiento; }
    public void setShowTraFechaNacimiento(String showTraFechaNacimiento) { this.showTraFechaNacimiento = showTraFechaNacimiento; }

    public String getTraDireccion() { return traDireccion; }
    public void setTraDireccion(String traDireccion) { this.traDireccion = traDireccion; }

    public String getTraMovil() { return traMovil; }
    public void setTraMovil(String traMovil) { this.traMovil = traMovil; }

    public Integer getTraEducacionCod() { return traEducacionCod; }
    public void setTraEducacionCod(Integer traEducacionCod) { this.traEducacionCod = traEducacionCod; }

    public String getEduNombre() { return eduNombre; }
    public void setEduNombre(String eduNombre) { this.eduNombre = eduNombre; }

    public Integer getTraSindicatoCod() { return traSindicatoCod; }
    public void setTraSindicatoCod(Integer traSindicatoCod) { this.traSindicatoCod = traSindicatoCod; }

    public String getSinNombre() { return sinNombre; }
    public void setSinNombre(String sinNombre) { this.sinNombre = sinNombre; }

    public Integer getTraLicenciaCod() { return traLicenciaCod; }
    public void setTraLicenciaCod(Integer traLicenciaCod) { this.traLicenciaCod = traLicenciaCod; }

    public String getLicTipo() { return licTipo; }
    public void setLicTipo(String licTipo) { this.licTipo = licTipo; }

    public String getLicDescripcion() { return licDescripcion; }
    public void setLicDescripcion(String licDescripcion) { this.licDescripcion = licDescripcion; }

    public Integer getTraCargasSimple() { return traCargasSimple; }
    public void setTraCargasSimple(Integer traCargasSimple) { this.traCargasSimple = traCargasSimple; }

    public Integer getTraCargaPorInvalides() { return traCargaPorInvalides; }
    public void setTraCargaPorInvalides(Integer traCargaPorInvalides) { this.traCargaPorInvalides = traCargaPorInvalides; }

    public Integer getTraContratoProvision() { return traContratoProvision; }
    public void setTraContratoProvision(Integer traContratoProvision) { this.traContratoProvision = traContratoProvision; }

    public Integer getTraAccesoVehicular() { return traAccesoVehicular; }
    public void setTraAccesoVehicular(Integer traAccesoVehicular) { this.traAccesoVehicular = traAccesoVehicular; }

    public Integer getTraCursoRendido() { return traCursoRendido; }
    public void setTraCursoRendido(Integer traCursoRendido) { this.traCursoRendido = traCursoRendido; }

    public Integer getTraPasePortuario() { return traPasePortuario; }
    public void setTraPasePortuario(Integer traPasePortuario) { this.traPasePortuario = traPasePortuario; }

    public Integer getTraReglamentoInterno() { return traReglamentoInterno; }
    public void setTraReglamentoInterno(Integer traReglamentoInterno) { this.traReglamentoInterno = traReglamentoInterno; }

    public String getTraTallaRopa() { return traTallaRopa; }
    public void setTraTallaRopa(String traTallaRopa) { this.traTallaRopa = traTallaRopa; }

    public String getTraNumCalzado() { return traNumCalzado; }
    public void setTraNumCalzado(String traNumCalzado) { this.traNumCalzado = traNumCalzado; }

    public String getTraLibretaInp() { return traLibretaInp; }
    public void setTraLibretaInp(String traLibretaInp) { this.traLibretaInp = traLibretaInp; }

    public Integer getTraSubsidioCesantia() { return traSubsidioCesantia; }
    public void setTraSubsidioCesantia(Integer traSubsidioCesantia) { this.traSubsidioCesantia = traSubsidioCesantia; }

    public LocalDate getTraVencimientoPase() { return traVencimientoPase; }
    public void setTraVencimientoPase(LocalDate traVencimientoPase) { this.traVencimientoPase = traVencimientoPase; }

    public String getShowTraVencimientoPase() { return showTraVencimientoPase; }
    public void setShowTraVencimientoPase(String showTraVencimientoPase) { this.showTraVencimientoPase = showTraVencimientoPase; }

    public Integer getTraAfpCod() { return traAfpCod; }
    public void setTraAfpCod(Integer traAfpCod) { this.traAfpCod = traAfpCod; }

    public String getAfpNombre() { return afpNombre; }
    public void setAfpNombre(String afpNombre) { this.afpNombre = afpNombre; }

    public Integer getTraAfpApvCod() { return traAfpApvCod; }
    public void setTraAfpApvCod(Integer traAfpApvCod) { this.traAfpApvCod = traAfpApvCod; }

    public String getAfpApv() { return afpApv; }
    public void setAfpApv(String afpApv) { this.afpApv = afpApv; }

    public String getTraNcontratoApv() { return traNcontratoApv; }
    public void setTraNcontratoApv(String traNcontratoApv) { this.traNcontratoApv = traNcontratoApv; }

    public Integer getTraApvTipoValor() { return traApvTipoValor; }
    public void setTraApvTipoValor(Integer traApvTipoValor) { this.traApvTipoValor = traApvTipoValor; }

    public String getTipoValorApv() { return tipoValorApv; }
    public void setTipoValorApv(String tipoValorApv) { this.tipoValorApv = tipoValorApv; }

    public BigDecimal getTraAfpApvPorc() { return traAfpApvPorc; }
    public void setTraAfpApvPorc(BigDecimal traAfpApvPorc) { this.traAfpApvPorc = traAfpApvPorc; }

    public String getAfpApvPorc() { return afpApvPorc; }
    public void setAfpApvPorc(String afpApvPorc) { this.afpApvPorc = afpApvPorc; }

    public String getTraFormaPagoApv() { return traFormaPagoApv; }
    public void setTraFormaPagoApv(String traFormaPagoApv) { this.traFormaPagoApv = traFormaPagoApv; }

    public Integer getTraAfpC2Cod() { return traAfpC2Cod; }
    public void setTraAfpC2Cod(Integer traAfpC2Cod) { this.traAfpC2Cod = traAfpC2Cod; }

    public String getAfpC2() { return afpC2; }
    public void setAfpC2(String afpC2) { this.afpC2 = afpC2; }

    public Integer getTraC2TipoValor() { return traC2TipoValor; }
    public void setTraC2TipoValor(Integer traC2TipoValor) { this.traC2TipoValor = traC2TipoValor; }

    public String getTipoValorC2() { return tipoValorC2; }
    public void setTipoValorC2(String tipoValorC2) { this.tipoValorC2 = tipoValorC2; }

    public BigDecimal getTraAfpC2Porc() { return traAfpC2Porc; }
    public void setTraAfpC2Porc(BigDecimal traAfpC2Porc) { this.traAfpC2Porc = traAfpC2Porc; }

    public String getAfpC2Porc() { return afpC2Porc; }
    public void setAfpC2Porc(String afpC2Porc) { this.afpC2Porc = afpC2Porc; }

    public Integer getTraSaludCod() { return traSaludCod; }
    public void setTraSaludCod(Integer traSaludCod) { this.traSaludCod = traSaludCod; }

    public String getSalNombre() { return salNombre; }
    public void setSalNombre(String salNombre) { this.salNombre = salNombre; }

    public BigDecimal getTraUfIsapre() { return traUfIsapre; }
    public void setTraUfIsapre(BigDecimal traUfIsapre) { this.traUfIsapre = traUfIsapre; }

    public String getUfIsapre() { return ufIsapre; }
    public void setUfIsapre(String ufIsapre) { this.ufIsapre = ufIsapre; }

    public String getTraFoto() { return traFoto; }
    public void setTraFoto(String traFoto) { this.traFoto = traFoto; }

    public Integer getTraFormaPagoCod() { return traFormaPagoCod; }
    public void setTraFormaPagoCod(Integer traFormaPagoCod) { this.traFormaPagoCod = traFormaPagoCod; }

    public String getFopaDescripcion() { return fopaDescripcion; }
    public void setFopaDescripcion(String fopaDescripcion) { this.fopaDescripcion = fopaDescripcion; }

    public Integer getBanCod() { return banCod; }
    public void setBanCod(Integer banCod) { this.banCod = banCod; }

    public String getBanNombre() { return banNombre; }
    public void setBanNombre(String banNombre) { this.banNombre = banNombre; }

    public String getTraNcuenta() { return traNcuenta; }
    public void setTraNcuenta(String traNcuenta) { this.traNcuenta = traNcuenta; }

    public LocalDate getTraVigDesde() { return traVigDesde; }
    public void setTraVigDesde(LocalDate traVigDesde) { this.traVigDesde = traVigDesde; }

    public String getShowTraVigDesde() { return showTraVigDesde; }
    public void setShowTraVigDesde(String showTraVigDesde) { this.showTraVigDesde = showTraVigDesde; }

    public LocalDate getTraVigHasta() { return traVigHasta; }
    public void setTraVigHasta(LocalDate traVigHasta) { this.traVigHasta = traVigHasta; }

    public String getShowTraVigHasta() { return showTraVigHasta; }
    public void setShowTraVigHasta(String showTraVigHasta) { this.showTraVigHasta = showTraVigHasta; }

    public Integer getTraUserUltimo() { return traUserUltimo; }
    public void setTraUserUltimo(Integer traUserUltimo) { this.traUserUltimo = traUserUltimo; }

    public String getUsuUser() { return usuUser; }
    public void setUsuUser(String usuUser) { this.usuUser = usuUser; }

    public LocalDateTime getTraFechaUltimo() { return traFechaUltimo; }
    public void setTraFechaUltimo(LocalDateTime traFechaUltimo) { this.traFechaUltimo = traFechaUltimo; }

    public String getShowTraFechaUltimo() { return showTraFechaUltimo; }
    public void setShowTraFechaUltimo(String showTraFechaUltimo) { this.showTraFechaUltimo = showTraFechaUltimo; }

    public Integer getTraSexo() { return traSexo; }
    public void setTraSexo(Integer traSexo) { this.traSexo = traSexo; }

    public String getTseNombre() { return tseNombre; }
    public void setTseNombre(String tseNombre) { this.tseNombre = tseNombre; }

    public Integer getNacCod() { return nacCod; }
    public void setNacCod(Integer nacCod) { this.nacCod = nacCod; }

    public String getNacDescripcion() { return nacDescripcion; }
    public void setNacDescripcion(String nacDescripcion) { this.nacDescripcion = nacDescripcion; }

    public Integer getCiuCod() { return ciuCod; }
    public void setCiuCod(Integer ciuCod) { this.ciuCod = ciuCod; }

    public String getCiuNombre() { return ciuNombre; }
    public void setCiuNombre(String ciuNombre) { this.ciuNombre = ciuNombre; }

    public String getTraNumCasa() { return traNumCasa; }
    public void setTraNumCasa(String traNumCasa) { this.traNumCasa = traNumCasa; }

    public String getTraDepto() { return traDepto; }
    public void setTraDepto(String traDepto) { this.traDepto = traDepto; }

    public String getTraBlock() { return traBlock; }
    public void setTraBlock(String traBlock) { this.traBlock = traBlock; }

    public String getTraEmail() { return traEmail; }
    public void setTraEmail(String traEmail) { this.traEmail = traEmail; }

    public Integer getTraEstadoCivil() { return traEstadoCivil; }
    public void setTraEstadoCivil(Integer traEstadoCivil) { this.traEstadoCivil = traEstadoCivil; }

    public String getEciNombre() { return eciNombre; }
    public void setEciNombre(String eciNombre) { this.eciNombre = eciNombre; }

    public Integer getTraPensionado() { return traPensionado; }
    public void setTraPensionado(Integer traPensionado) { this.traPensionado = traPensionado; }

    public String getPensionado() { return pensionado; }
    public void setPensionado(String pensionado) { this.pensionado = pensionado; }

    public Integer getTraCronico() { return traCronico; }
    public void setTraCronico(Integer traCronico) { this.traCronico = traCronico; }

    public String getCronico() { return cronico; }
    public void setCronico(String cronico) { this.cronico = cronico; }

    public String getTraEnfermedad() { return traEnfermedad; }
    public void setTraEnfermedad(String traEnfermedad) { this.traEnfermedad = traEnfermedad; }

    public Integer getTraReqContrato() { return traReqContrato; }
    public void setTraReqContrato(Integer traReqContrato) { this.traReqContrato = traReqContrato; }

    public String getReqContrato() { return reqContrato; }
    public void setReqContrato(String reqContrato) { this.reqContrato = reqContrato; }
    
    @Override
    public String toString() {
        return "Trabajador{" +
                "traFicha=" + traFicha +
                ", traFichaTrabajador=" + traFichaTrabajador +
                ", traRut='" + traRut + '\'' +
                ", nombreCompleto='" + getNombreCompleto() + '\'' +
                ", traEmail='" + traEmail + '\'' +
                ", vigente=" + isVigente() +
                '}';
    }
}