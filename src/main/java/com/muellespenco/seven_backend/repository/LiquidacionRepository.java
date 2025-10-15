package com.muellespenco.seven_backend.repository;

import com.muellespenco.seven_backend.entity.Liquidacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiquidacionRepository extends JpaRepository<Liquidacion, Long> {

    /**
     * Obtiene las liquidaciones de un trabajador con paginación
     */
    @Query(value = "SELECT * FROM eventuales.v_detalle_pago_x_periodo " +
                   "WHERE tra_ficha_trabajador = :traFichaTrabajador " +
                   "ORDER BY fae_fecha_inicial DESC",
           countQuery = "SELECT COUNT(*) FROM eventuales.v_detalle_pago_x_periodo WHERE tra_ficha_trabajador = :traFichaTrabajador",
           nativeQuery = true)
    Page<Liquidacion> findByTraFichaTrabajador(@Param("traFichaTrabajador") Long traFichaTrabajador, Pageable pageable);

    /**
     * Obtiene las liquidaciones de un trabajador filtradas por período
     */
    @Query(value = "SELECT * FROM eventuales.v_detalle_pago_x_periodo " +
                   "WHERE tra_ficha_trabajador = :traFichaTrabajador " +
                   "AND EXTRACT(YEAR FROM fae_fecha_inicial) * 100 + EXTRACT(MONTH FROM fae_fecha_inicial) = :periodo " +
                   "ORDER BY fae_fecha_inicial DESC",
           countQuery = "SELECT COUNT(*) FROM eventuales.v_detalle_pago_x_periodo " +
                       "WHERE tra_ficha_trabajador = :traFichaTrabajador " +
                       "AND EXTRACT(YEAR FROM fae_fecha_inicial) * 100 + EXTRACT(MONTH FROM fae_fecha_inicial) = :periodo",
           nativeQuery = true)
    Page<Liquidacion> findByTraFichaTrabajadorAndPeriodo(@Param("traFichaTrabajador") Long traFichaTrabajador, 
                                                         @Param("periodo") Integer periodo, 
                                                         Pageable pageable);

    /**
     * Obtiene las liquidaciones de un trabajador en un rango de fechas
     */
    @Query(value = "SELECT * FROM eventuales.v_detalle_pago_x_periodo " +
                   "WHERE tra_ficha_trabajador = :traFichaTrabajador " +
                   "AND fae_fecha_inicial >= :fechaDesde::date " +
                   "AND fae_fecha_inicial <= :fechaHasta::date " +
                   "ORDER BY fae_fecha_inicial DESC",
           countQuery = "SELECT COUNT(*) FROM eventuales.v_detalle_pago_x_periodo " +
                       "WHERE tra_ficha_trabajador = :traFichaTrabajador " +
                       "AND fae_fecha_inicial >= :fechaDesde::date " +
                       "AND fae_fecha_inicial <= :fechaHasta::date",
           nativeQuery = true)
    Page<Liquidacion> findByTraFichaTrabajadorAndFechaRange(@Param("traFichaTrabajador") Long traFichaTrabajador,
                                                            @Param("fechaDesde") String fechaDesde,
                                                            @Param("fechaHasta") String fechaHasta,
                                                            Pageable pageable);

    /**
     * Obtiene una liquidación específica por código de nómina
     */
    @Query(value = "SELECT * FROM eventuales.v_detalle_pago_x_periodo WHERE cnom_cod = :cnomCod",
           nativeQuery = true)
    Liquidacion findByCnomCod(@Param("cnomCod") Long cnomCod);

    /**
     * Obtiene los períodos disponibles para un trabajador
     */
    @Query(value = "SELECT DISTINCT EXTRACT(YEAR FROM fae_fecha_inicial) * 100 + EXTRACT(MONTH FROM fae_fecha_inicial) as periodo " +
                   "FROM eventuales.v_detalle_pago_x_periodo " +
                   "WHERE tra_ficha_trabajador = :traFichaTrabajador " +
                   "ORDER BY periodo DESC",
           nativeQuery = true)
    List<Integer> findAvailablePeriodsForWorker(@Param("traFichaTrabajador") Long traFichaTrabajador);
}
