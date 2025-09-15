package com.muellespenco.seven_backend.service;

import com.muellespenco.seven_backend.config.CustomUserDetails;
import com.muellespenco.seven_backend.config.JwtUtil;
import com.muellespenco.seven_backend.dto.LoginRequestDto;
import com.muellespenco.seven_backend.dto.LoginResponseDto;
import com.muellespenco.seven_backend.dto.UsuarioResponseDto;
import com.muellespenco.seven_backend.entity.Usuario;
import com.muellespenco.seven_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    
    /**
     * Implementación de UserDetailsService para Spring Security
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCredentialAndVigente(username, LocalDate.now())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        
        return new CustomUserDetails(usuario);
    }
    
    /**
     * Autenticar usuario y generar token JWT
     */
    public LoginResponseDto authenticate(LoginRequestDto loginRequest) {
        try {
            // Buscar usuario por username o email
            Usuario usuario = usuarioRepository.findByCredentialAndVigente(
                    loginRequest.getCredential(), 
                    LocalDate.now()
            ).orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));
            
            // Verificar contraseña con MD5
            if (!validateMD5Password(loginRequest.getPassword(), usuario.getUsuPass())) {
                throw new BadCredentialsException("Credenciales inválidas");
            }
            
            // Actualizar última conexión
            usuario.setUsuUltimaConexion(LocalDateTime.now());
            usuarioRepository.save(usuario);
            
            // Generar token JWT
            String token = jwtUtil.generateToken(
                usuario.getUsuUser(), 
                usuario.getUsuCod(), 
                usuario.getUsuEmail()
            );
            
            // Crear respuesta
            UsuarioResponseDto usuarioResponse = UsuarioResponseDto.from(usuario);
            
            return LoginResponseDto.success(
                token, 
                jwtUtil.getExpirationTimeInSeconds(), 
                usuarioResponse
            );
            
        } catch (BadCredentialsException e) {
            return LoginResponseDto.error("Credenciales inválidas");
        } catch (Exception e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            e.printStackTrace();
            return LoginResponseDto.error("Error interno del servidor");
        }
    }
    
    /**
     * Validar contraseña MD5
     */
    private boolean validateMD5Password(String plainPassword, String hashedPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(plainPassword.getBytes());
            String md5Hash = bytesToHex(hash);
            return md5Hash.equals(hashedPassword);
        } catch (Exception e) {
            System.err.println("Error validando contraseña MD5: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Convertir bytes a hexadecimal
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
    
    /**
     * Obtener usuario por ID
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDto> findById(Integer usuCod) {
        return usuarioRepository.findById(usuCod)
                .map(UsuarioResponseDto::from);
    }
    
    /**
     * Obtener usuario por username
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDto> findByUsername(String username) {
        return usuarioRepository.findByUsuUser(username)
                .map(UsuarioResponseDto::from);
    }
    
    /**
     * Obtener usuario por email
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDto> findByEmail(String email) {
        return usuarioRepository.findByUsuEmail(email)
                .map(UsuarioResponseDto::from);
    }
    
    /**
     * Obtener todos los usuarios vigentes
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> findAllVigentes() {
        return usuarioRepository.findAllVigentes(LocalDate.now())
                .stream()
                .map(UsuarioResponseDto::from)
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar usuarios por nombre
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> searchByNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(UsuarioResponseDto::from)
                .collect(Collectors.toList());
    }
    
    /**
     * Verificar si existe un usuario con ese username
     */
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsuUser(username);
    }
    
    /**
     * Verificar si existe un usuario con ese email
     */
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByUsuEmail(email);
    }
    
    /**
     * Verificar si existe un usuario con ese RUT
     */
    @Transactional(readOnly = true)
    public boolean existsByRut(String rut) {
        return usuarioRepository.existsByUsuRut(rut);
    }
    
    /**
     * Obtener usuario desde token JWT
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDto> getUserFromToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            return findByUsername(username);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    /**
     * Validar token JWT
     */
    public boolean isValidToken(String token) {
        return jwtUtil.isValidToken(token);
    }
    
    /**
     * Actualizar última conexión
     */
    public void updateLastConnection(String username) {
        usuarioRepository.findByUsuUser(username)
                .ifPresent(usuario -> {
                    usuario.setUsuUltimaConexion(LocalDateTime.now());
                    usuarioRepository.save(usuario);
                });
    }
    
    /**
     * Obtener información del usuario autenticado actual
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDto> getCurrentUser(String username) {
        return usuarioRepository.findByUsuUserAndVigente(username, LocalDate.now())
                .map(UsuarioResponseDto::from);
    }
    
    /**
     * Contar usuarios vigentes
     */
    @Transactional(readOnly = true)
    public long countVigentes() {
        return usuarioRepository.findAllVigentes(LocalDate.now()).size();
    }
    
    /**
     * Obtener usuarios por tipo
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponseDto> findByTipo(Integer tipo) {
        return usuarioRepository.findByUsuTipo(tipo)
                .stream()
                .map(UsuarioResponseDto::from)
                .collect(Collectors.toList());
    }
}