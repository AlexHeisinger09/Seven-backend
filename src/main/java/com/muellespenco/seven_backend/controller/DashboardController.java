package com.muellespenco.seven_backend.controller;

import com.muellespenco.seven_backend.config.JwtUtil;
import com.muellespenco.seven_backend.dto.ApiResponseDto;
import com.muellespenco.seven_backend.dto.DashboardResponseDto;
import com.muellespenco.seven_backend.entity.Trabajador;
import com.muellespenco.seven_backend.entity.TurnosTrabajador;
import com.muellespenco.seven_backend.entity.Usuario;
import com.muellespenco.seven_backend.repository.TrabajadorRepository;
import com.muellespenco.seven_backend.repository.TurnosTrabajadorRepository;
import com.muellespenco.seven_backend.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;  // ← ESTE IMPORT ES IMPORTANTE
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
@Tag(name = "Dashboard", description = "Endpoints para información del dashboard personal")
public class DashboardController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private TurnosTrabajadorRepository turnosRepository;

    /**
     * Obtener información completa del dashboard personal
     */
    @GetMapping("/me")
    @Operation(summary = "Obtener mi dashboard", description = "Obtiene toda la información del dashboard del usuario autenticado")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<ApiResponseDto<DashboardResponseDto>> getMiDashboard(
            @RequestHeader("Authorization") String authHeader) {

        try {
            String token = jwtUtil.extractTokenFromHeader(authHeader);

            if (token == null || !jwtUtil.isValidToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "Token inválido o expirado"));
            }

            // Obtener usuario del token
            Integer usuCod = jwtUtil.extractUsuCod(token);
            if (usuCod == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponseDto.error("TOKEN_INVALIDO", "No se pudo obtener el usuario del token"));
            }

            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuCod);
            if (usuarioOpt.isEmpty() || usuarioOpt.get().getUsuFicha() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("FICHA_NO_ENCONTRADA",
                                "No se encontró ficha de trabajador asociada"));
            }

            Long usuFicha = usuarioOpt.get().getUsuFicha();
            Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByTraFichaTrabajador(usuFicha);
            if (trabajadorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponseDto.error("TRABAJADOR_NO_ENCONTRADO",
                                "No existe trabajador para la ficha " + usuFicha));
            }

            Trabajador trabajador = trabajadorOpt.get();
            Long traFicha = trabajador.getTraFicha();

            // Construir dashboard
            DashboardResponseDto dashboard = new DashboardResponseDto();

            // 1. Información de turnos del período actual
            dashboard.setInfoTurnos(obtenerInfoTurnos(traFicha));

            // 2. Información de vencimiento de pase portuario
            dashboard.setVencimientoPase(obtenerInfoVencimientoPase(trabajador));

            // 3. Próximos cumpleaños (próximos 30 días)
            dashboard.setProximosCumpleanos(obtenerProximosCumpleanos());

            ApiResponseDto<DashboardResponseDto> response = ApiResponseDto.success(
                    dashboard,
                    "Dashboard obtenido exitosamente");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Error obteniendo dashboard: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseDto.error("ERROR_INTERNO", "Error interno del servidor"));
        }
    }

    /**
     * Obtener información de turnos trabajados en el período actual
     */
    private DashboardResponseDto.InfoTurnosDto obtenerInfoTurnos(Long traFicha) {
        try {
            // Calcular período actual (YYYYMM)
            LocalDate hoy = LocalDate.now();
            Integer periodoActual = (hoy.getYear() * 100) + hoy.getMonthValue();

            Optional<TurnosTrabajador> turnosOpt = turnosRepository.findByTraFichaAndPeriodo(traFicha, periodoActual);

            if (turnosOpt.isPresent()) {
                TurnosTrabajador turnos = turnosOpt.get();
                String periodoStr = String.format("%02d/%d", turnos.getMes(), turnos.getAnio());

                return new DashboardResponseDto.InfoTurnosDto(
                        turnos.getTotalTurnos(),
                        periodoStr,
                        turnos.getMes(),
                        turnos.getAnio());
            } else {
                // Si no hay datos, intentar obtener el último período disponible
                Optional<TurnosTrabajador> ultimoPeriodoOpt = turnosRepository.findUltimoPeriodoByTraFicha(traFicha);

                if (ultimoPeriodoOpt.isPresent()) {
                    TurnosTrabajador turnos = ultimoPeriodoOpt.get();
                    String periodoStr = String.format("%02d/%d", turnos.getMes(), turnos.getAnio());

                    return new DashboardResponseDto.InfoTurnosDto(
                            turnos.getTotalTurnos(),
                            periodoStr,
                            turnos.getMes(),
                            turnos.getAnio());
                } else {
                    // Sin datos
                    String periodoStr = String.format("%02d/%d", hoy.getMonthValue(), hoy.getYear());
                    return new DashboardResponseDto.InfoTurnosDto(0, periodoStr, hoy.getMonthValue(), hoy.getYear());
                }
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo info de turnos: " + e.getMessage());
            LocalDate hoy = LocalDate.now();
            String periodoStr = String.format("%02d/%d", hoy.getMonthValue(), hoy.getYear());
            return new DashboardResponseDto.InfoTurnosDto(0, periodoStr, hoy.getMonthValue(), hoy.getYear());
        }
    }

    /**
     * Obtener información de vencimiento del pase portuario
     */
    private DashboardResponseDto.InfoVencimientoPaseDto obtenerInfoVencimientoPase(Trabajador trabajador) {
        try {
            LocalDate fechaVencimiento = trabajador.getTraVencimientoPase();
            String fechaVencimientoStr = trabajador.getShowTraVencimientoPase();

            if (fechaVencimiento == null) {
                return new DashboardResponseDto.InfoVencimientoPaseDto(
                        "Sin fecha", null, false, false);
            }

            LocalDate hoy = LocalDate.now();
            long diasParaVencer = ChronoUnit.DAYS.between(hoy, fechaVencimiento);
            boolean vencido = diasParaVencer < 0;
            boolean proximoAVencer = !vencido && diasParaVencer <= 30;

            return new DashboardResponseDto.InfoVencimientoPaseDto(
                    fechaVencimientoStr != null ? fechaVencimientoStr
                            : fechaVencimiento.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    (int) diasParaVencer,
                    vencido,
                    proximoAVencer);
        } catch (Exception e) {
            System.err.println("Error obteniendo info de vencimiento: " + e.getMessage());
            return new DashboardResponseDto.InfoVencimientoPaseDto(
                    "Error", null, false, false);
        }
    }

    /**
     * Obtener próximos cumpleaños (próximos 30 días)
     */
    /**
     * Obtener próximos cumpleaños (próximos 30 días)
     */
    private List<DashboardResponseDto.CumpleaneroDto> obtenerProximosCumpleanos() {
        try {
            LocalDate hoy = LocalDate.now();
            LocalDate dentroDeUnMes = hoy.plusDays(30);

            // Obtener todos los trabajadores vigentes
            List<Trabajador> trabajadores = trabajadorRepository.findAllVigentes(hoy);

            List<DashboardResponseDto.CumpleaneroDto> cumpleaneros = new ArrayList<>();

            for (Trabajador t : trabajadores) {
                if (t.getTraFechaNacimiento() != null) {
                    try {
                        // Calcular el próximo cumpleaños
                        LocalDate fechaNacimiento = t.getTraFechaNacimiento();

                        // Manejar caso especial: 29 de febrero en años no bisiestos
                        int dia = fechaNacimiento.getDayOfMonth();
                        int mes = fechaNacimiento.getMonthValue();

                        // Si es 29 de febrero y el año actual no es bisiesto, usar 28 de febrero
                        if (mes == 2 && dia == 29 && !hoy.isLeapYear()) {
                            dia = 28;
                        }

                        LocalDate proximoCumpleanos;
                        try {
                            proximoCumpleanos = LocalDate.of(hoy.getYear(), mes, dia);
                        } catch (Exception e) {
                            // Si aún hay error, saltar este trabajador
                            System.err.println("Error calculando cumpleaños para " + t.getNombreCompleto() + ": "
                                    + e.getMessage());
                            continue;
                        }

                        // Si ya pasó este año, calcular para el próximo año
                        if (proximoCumpleanos.isBefore(hoy)) {
                            // Para el próximo año, volver a verificar si es bisiesto
                            int proximoAnio = hoy.getYear() + 1;
                            int diaProximoAnio = fechaNacimiento.getDayOfMonth();

                            // Si es 29 de febrero y el próximo año no es bisiesto, usar 28 de febrero
                            if (mes == 2 && diaProximoAnio == 29 && !LocalDate.of(proximoAnio, 1, 1).isLeapYear()) {
                                diaProximoAnio = 28;
                            }

                            try {
                                proximoCumpleanos = LocalDate.of(proximoAnio, mes, diaProximoAnio);
                            } catch (Exception e) {
                                System.err.println("Error calculando próximo cumpleaños para " + t.getNombreCompleto()
                                        + ": " + e.getMessage());
                                continue;
                            }
                        }

                        // Verificar si está dentro de los próximos 30 días
                        if (!proximoCumpleanos.isAfter(dentroDeUnMes)) {
                            long diasParaCumpleanos = ChronoUnit.DAYS.between(hoy, proximoCumpleanos);

                            cumpleaneros.add(new DashboardResponseDto.CumpleaneroDto(
                                    t.getNombreCompleto(),
                                    t.getShowTraFechaNacimiento(),
                                    (int) diasParaCumpleanos,
                                    t.getTraFoto(),
                                    t.getTseNombre()));
                        }
                    } catch (Exception e) {
                        // Si hay cualquier error con este trabajador, continuar con el siguiente
                        System.err.println(
                                "Error procesando cumpleaños de " + t.getNombreCompleto() + ": " + e.getMessage());
                        continue;
                    }
                }
            }

            // Ordenar por días para cumpleaños (más cercano primero) y limitar a 5
            return cumpleaneros.stream()
                    .sorted(Comparator.comparingInt(DashboardResponseDto.CumpleaneroDto::getDiasParaCumpleanos))
                    .limit(5)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error obteniendo próximos cumpleaños: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Health check
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica el estado del servicio de dashboard")
    public ResponseEntity<ApiResponseDto<String>> health() {
        ApiResponseDto<String> response = ApiResponseDto.success(
                "Dashboard service funcionando correctamente",
                "Health check exitoso");
        return ResponseEntity.ok(response);
    }
}