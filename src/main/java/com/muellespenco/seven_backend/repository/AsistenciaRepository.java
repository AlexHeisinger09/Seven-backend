package com.muellespenco.seven_backend.repository;

import com.muellespenco.seven_backend.entity.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    
    /**
     * Buscar asistencias por ficha de trabajador
     */
    @Query(value = "SELECT * FROM eventuales.v_asistencia_trabajador_faena_cerrada WHERE tra_ficha = :traFicha ORDER BY fae_fecha_inicial_planif DESC", nativeQuery = true)
    List<Asistencia> findByTraFichaOrderByFaeFechaInicialPlanifDesc(@Param("traFicha") Long traFicha);
    
    /**
     * Buscar asistencias por ficha de trabajador en un rango de fechas
     */
    @Query(value = "SELECT * FROM eventuales.v_asistencia_trabajador_faena_cerrada WHERE tra_ficha = :traFicha AND fae_fecha_inicial_planif BETWEEN :fechaInicio AND :fechaFin ORDER BY fae_fecha_inicial_planif DESC", nativeQuery = true)
    List<Asistencia> findByTraFichaAndFechaRange(@Param("traFicha") Long traFicha, 
                                                 @Param("fechaInicio") LocalDate fechaInicio, 
                                                 @Param("fechaFin") LocalDate fechaFin);
    
    /**
     * Buscar asistencias por ficha de trabajador en un mes específico
     */
    @Query(value = "SELECT * FROM eventuales.v_asistencia_trabajador_faena_cerrada WHERE tra_ficha = :traFicha AND EXTRACT(YEAR FROM fae_fecha_inicial_planif) = :anio AND EXTRACT(MONTH FROM fae_fecha_inicial_planif) = :mes ORDER BY fae_fecha_inicial_planif DESC", nativeQuery = true)
    List<Asistencia> findByTraFichaAndMonthYear(@Param("traFicha") Long traFicha, 
                                                @Param("mes") int mes, 
                                                @Param("anio") int anio);
    
    /**
     * Buscar asistencia específica por ficha, fecha y turno
     */
    @Query(value = "SELECT * FROM eventuales.v_asistencia_trabajador_faena_cerrada WHERE tra_ficha = :traFicha AND fae_fecha_inicial_planif = :fecha AND fae_turno_cod = :turno", nativeQuery = true)
    Optional<Asistencia> findByTraFichaAndFechaAndTurno(@Param("traFicha") Long traFicha, 
                                                        @Param("fecha") LocalDate fecha, 
                                                        @Param("turno") Integer turno);
    
    /**
     * Buscar asistencias por ficha de trabajador en una fecha específica
     */
    @Query(value = "SELECT * FROM eventuales.v_asistencia_trabajador_faena_cerrada WHERE tra_ficha = :traFicha AND fae_fecha_inicial_planif = :fecha ORDER BY fae_turno_cod", nativeQuery = true)
    List<Asistencia> findByTraFichaAndFaeFechaInicialPlanifOrderByFaeTurnoCod(@Param("traFicha") Long traFicha, @Param("fecha") LocalDate fecha);
    
    /**
     * Obtener últimas asistencias de un trabajador (limitadas)
     */
    @Query(value = "SELECT * FROM eventuales.v_asistencia_trabajador_faena_cerrada WHERE tra_ficha = :traFicha ORDER BY fae_fecha_inicial_planif DESC LIMIT :limite", nativeQuery = true)
    List<Asistencia> findTopByTraFichaOrderByFaeFechaInicialPlanifDesc(@Param("traFicha") Long traFicha, @Param("limite") int limite);
    
    /**
     * Contar asistencias de un trabajador en un período
     */
    @Query(value = "SELECT COUNT(*) FROM eventuales.v_asistencia_trabajador_faena_cerrada WHERE tra_ficha = :traFicha AND fae_fecha_inicial_planif BETWEEN :fechaInicio AND :fechaFin", nativeQuery = true)
    long countByTraFichaAndFechaRange(@Param("traFicha") Long traFicha, 
                                      @Param("fechaInicio") LocalDate fechaInicio, 
                                      @Param("fechaFin") LocalDate fechaFin);
    
    /**
     * Obtener días únicos con asistencia en un mes
     */
    @Query("SELECT DISTINCT DAY(a.faeFechaInicialPlanif) FROM Asistencia a WHERE a.traFicha = :traFicha AND YEAR(a.faeFechaInicialPlanif) = :anio AND MONTH(a.faeFechaInicialPlanif) = :mes")
    List<Integer> findDistinctDaysByTraFichaAndMonthYear(@Param("traFicha") Long traFicha, 
                                                         @Param("mes") int mes, 
                                                         @Param("anio") int anio);
    
    /**
     * Buscar asistencias con ausencia
     */
    @Query("SELECT a FROM Asistencia a WHERE a.traFicha = :traFicha AND a.dasiAusencia IS NOT NULL AND a.dasiAusencia != '' ORDER BY a.faeFechaInicialPlanif DESC")
    List<Asistencia> findAusenciasByTraFicha(@Param("traFicha") Long traFicha);
    
    /**
     * Obtener estadísticas de asistencia mensual
     */
    @Query("SELECT " +
           "COUNT(a) as totalDias, " +
           "SUM(CASE WHEN a.horaEntrada IS NOT NULL AND a.horaEntrada != '' THEN 1 ELSE 0 END) as diasAsistidos, " +
           "SUM(CASE WHEN a.dasiAusencia IS NOT NULL AND a.dasiAusencia != '' THEN 1 ELSE 0 END) as diasAusentes " +
           "FROM Asistencia a " +
           "WHERE a.traFicha = :traFicha AND YEAR(a.faeFechaInicialPlanif) = :anio AND MONTH(a.faeFechaInicialPlanif) = :mes")
    Object[] getEstadisticasMensual(@Param("traFicha") Long traFicha, @Param("mes") int mes, @Param("anio") int anio);
}