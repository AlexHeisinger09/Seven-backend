package com.muellespenco.seven_backend.service;

import com.muellespenco.seven_backend.dto.NombradaResponseDto;
import com.muellespenco.seven_backend.entity.Usuario;
import com.muellespenco.seven_backend.repository.NombradaRepository;
import com.muellespenco.seven_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NombradaService {

    @Autowired
    private NombradaRepository nombradaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtener nombradas del trabajador actual por fecha y turno
     */
    @Transactional(readOnly = true)
    public List<NombradaResponseDto> getMisNombradas(Integer usuCod, String fecha, Integer turno) {
        try {
            // Obtener la ficha del trabajador desde el usuario
            Usuario usuario = usuarioRepository.findById(usuCod)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            Long fichaTrabajador = usuario.getUsuFicha();
            if (fichaTrabajador == null) {
                throw new RuntimeException("Usuario no tiene ficha de trabajador asociada");
            }
            
            // Convertir fecha de yyyy-MM-dd a dd/MM/yyyy
            String fechaFormateada = convertirFecha(fecha);
            
            // Buscar nombradas
            return nombradaRepository.findByFichaTrabajadorAndFechaAndTurno(
                fichaTrabajador, 
                fechaFormateada, 
                turno
            ).stream()
             .map(NombradaResponseDto::from)
             .collect(Collectors.toList());
             
        } catch (Exception e) {
            System.err.println("Error obteniendo nombradas: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Obtener todas las nombradas por fecha y turno (para administradores)
     */
    @Transactional(readOnly = true)
    public List<NombradaResponseDto> getAllNombradas(String fecha, Integer turno) {
        try {
            // Convertir fecha de yyyy-MM-dd a dd/MM/yyyy
            String fechaFormateada = convertirFecha(fecha);
            
            return nombradaRepository.findByFechaAndTurno(fechaFormateada, turno)
                .stream()
                .map(NombradaResponseDto::from)
                .collect(Collectors.toList());
                
        } catch (Exception e) {
            System.err.println("Error obteniendo nombradas: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Convertir fecha de formato yyyy-MM-dd a dd/MM/yyyy
     */
    private String convertirFecha(String fecha) {
        try {
            DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(fecha, formatoEntrada);
            return localDate.format(formatoSalida);
        } catch (Exception e) {
            System.err.println("Error convirtiendo fecha: " + e.getMessage());
            return fecha;
        }
    }
}
