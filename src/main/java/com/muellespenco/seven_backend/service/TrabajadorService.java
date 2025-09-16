package com.muellespenco.seven_backend.service;

import com.muellespenco.seven_backend.dto.TrabajadorResponseDto;
import com.muellespenco.seven_backend.repository.TrabajadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrabajadorService {

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    /**
     * Obtener trabajador por ficha de trabajador
     */
    @Transactional(readOnly = true)
    public Optional<TrabajadorResponseDto> findByFichaTrabajador(Long fichaTrabajador) {
        try {
            return trabajadorRepository.findByTraFichaTrabajador(fichaTrabajador)
                    .map(TrabajadorResponseDto::from);
        } catch (Exception e) {
            System.err.println("Error obteniendo trabajador por ficha: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtener trabajador vigente por ficha de trabajador
     */
    @Transactional(readOnly = true)
    public Optional<TrabajadorResponseDto> findByFichaTrabajadorVigente(Long fichaTrabajador) {
        try {
            return trabajadorRepository.findByTraFichaTrabajadorAndVigente(fichaTrabajador, LocalDate.now())
                    .map(TrabajadorResponseDto::from);
        } catch (Exception e) {
            System.err.println("Error obteniendo trabajador vigente por ficha: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtener trabajador por RUT
     */
    @Transactional(readOnly = true)
    public Optional<TrabajadorResponseDto> findByRut(String rut) {
        try {
            return trabajadorRepository.findByTraRut(rut)
                    .map(TrabajadorResponseDto::from);
        } catch (Exception e) {
            System.err.println("Error obteniendo trabajador por RUT: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtener trabajador por email
     */
    @Transactional(readOnly = true)
    public Optional<TrabajadorResponseDto> findByEmail(String email) {
        try {
            return trabajadorRepository.findByTraEmail(email)
                    .map(TrabajadorResponseDto::from);
        } catch (Exception e) {
            System.err.println("Error obteniendo trabajador por email: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtener todos los trabajadores vigentes
     */
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDto> findAllVigentes() {
        try {
            return trabajadorRepository.findAllVigentes(LocalDate.now())
                    .stream()
                    .map(TrabajadorResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo trabajadores vigentes: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Buscar trabajadores por nombre
     */
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDto> searchByNombre(String nombre) {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                return List.of();
            }
            return trabajadorRepository.findByNombreContainingIgnoreCase(nombre.trim())
                    .stream()
                    .map(TrabajadorResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error buscando trabajadores por nombre: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Obtener trabajadores que cumplen años en un mes específico
     */
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDto> findCumpleanerosByMes(int mes) {
        try {
            if (mes < 1 || mes > 12) {
                throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
            }
            return trabajadorRepository.findCumpleanerosByMes(mes, LocalDate.now())
                    .stream()
                    .map(TrabajadorResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo cumpleañeros del mes: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Obtener trabajadores que cumplen años hoy
     */
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDto> findCumpleanosHoy() {
        try {
            LocalDate hoy = LocalDate.now();
            return trabajadorRepository.findCumpleanosHoy(hoy.getMonthValue(), hoy.getDayOfMonth(), hoy)
                    .stream()
                    .map(TrabajadorResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo cumpleaños de hoy: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Obtener trabajadores nuevos (últimos N días)
     */
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDto> findTrabajadoresNuevos(int dias) {
        try {
            LocalDate fechaDesde = LocalDate.now().minusDays(dias);
            return trabajadorRepository.findTrabajadoresNuevos(fechaDesde, LocalDate.now())
                    .stream()
                    .map(TrabajadorResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error obteniendo trabajadores nuevos: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Contar trabajadores vigentes
     */
    @Transactional(readOnly = true)
    public long countVigentes() {
        try {
            return trabajadorRepository.countVigentes(LocalDate.now());
        } catch (Exception e) {
            System.err.println("Error contando trabajadores vigentes: " + e.getMessage());
            return 0L;
        }
    }

    /**
     * Verificar si existe un trabajador con ese RUT
     */
    @Transactional(readOnly = true)
    public boolean existsByRut(String rut) {
        try {
            return trabajadorRepository.existsByTraRut(rut);
        } catch (Exception e) {
            System.err.println("Error verificando existencia por RUT: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verificar si existe un trabajador con ese email
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        try {
            return trabajadorRepository.existsByTraEmail(email);
        } catch (Exception e) {
            System.err.println("Error verificando existencia por email: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtener estadísticas básicas de trabajadores
     */
    @Transactional(readOnly = true)
    public EstadisticasDto getEstadisticasBasicas() {
        try {
            Object[] result = trabajadorRepository.getEstadisticasBasicas(LocalDate.now());
            if (result != null && result.length >= 3) {
                Long total = result[0] != null ? ((Number) result[0]).longValue() : 0L;
                Long hombres = result[1] != null ? ((Number) result[1]).longValue() : 0L;
                Long mujeres = result[2] != null ? ((Number) result[2]).longValue() : 0L;
                
                return new EstadisticasDto(total, hombres, mujeres);
            }
            return new EstadisticasDto(0L, 0L, 0L);
        } catch (Exception e) {
            System.err.println("Error obteniendo estadísticas básicas: " + e.getMessage());
            return new EstadisticasDto(0L, 0L, 0L);
        }
    }

    /**
     * Buscar trabajadores por área
     */
    @Transactional(readOnly = true)
    public List<TrabajadorResponseDto> findByArea(String area) {
        try {
            if (area == null || area.trim().isEmpty()) {
                return List.of();
            }
            return trabajadorRepository.findByArea(area.trim())
                    .stream()
                    .map(TrabajadorResponseDto::from)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error buscando trabajadores por área: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * DTO interno para estadísticas básicas
     */
    public static class EstadisticasDto {
        private final Long totalTrabajadores;
        private final Long hombres;
        private final Long mujeres;

        public EstadisticasDto(Long totalTrabajadores, Long hombres, Long mujeres) {
            this.totalTrabajadores = totalTrabajadores;
            this.hombres = hombres;
            this.mujeres = mujeres;
        }

        public Long getTotalTrabajadores() {
            return totalTrabajadores;
        }

        public Long getHombres() {
            return hombres;
        }

        public Long getMujeres() {
            return mujeres;
        }

        public double getPorcentajeHombres() {
            if (totalTrabajadores == 0) return 0.0;
            return (hombres * 100.0) / totalTrabajadores;
        }

        public double getPorcentajeMujeres() {
            if (totalTrabajadores == 0) return 0.0;
            return (mujeres * 100.0) / totalTrabajadores;
        }

        @Override
        public String toString() {
            return "EstadisticasDto{" +
                    "totalTrabajadores=" + totalTrabajadores +
                    ", hombres=" + hombres +
                    ", mujeres=" + mujeres +
                    ", porcentajeHombres=" + getPorcentajeHombres() +
                    ", porcentajeMujeres=" + getPorcentajeMujeres() +
                    '}';
        }
    }
}