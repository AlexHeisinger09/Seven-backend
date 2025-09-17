package com.muellespenco.seven_backend.service;

import com.muellespenco.seven_backend.dto.AsistenciaResponseDto;
import com.muellespenco.seven_backend.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    /**
     * Obtener asistencias de un trabajador por mes y año
     */
    @Transactional(readOnly = true)
    public List<AsistenciaResponseDto> findByTraFichaAndMonthYear(Long traFicha, int mes, int anio) {
        try {
            if (traFicha == null || mes < 1 || mes > 12 || anio < 2000) {
                return List.of();
            }
            
            return asistenciaRepository.findByTraFichaAndMonthYear(traFicha, mes, anio)
                    .stream()
                    .map(AsistenciaResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo asistencias por mes y año: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtener asistencias de un trabajador en una fecha específica
     */
    @Transactional(readOnly = true)
    public List<AsistenciaResponseDto> findByTraFichaAndFecha(Long traFicha, LocalDate fecha) {
        try {
            if (traFicha == null || fecha == null) {
                return List.of();
            }
            
            return asistenciaRepository.findByTraFichaAndFaeFechaInicialPlanifOrderByFaeTurnoCod(traFicha, fecha)
                    .stream()
                    .map(AsistenciaResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo asistencias por fecha: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtener asistencias de un trabajador en una fecha específica
     */
    @Transactional(readOnly = true)
    public List<AsistenciaResponseDto> findByTraFichaAndFecha(Long traFicha, LocalDate fecha) {
        try {
            if (traFicha == null || fecha == null) {
                return List.of();
            }
            
            return asistenciaRepository.findByTraFichaAndFaeFechaInicialPlanifOrderByFaeTurnoCod(traFicha, fecha)
                    .stream()
                    .map(AsistenciaResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo asistencias por fecha: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtener asistencias de un trabajador en un rango de fechas
     */
    @Transactional(readOnly = true)
    public List<AsistenciaResponseDto> findByTraFichaAndFechaRange(Long traFicha, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            if (traFicha == null || fechaInicio == null || fechaFin == null) {
                return List.of();
            }
            
            return asistenciaRepository.findByTraFichaAndFechaRange(traFicha, fechaInicio, fechaFin)
                    .stream()
                    .map(AsistenciaResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo asistencias por rango de fechas: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
}