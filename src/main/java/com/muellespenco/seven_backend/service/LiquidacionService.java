package com.muellespenco.seven_backend.service;
import com.muellespenco.seven_backend.entity.Liquidacion; 
import com.muellespenco.seven_backend.dto.LiquidacionResponseDto;
import com.muellespenco.seven_backend.dto.LiquidacionesListaResponseDto;
import com.muellespenco.seven_backend.entity.Usuario;
import com.muellespenco.seven_backend.repository.LiquidacionRepository;
import com.muellespenco.seven_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LiquidacionService {

    @Autowired
    private LiquidacionRepository liquidacionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene las liquidaciones del trabajador actual
     */
    @Transactional(readOnly = true)
    public LiquidacionesListaResponseDto getMisLiquidaciones(Integer usuCod, int page, int limit, Integer periodo, String fechaDesde, String fechaHasta) {
        try {
            // Obtener la ficha del trabajador desde el usuario
            Usuario usuario = usuarioRepository.findById(usuCod)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            Long fichaTrabajador = usuario.getUsuFicha();
            if (fichaTrabajador == null) {
                throw new RuntimeException("Usuario no tiene ficha de trabajador asociada");
            }
            
            return getLiquidacionesByWorker(fichaTrabajador, page, limit, periodo, fechaDesde, fechaHasta);
            
        } catch (Exception e) {
            System.err.println("Error obteniendo mis liquidaciones: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo liquidaciones: " + e.getMessage());
        }
    }

    /**
     * Obtiene las liquidaciones de un trabajador con paginación
     */
    @Transactional(readOnly = true)
    public LiquidacionesListaResponseDto getLiquidacionesByWorker(Long traFichaTrabajador, int page, int limit, Integer periodo, String fechaDesde, String fechaHasta) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Liquidacion> liquidacionesPage;
        
        System.out.println("=== SERVICE DEBUG ===");
        System.out.println("traFichaTrabajador: " + traFichaTrabajador);
        System.out.println("page: " + page + ", limit: " + limit + ", periodo: " + periodo);
        System.out.println("fechaDesde: " + fechaDesde + ", fechaHasta: " + fechaHasta);
        
        if (fechaDesde != null && fechaHasta != null) {
            // Usar filtro por rango de fechas
            System.out.println("Usando filtro por rango de fechas");
            
            // Validar formato de fechas
            try {
                LocalDate.parse(fechaDesde);
                LocalDate.parse(fechaHasta);
                System.out.println("Fechas válidas: " + fechaDesde + " a " + fechaHasta);
            } catch (Exception e) {
                System.err.println("ERROR: Formato de fecha inválido - " + e.getMessage());
                throw new RuntimeException("Formato de fecha inválido");
            }
            
            liquidacionesPage = liquidacionRepository.findByTraFichaTrabajadorAndFechaRange(traFichaTrabajador, fechaDesde, fechaHasta, pageable);
        } else if (periodo != null) {
            // Usar filtro por período
            System.out.println("Usando filtro por período");
            liquidacionesPage = liquidacionRepository.findByTraFichaTrabajadorAndPeriodo(traFichaTrabajador, periodo, pageable);
        } else {
            // Sin filtros específicos
            System.out.println("Sin filtros específicos - mostrando todas");
            liquidacionesPage = liquidacionRepository.findByTraFichaTrabajador(traFichaTrabajador, pageable);
        }

        System.out.println("Found " + liquidacionesPage.getTotalElements() + " total liquidaciones");
        System.out.println("Current page has " + liquidacionesPage.getContent().size() + " liquidaciones");

        List<LiquidacionResponseDto> liquidaciones = liquidacionesPage.getContent()
                .stream()
                .map(LiquidacionResponseDto::from)
                .collect(Collectors.toList());

        long total = liquidacionesPage.getTotalElements();
        boolean hasMore = liquidacionesPage.hasNext();

        System.out.println("=== END SERVICE DEBUG ===");

        return new LiquidacionesListaResponseDto(liquidaciones, total, hasMore);
    }

    /**
     * Obtiene una liquidación específica por código de nómina
     */
    @Transactional(readOnly = true)
    public LiquidacionResponseDto getLiquidacionDetail(Long cnomCod) {
        Liquidacion liquidacion = liquidacionRepository.findByCnomCod(cnomCod);
        if (liquidacion == null) {
            throw new RuntimeException("Liquidación no encontrada");
        }
        return LiquidacionResponseDto.from(liquidacion);
    }

    /**
     * Obtiene los períodos disponibles para el trabajador actual
     */
    @Transactional(readOnly = true)
    public List<Integer> getMisPeriodosDisponibles(Integer usuCod) {
        try {
            Usuario usuario = usuarioRepository.findById(usuCod)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            Long fichaTrabajador = usuario.getUsuFicha();
            if (fichaTrabajador == null) {
                throw new RuntimeException("Usuario no tiene ficha de trabajador asociada");
            }
            
            return liquidacionRepository.findAvailablePeriodsForWorker(fichaTrabajador);
            
        } catch (Exception e) {
            System.err.println("Error obteniendo períodos disponibles: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Obtiene los períodos disponibles para un trabajador
     */
    @Transactional(readOnly = true)
    public List<Integer> getAvailablePeriodsForWorker(Long traFichaTrabajador) {
        return liquidacionRepository.findAvailablePeriodsForWorker(traFichaTrabajador);
    }
}