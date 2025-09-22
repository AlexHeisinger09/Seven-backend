package com.muellespenco.seven_backend.service;

import com.muellespenco.seven_backend.dto.AsistenciaResponseDto;
import com.muellespenco.seven_backend.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    /**
     * Obtener todas las asistencias de un trabajador (ordenadas por fecha desc)
     */
    @Transactional(readOnly = true)
    public List<AsistenciaResponseDto> findByTraFicha(Long traFicha) {
        try {
            if (traFicha == null) {
                return List.of();
            }
            
            return asistenciaRepository.findByTraFichaOrderByFaeFechaInicialPlanifDesc(traFicha)
                    .stream()
                    .map(AsistenciaResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo todas las asistencias del trabajador: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtener días únicos con asistencia en un mes específico
     */
    @Transactional(readOnly = true)
    public List<Integer> getDiasConAsistencia(Long traFicha, int mes, int anio) {
        try {
            if (traFicha == null || mes < 1 || mes > 12 || anio < 2000) {
                return List.of();
            }
            
            return asistenciaRepository.findDistinctDaysByTraFichaAndMonthYear(traFicha, mes, anio);
        } catch (Exception e) {
            System.err.println("Error obteniendo días con asistencia: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtener últimas asistencias de un trabajador
     */
    @Transactional(readOnly = true)
    public List<AsistenciaResponseDto> getUltimasAsistencias(Long traFicha, int limite) {
        try {
            if (traFicha == null || limite < 1) {
                return List.of();
            }
            
            return asistenciaRepository.findTopByTraFichaOrderByFaeFechaInicialPlanifDesc(traFicha, limite)
                    .stream()
                    .map(AsistenciaResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo últimas asistencias: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtener asistencia específica por ficha, fecha y turno
     */
    @Transactional(readOnly = true)
    public Optional<AsistenciaResponseDto> findByTraFichaAndFechaAndTurno(Long traFicha, LocalDate fecha, Integer turno) {
        try {
            if (traFicha == null || fecha == null || turno == null) {
                return Optional.empty();
            }
            
            return asistenciaRepository.findByTraFichaAndFechaAndTurno(traFicha, fecha, turno)
                    .map(AsistenciaResponseDto::from);
        } catch (Exception e) {
            System.err.println("Error obteniendo asistencia específica: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Contar asistencias en un período
     */
    @Transactional(readOnly = true)
    public long countAsistenciasByPeriodo(Long traFicha, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            if (traFicha == null || fechaInicio == null || fechaFin == null) {
                return 0L;
            }
            
            return asistenciaRepository.countByTraFichaAndFechaRange(traFicha, fechaInicio, fechaFin);
        } catch (Exception e) {
            System.err.println("Error contando asistencias por período: " + e.getMessage());
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * Obtener asistencias con ausencia de un trabajador
     */
    @Transactional(readOnly = true)
    public List<AsistenciaResponseDto> findAusenciasByTraFicha(Long traFicha) {
        try {
            if (traFicha == null) {
                return List.of();
            }
            
            return asistenciaRepository.findAusenciasByTraFicha(traFicha)
                    .stream()
                    .map(AsistenciaResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo ausencias: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Obtener estadísticas de asistencia mensual
     */
    @Transactional(readOnly = true)
    public EstadisticasAsistenciaDto getEstadisticasMensual(Long traFicha, int mes, int anio) {
        try {
            if (traFicha == null || mes < 1 || mes > 12 || anio < 2000) {
                return new EstadisticasAsistenciaDto(0L, 0L, 0L, 0.0, mes, anio);
            }
            
            Object[] result = asistenciaRepository.getEstadisticasMensual(traFicha, mes, anio);
            
            if (result != null && result.length >= 3) {
                Long totalDias = result[0] != null ? ((Number) result[0]).longValue() : 0L;
                Long diasAsistidos = result[1] != null ? ((Number) result[1]).longValue() : 0L;
                Long diasAusentes = result[2] != null ? ((Number) result[2]).longValue() : 0L;
                
                double porcentajeAsistencia = totalDias > 0 ? (diasAsistidos * 100.0) / totalDias : 0.0;
                
                return new EstadisticasAsistenciaDto(totalDias, diasAsistidos, diasAusentes, porcentajeAsistencia, mes, anio);
            }
            
            return new EstadisticasAsistenciaDto(0L, 0L, 0L, 0.0, mes, anio);
        } catch (Exception e) {
            System.err.println("Error obteniendo estadísticas mensuales: " + e.getMessage());
            e.printStackTrace();
            return new EstadisticasAsistenciaDto(0L, 0L, 0L, 0.0, mes, anio);
        }
    }

    /**
     * Obtener estadísticas de asistencia por rango de fechas
     */
    @Transactional(readOnly = true)
    public EstadisticasAsistenciaDto getEstadisticasByPeriodo(Long traFicha, LocalDate fechaInicio, LocalDate fechaFin) {
        try {
            if (traFicha == null || fechaInicio == null || fechaFin == null) {
                return new EstadisticasAsistenciaDto(0L, 0L, 0L, 0.0, 0, 0);
            }
            
            List<AsistenciaResponseDto> asistencias = findByTraFichaAndFechaRange(traFicha, fechaInicio, fechaFin);
            
            long totalDias = asistencias.size();
            long diasAsistidos = asistencias.stream()
                    .mapToLong(a -> a.isTieneAsistencia() ? 1 : 0)
                    .sum();
            long diasAusentes = asistencias.stream()
                    .mapToLong(a -> (a.getDasiAusencia() != null && !a.getDasiAusencia().isEmpty()) ? 1 : 0)
                    .sum();
            
            double porcentajeAsistencia = totalDias > 0 ? (diasAsistidos * 100.0) / totalDias : 0.0;
            
            return new EstadisticasAsistenciaDto(
                    totalDias, 
                    diasAsistidos, 
                    diasAusentes, 
                    porcentajeAsistencia, 
                    fechaInicio.getMonthValue(), 
                    fechaInicio.getYear()
            );
        } catch (Exception e) {
            System.err.println("Error obteniendo estadísticas por período: " + e.getMessage());
            e.printStackTrace();
            return new EstadisticasAsistenciaDto(0L, 0L, 0L, 0.0, 0, 0);
        }
    }

    /**
     * DTO interno para estadísticas de asistencia
     */
    public static class EstadisticasAsistenciaDto {
        private final Long totalDias;
        private final Long diasAsistidos;
        private final Long diasAusentes;
        private final double porcentajeAsistencia;
        private final int mes;
        private final int anio;

        public EstadisticasAsistenciaDto(Long totalDias, Long diasAsistidos, Long diasAusentes, 
                                       double porcentajeAsistencia, int mes, int anio) {
            this.totalDias = totalDias;
            this.diasAsistidos = diasAsistidos;
            this.diasAusentes = diasAusentes;
            this.porcentajeAsistencia = porcentajeAsistencia;
            this.mes = mes;
            this.anio = anio;
        }

        // Getters
        public Long getTotalDias() { return totalDias; }
        public Long getDiasAsistidos() { return diasAsistidos; }
        public Long getDiasAusentes() { return diasAusentes; }
        public double getPorcentajeAsistencia() { return porcentajeAsistencia; }
        public int getMes() { return mes; }
        public int getAnio() { return anio; }

        // Métodos adicionales de utilidad
        public double getPorcentajeAusencia() {
            return totalDias > 0 ? (diasAusentes * 100.0) / totalDias : 0.0;
        }

        public Long getDiasSinRegistro() {
            return totalDias - diasAsistidos - diasAusentes;
        }

        public boolean tieneAsistenciaPerfecta() {
            return totalDias > 0 && diasAsistidos.equals(totalDias);
        }

        @Override
        public String toString() {
            return "EstadisticasAsistenciaDto{" +
                    "mes=" + mes +
                    ", anio=" + anio +
                    ", totalDias=" + totalDias +
                    ", diasAsistidos=" + diasAsistidos +
                    ", diasAusentes=" + diasAusentes +
                    ", porcentajeAsistencia=" + String.format("%.2f", porcentajeAsistencia) + "%" +
                    '}';
        }
    }
}