package com.muellespenco.seven_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "v_total_turnos_trabajador_periodo", schema = "eventuales")
public class TurnosTrabajador {
    
    @Id
    @Column(name = "tra_ficha")
    private Long traFicha;
    
    @Column(name = "total_turnos")
    private Integer totalTurnos;
    
    @Column(name = "litu_periodo")
    private Integer lituPeriodo;
    
    // Constructor vacío
    public TurnosTrabajador() {}
    
    // Getters y Setters
    public Long getTraFicha() { return traFicha; }
    public void setTraFicha(Long traFicha) { this.traFicha = traFicha; }
    
    public Integer getTotalTurnos() { return totalTurnos; }
    public void setTotalTurnos(Integer totalTurnos) { this.totalTurnos = totalTurnos; }
    
    public Integer getLituPeriodo() { return lituPeriodo; }
    public void setLituPeriodo(Integer lituPeriodo) { this.lituPeriodo = lituPeriodo; }
    
    // Método de utilidad para obtener mes y año del período
    public int getMes() {
        if (lituPeriodo == null) return 0;
        return lituPeriodo % 100;
    }
    
    public int getAnio() {
        if (lituPeriodo == null) return 0;
        return lituPeriodo / 100;
    }
    
    @Override
    public String toString() {
        return "TurnosTrabajador{" +
                "traFicha=" + traFicha +
                ", totalTurnos=" + totalTurnos +
                ", lituPeriodo=" + lituPeriodo +
                '}';
    }
}