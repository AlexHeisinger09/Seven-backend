package com.muellespenco.seven_backend.repository;

import com.muellespenco.seven_backend.entity.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    
    /**
     * Buscar trabajador por ficha de trabajador
     */
    Optional<Trabajador> findByTraFichaTrabajador(Long traFichaTrabajador);
    
    /**
     * Buscar trabajador por RUT
     */
    Optional<Trabajador> findByTraRut(String traRut);
    
    /**
     * Buscar trabajador por email
     */
    Optional<Trabajador> findByTraEmail(String traEmail);
    
    /**
     * Buscar trabajadores vigentes
     */
    @Query("SELECT t FROM Trabajador t WHERE :fechaActual BETWEEN t.traVigDesde AND t.traVigHasta")
    List<Trabajador> findAllVigentes(@Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Buscar trabajador vigente por ficha
     */
    @Query("SELECT t FROM Trabajador t WHERE t.traFichaTrabajador = :traFichaTrabajador AND :fechaActual BETWEEN t.traVigDesde AND t.traVigHasta")
    Optional<Trabajador> findByTraFichaTrabajadorAndVigente(@Param("traFichaTrabajador") Long traFichaTrabajador, @Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Buscar trabajadores por nombre (like)
     */
    @Query("SELECT t FROM Trabajador t WHERE LOWER(CONCAT(t.traNombre, ' ', t.traApellidoP, ' ', t.traApellidoM)) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Trabajador> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    /**
     * Buscar trabajadores por área o división
     */
    @Query("SELECT t FROM Trabajador t WHERE LOWER(t.eduNombre) LIKE LOWER(CONCAT('%', :area, '%')) OR LOWER(t.sinNombre) LIKE LOWER(CONCAT('%', :area, '%'))")
    List<Trabajador> findByArea(@Param("area") String area);
    
    /**
     * Verificar si existe un trabajador con ese RUT
     */
    boolean existsByTraRut(String traRut);
    
    /**
     * Verificar si existe un trabajador con ese email
     */
    boolean existsByTraEmail(String traEmail);
    
    /**
     * Obtener trabajadores que cumplen años en un mes específico
     */
    @Query("SELECT t FROM Trabajador t WHERE MONTH(t.traFechaNacimiento) = :mes AND :fechaActual BETWEEN t.traVigDesde AND t.traVigHasta")
    List<Trabajador> findCumpleanerosByMes(@Param("mes") int mes, @Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Obtener trabajadores que cumplen años hoy
     */
    @Query("SELECT t FROM Trabajador t WHERE MONTH(t.traFechaNacimiento) = :mes AND DAY(t.traFechaNacimiento) = :dia AND :fechaActual BETWEEN t.traVigDesde AND t.traVigHasta")
    List<Trabajador> findCumpleanosHoy(@Param("mes") int mes, @Param("dia") int dia, @Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Obtener trabajadores nuevos (ingresaron en los últimos N días)
     */
    @Query("SELECT t FROM Trabajador t WHERE t.traFechaIngMdp >= :fechaDesde AND :fechaActual BETWEEN t.traVigDesde AND t.traVigHasta ORDER BY t.traFechaIngMdp DESC")
    List<Trabajador> findTrabajadoresNuevos(@Param("fechaDesde") LocalDate fechaDesde, @Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Contar trabajadores vigentes
     */
    @Query("SELECT COUNT(t) FROM Trabajador t WHERE :fechaActual BETWEEN t.traVigDesde AND t.traVigHasta")
    long countVigentes(@Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Obtener estadísticas básicas de trabajadores
     */
    @Query("SELECT " +
           "COUNT(t) as total, " +
           "SUM(CASE WHEN t.tseNombre = 'MASCULINO' THEN 1 ELSE 0 END) as hombres, " +
           "SUM(CASE WHEN t.tseNombre = 'FEMENINO' THEN 1 ELSE 0 END) as mujeres " +
           "FROM Trabajador t WHERE :fechaActual BETWEEN t.traVigDesde AND t.traVigHasta")
    Object[] getEstadisticasBasicas(@Param("fechaActual") LocalDate fechaActual);
}