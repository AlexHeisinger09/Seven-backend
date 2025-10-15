package com.muellespenco.seven_backend.repository;

import com.muellespenco.seven_backend.entity.Nombrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NombradaRepository extends JpaRepository<Nombrada, Long> {
    
    /**
     * Buscar nombradas por ficha de trabajador, fecha y turno
     */
    @Query(value = "SELECT * FROM eventuales.v_web_nombrada " +
                   "WHERE tra_ficha_trabajador = :fichaTrabajador " +
                   "AND fae_fecha_inicial_planif = :fecha " +
                   "AND fae_turno_cod = :turno " +
                   "ORDER BY TO_DATE(fae_fecha_inicial_planif, 'DD/MM/YYYY') DESC", 
           nativeQuery = true)
    List<Nombrada> findByFichaTrabajadorAndFechaAndTurno(
        @Param("fichaTrabajador") Long fichaTrabajador,
        @Param("fecha") String fecha,
        @Param("turno") Integer turno
    );
    
    /**
     * Buscar todas las nombradas por fecha y turno
     */
    @Query(value = "SELECT * FROM eventuales.v_web_nombrada " +
                   "WHERE fae_fecha_inicial_planif = :fecha " +
                   "AND fae_turno_cod = :turno " +
                   "ORDER BY TO_DATE(fae_fecha_inicial_planif, 'DD/MM/YYYY') DESC", 
           nativeQuery = true)
    List<Nombrada> findByFechaAndTurno(
        @Param("fecha") String fecha,
        @Param("turno") Integer turno
    );
}
