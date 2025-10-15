package com.muellespenco.seven_backend.repository;

import com.muellespenco.seven_backend.entity.TurnosTrabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TurnosTrabajadorRepository extends JpaRepository<TurnosTrabajador, Long> {
    
    /**
     * Buscar turnos de un trabajador para un período específico
     */
    @Query(value = "SELECT * FROM eventuales.v_total_turnos_trabajador_periodo WHERE tra_ficha = :traFicha AND litu_periodo = :periodo", nativeQuery = true)
    Optional<TurnosTrabajador> findByTraFichaAndPeriodo(@Param("traFicha") Long traFicha, @Param("periodo") Integer periodo);
    
    /**
     * Buscar turnos del período actual del trabajador
     */
    @Query(value = "SELECT * FROM eventuales.v_total_turnos_trabajador_periodo WHERE tra_ficha = :traFicha ORDER BY litu_periodo DESC LIMIT 1", nativeQuery = true)
    Optional<TurnosTrabajador> findUltimoPeriodoByTraFicha(@Param("traFicha") Long traFicha);
}