package com.muellespenco.seven_backend.dto;

import com.muellespenco.seven_backend.entity.Trabajador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para Trabajador, alineado 1:1 con la entidad y
 * con campos derivados útiles para el frontend (nombreCompleto, edad, vigente, direccionCompleta).
 */
public class TrabajadorResponseDto implements Serializable {

    // Identificación básica
    private Long traFicha;
    private Long traFichaTrabajador;
    private String traRut;
    private String traNombre;
    private String traApellidoP;
    private String traApellidoM;
    private String nombreCompleto;

    // Fechas importantes
    private LocalDate traFechaIngMdp;
    private String showTraFechaIngMdp;
    private LocalDate traFechaNacimiento;
    private String showTraFechaNacimiento;
    private int edad;

    // Información de contacto
    private String traDireccion;
    private String traMovil;
    private String traEmail;
    private String direccionCompleta;

    // Sexo / nacionalidad / ciudad / estado civil
    private Integer traSexo;
    private String tseNombre;
    private Integer nacCod;
    private String nacDescripcion;
    private Integer ciuCod;
    private String ciuNombre;
    private Integer traEstadoCivil; // corregido nombre
    private String eciNombre;

    // Información educativa y sindical
    private Integer traEducacionCod;
    private String eduNombre;
    private Integer traSindicatoCod;
    private String sinNombre;

    // Información de licencia
    private Integer traLicenciaCod;
    private String licTipo;
    private String licDescripcion;

    // Cargas familiares
    private Integer traCargasSimple;
    private Integer traCargaPorInvalides;

    // Información laboral (banderas como Integer tal cual en la entidad)
    private Integer traContratoProvision;
    private Integer traAccesoVehicular;
    private Integer traCursoRendido;
    private Integer traPasePortuario;
    private Integer traReglamentoInterno;
    private LocalDate traVencimientoPase;
    private String showTraVencimientoPase; // en entidad es String

    // Información física
    private String traTallaRopa;
    private String traNumCalzado;

    // Seguridad social
    private String traLibretaInp;
    private Integer traSubsidioCesantia;
    private Integer traAfpCod;
    private String afpNombre;
    private Integer traSaludCod;
    private String salNombre;
    private BigDecimal traUfIsapre; // BigDecimal para no perder precisión
    private String ufIsapre;

    // APV
    private Integer traAfpApvCod;
    private String afpApv;
    private String traNcontratoApv;
    private Integer traApvTipoValor;
    private String tipoValorApv;
    private BigDecimal traAfpApvPorc; // BigDecimal
    private String afpApvPorc;
    private String traFormaPagoApv;

    // Cuenta 2 (C2)
    private Integer traAfpC2Cod;
    private String afpC2;
    private Integer traC2TipoValor;
    private String tipoValorC2;
    private BigDecimal traAfpC2Porc; // BigDecimal
    private String afpC2Porc;

    // Información bancaria
    private Integer traFormaPagoCod;
    private String fopaDescripcion;
    private Integer banCod;
    private String banNombre;
    private String traNcuenta;

    // Vigencia
    private LocalDate traVigDesde;
    private String showTraVigDesde;
    private LocalDate traVigHasta;
    private String showTraVigHasta;
    private boolean vigente;

    // Auditoría
    private Integer traUserUltimo;
    private String usuUser;
    private LocalDateTime traFechaUltimo;
    private String showTraFechaUltimo;

    // Salud adicional
    private Integer traPensionado;
    private String pensionado;
    private Integer traCronico;
    private String cronico;
    private String traEnfermedad;

    // Requerimientos
    private Integer traReqContrato;
    private String reqContrato;

    // Foto
    private String traFoto;

    public TrabajadorResponseDto() {}

    public TrabajadorResponseDto(Trabajador t) {
        // Identificación
        this.traFicha = t.getTraFicha();
        this.traFichaTrabajador = t.getTraFichaTrabajador();
        this.traRut = t.getTraRut();
        this.traNombre = t.getTraNombre();
        this.traApellidoP = t.getTraApellidoP();
        this.traApellidoM = t.getTraApellidoM();
        this.nombreCompleto = t.getNombreCompleto();

        // Fechas
        this.traFechaIngMdp = t.getTraFechaIngMdp();
        this.showTraFechaIngMdp = t.getShowTraFechaIngMdp();
        this.traFechaNacimiento = t.getTraFechaNacimiento();
        this.showTraFechaNacimiento = t.getShowTraFechaNacimiento();
        this.edad = t.getEdad();

        // Contacto
        this.traDireccion = t.getTraDireccion();
        this.traMovil = t.getTraMovil();
        this.traEmail = t.getTraEmail();
        this.direccionCompleta = t.getDireccionCompleta();

        // Demografía / estado civil
        this.traSexo = t.getTraSexo();
        this.tseNombre = t.getTseNombre();
        this.nacCod = t.getNacCod();
        this.nacDescripcion = t.getNacDescripcion();
        this.ciuCod = t.getCiuCod();
        this.ciuNombre = t.getCiuNombre();
        this.traEstadoCivil = t.getTraEstadoCivil();
        this.eciNombre = t.getEciNombre();

        // Educación / sindicato
        this.traEducacionCod = t.getTraEducacionCod();
        this.eduNombre = t.getEduNombre();
        this.traSindicatoCod = t.getTraSindicatoCod();
        this.sinNombre = t.getSinNombre();

        // Licencias
        this.traLicenciaCod = t.getTraLicenciaCod();
        this.licTipo = t.getLicTipo();
        this.licDescripcion = t.getLicDescripcion();

        // Cargas
        this.traCargasSimple = t.getTraCargasSimple();
        this.traCargaPorInvalides = t.getTraCargaPorInvalides();

        // Laboral / permisos
        this.traContratoProvision = t.getTraContratoProvision();
        this.traAccesoVehicular = t.getTraAccesoVehicular();
        this.traCursoRendido = t.getTraCursoRendido();
        this.traPasePortuario = t.getTraPasePortuario();
        this.traReglamentoInterno = t.getTraReglamentoInterno();
        this.traVencimientoPase = t.getTraVencimientoPase();
        this.showTraVencimientoPase = t.getShowTraVencimientoPase();

        // Física
        this.traTallaRopa = t.getTraTallaRopa();
        this.traNumCalzado = t.getTraNumCalzado();

        // Seguridad social
        this.traLibretaInp = t.getTraLibretaInp();
        this.traSubsidioCesantia = t.getTraSubsidioCesantia();
        this.traAfpCod = t.getTraAfpCod();
        this.afpNombre = t.getAfpNombre();
        this.traSaludCod = t.getTraSaludCod();
        this.salNombre = t.getSalNombre();
        this.traUfIsapre = t.getTraUfIsapre();
        this.ufIsapre = t.getUfIsapre();

        // APV
        this.traAfpApvCod = t.getTraAfpApvCod();
        this.afpApv = t.getAfpApv();
        this.traNcontratoApv = t.getTraNcontratoApv();
        this.traApvTipoValor = t.getTraApvTipoValor();
        this.tipoValorApv = t.getTipoValorApv();
        this.traAfpApvPorc = t.getTraAfpApvPorc();
        this.afpApvPorc = t.getAfpApvPorc();
        this.traFormaPagoApv = t.getTraFormaPagoApv();

        // Cuenta 2
        this.traAfpC2Cod = t.getTraAfpC2Cod();
        this.afpC2 = t.getAfpC2();
        this.traC2TipoValor = t.getTraC2TipoValor();
        this.tipoValorC2 = t.getTipoValorC2();
        this.traAfpC2Porc = t.getTraAfpC2Porc();
        this.afpC2Porc = t.getAfpC2Porc();

        // Bancaria
        this.traFormaPagoCod = t.getTraFormaPagoCod();
        this.fopaDescripcion = t.getFopaDescripcion();
        this.banCod = t.getBanCod();
        this.banNombre = t.getBanNombre();
        this.traNcuenta = t.getTraNcuenta();

        // Vigencia
        this.traVigDesde = t.getTraVigDesde();
        this.showTraVigDesde = t.getShowTraVigDesde();
        this.traVigHasta = t.getTraVigHasta();
        this.showTraVigHasta = t.getShowTraVigHasta();
        this.vigente = t.isVigente();

        // Auditoría
        this.traUserUltimo = t.getTraUserUltimo();
        this.usuUser = t.getUsuUser();
        this.traFechaUltimo = t.getTraFechaUltimo();
        this.showTraFechaUltimo = t.getShowTraFechaUltimo();

        // Salud
        this.traPensionado = t.getTraPensionado();
        this.pensionado = t.getPensionado();
        this.traCronico = t.getTraCronico();
        this.cronico = t.getCronico();
        this.traEnfermedad = t.getTraEnfermedad();

        // Requerimientos
        this.traReqContrato = t.getTraReqContrato();
        this.reqContrato = t.getReqContrato();

        // Foto
        this.traFoto = t.getTraFoto();
    }

    public static TrabajadorResponseDto from(Trabajador trabajador) {
        return new TrabajadorResponseDto(trabajador);
    }

    // ===== Getters y Setters =====

    public Long getTraFicha() { return traFicha; }
    public void setTraFicha(Long traFicha) { this.traFicha = traFicha; }

    public Long getTraFichaTrabajador() { return traFichaTrabajador; }
    public void setTraFichaTrabajador(Long traFichaTrabajador) { this.traFichaTrabajador = traFichaTrabajador; }

    public String getTraRut() { return traRut; }
    public void setTraRut(String traRut) { this.traRut = traRut; }

    public String getTraNombre() { return traNombre; }
    public void setTraNombre(String traNombre) { this.traNombre = traNombre; }

    public String getTraApellidoP() { return traApellidoP; }
    public void setTraApellidoP(String traApellidoP) { this.traApellidoP = traApellidoP; }

    public String getTraApellidoM() { return traApellidoM; }
    public void setTraApellidoM(String traApellidoM) { this.traApellidoM = traApellidoM; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public LocalDate getTraFechaIngMdp() { return traFechaIngMdp; }
    public void setTraFechaIngMdp(LocalDate traFechaIngMdp) { this.traFechaIngMdp = traFechaIngMdp; }

    public String getShowTraFechaIngMdp() { return showTraFechaIngMdp; }
    public void setShowTraFechaIngMdp(String showTraFechaIngMdp) { this.showTraFechaIngMdp = showTraFechaIngMdp; }

    public LocalDate getTraFechaNacimiento() { return traFechaNacimiento; }
    public void setTraFechaNacimiento(LocalDate traFechaNacimiento) { this.traFechaNacimiento = traFechaNacimiento; }

    public String getShowTraFechaNacimiento() { return showTraFechaNacimiento; }
    public void setShowTraFechaNacimiento(String showTraFechaNacimiento) { this.showTraFechaNacimiento = showTraFechaNacimiento; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getTraDireccion() { return traDireccion; }
    public void setTraDireccion(String traDireccion) { this.traDireccion = traDireccion; }

    public String getTraMovil() { return traMovil; }
    public void setTraMovil(String traMovil) { this.traMovil = traMovil; }

    public String getTraEmail() { return traEmail; }
    public void setTraEmail(String traEmail) { this.traEmail = traEmail; }

    public String getDireccionCompleta() { return direccionCompleta; }
    public void setDireccionCompleta(String direccionCompleta) { this.direccionCompleta = direccionCompleta; }

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

    public Integer getTraEstadoCivil() { return traEstadoCivil; }
    public void setTraEstadoCivil(Integer traEstadoCivil) { this.traEstadoCivil = traEstadoCivil; }

    public String getEciNombre() { return eciNombre; }
    public void setEciNombre(String eciNombre) { this.eciNombre = eciNombre; }

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

    public LocalDate getTraVencimientoPase() { return traVencimientoPase; }
    public void setTraVencimientoPase(LocalDate traVencimientoPase) { this.traVencimientoPase = traVencimientoPase; }

    public String getShowTraVencimientoPase() { return showTraVencimientoPase; }
    public void setShowTraVencimientoPase(String showTraVencimientoPase) { this.showTraVencimientoPase = showTraVencimientoPase; }

    public String getTraTallaRopa() { return traTallaRopa; }
    public void setTraTallaRopa(String traTallaRopa) { this.traTallaRopa = traTallaRopa; }

    public String getTraNumCalzado() { return traNumCalzado; }
    public void setTraNumCalzado(String traNumCalzado) { this.traNumCalzado = traNumCalzado; }

    public String getTraLibretaInp() { return traLibretaInp; }
    public void setTraLibretaInp(String traLibretaInp) { this.traLibretaInp = traLibretaInp; }

    public Integer getTraSubsidioCesantia() { return traSubsidioCesantia; }
    public void setTraSubsidioCesantia(Integer traSubsidioCesantia) { this.traSubsidioCesantia = traSubsidioCesantia; }

    public Integer getTraAfpCod() { return traAfpCod; }
    public void setTraAfpCod(Integer traAfpCod) { this.traAfpCod = traAfpCod; }

    public String getAfpNombre() { return afpNombre; }
    public void setAfpNombre(String afpNombre) { this.afpNombre = afpNombre; }

    public Integer getTraSaludCod() { return traSaludCod; }
    public void setTraSaludCod(Integer traSaludCod) { this.traSaludCod = traSaludCod; }

    public String getSalNombre() { return salNombre; }
    public void setSalNombre(String salNombre) { this.salNombre = salNombre; }

    public BigDecimal getTraUfIsapre() { return traUfIsapre; }
    public void setTraUfIsapre(BigDecimal traUfIsapre) { this.traUfIsapre = traUfIsapre; }

    public String getUfIsapre() { return ufIsapre; }
    public void setUfIsapre(String ufIsapre) { this.ufIsapre = ufIsapre; }

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

    public boolean isVigente() { return vigente; }
    public void setVigente(boolean vigente) { this.vigente = vigente; }

    public Integer getTraUserUltimo() { return traUserUltimo; }
    public void setTraUserUltimo(Integer traUserUltimo) { this.traUserUltimo = traUserUltimo; }

    public String getUsuUser() { return usuUser; }
    public void setUsuUser(String usuUser) { this.usuUser = usuUser; }

    public LocalDateTime getTraFechaUltimo() { return traFechaUltimo; }
    public void setTraFechaUltimo(LocalDateTime traFechaUltimo) { this.traFechaUltimo = traFechaUltimo; }

    public String getShowTraFechaUltimo() { return showTraFechaUltimo; }
    public void setShowTraFechaUltimo(String showTraFechaUltimo) { this.showTraFechaUltimo = showTraFechaUltimo; }

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

    public String getTraFoto() { return traFoto; }
    public void setTraFoto(String traFoto) { this.traFoto = traFoto; }

    @Override
    public String toString() {
        return "TrabajadorResponseDto{" +
                "traFicha=" + traFicha +
                ", traFichaTrabajador=" + traFichaTrabajador +
                ", traRut='" + traRut + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", traEmail='" + traEmail + '\'' +
                ", edad=" + edad +
                ", vigente=" + vigente +
                '}';
    }
}
