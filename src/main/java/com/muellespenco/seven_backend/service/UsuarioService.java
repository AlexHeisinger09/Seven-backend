package com.muellespenco.seven_backend.service;

import com.muellespenco.seven_backend.config.CustomUserDetails;
import com.muellespenco.seven_backend.config.JwtUtil;
import com.muellespenco.seven_backend.dto.ChangePasswordRequestDto;
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
     * 🔥 ACTUALIZADO: Autenticar usuario y generar token JWT (ahora soporta RUT)
     */
    public LoginResponseDto authenticate(LoginRequestDto loginRequest) {
        try {
            System.out.println("🔍 DEBUG - Iniciando autenticación para: " + loginRequest.getCredential());
            
            // 🔥 Detectar tipo de credencial
            String credential = loginRequest.getCredential().trim();
            String credentialType = detectCredentialType(credential);
            
            System.out.println("🔍 DEBUG - Tipo de credencial detectado: " + credentialType);

            // Buscar usuario por credential (username, email o RUT)
            Usuario usuario = usuarioRepository.findByCredentialAndVigente(
                    credential,
                    LocalDate.now()).orElseThrow(() -> {
                        System.out.println("❌ DEBUG - Usuario no encontrado para: " + credential);
                        return new BadCredentialsException("Credenciales inválidas");
                    });

            System.out.println("✅ DEBUG - Usuario encontrado: " + usuario.getUsuUser() + " (RUT: " + usuario.getUsuRut() + ")");

            // Verificar contraseña con MD5
            boolean passwordValid = validateMD5Password(loginRequest.getPassword(), usuario.getUsuPass());
            System.out.println("🔑 DEBUG - Validación de contraseña: " + passwordValid);

            if (!passwordValid) {
                System.out.println("❌ DEBUG - Contraseña inválida");
                throw new BadCredentialsException("Credenciales inválidas");
            }

            System.out.println("✅ DEBUG - Contraseña válida, actualizando última conexión");

            // Actualizar última conexión
            usuario.setUsuUltimaConexion(LocalDateTime.now());
            usuarioRepository.save(usuario);

            System.out.println("✅ DEBUG - Generando token JWT");

            // Generar token JWT
            String token = jwtUtil.generateToken(
                    usuario.getUsuUser(),
                    usuario.getUsuCod(),
                    usuario.getUsuEmail());

            System.out.println("✅ DEBUG - Token generado exitosamente");

            // Crear respuesta
            UsuarioResponseDto usuarioResponse = UsuarioResponseDto.from(usuario);

            return LoginResponseDto.success(
                    token,
                    jwtUtil.getExpirationTimeInSeconds(),
                    usuarioResponse);

        } catch (BadCredentialsException e) {
            System.out.println("❌ DEBUG - BadCredentialsException: " + e.getMessage());
            return LoginResponseDto.error("Credenciales inválidas");
        } catch (Exception e) {
            System.err.println("❌ DEBUG - Exception general: " + e.getMessage());
            e.printStackTrace();
            return LoginResponseDto.error("Error interno del servidor");
        }
    }

    /**
     * 🔥 NUEVO: Detectar tipo de credencial
     */
    private String detectCredentialType(String credential) {
        if (credential.contains("@")) {
            return "EMAIL";
        }
        
        // Verificar si tiene formato de RUT (números, puntos, guión y posible K)
        if (credential.matches("^[\\d.-]+[kK\\d]$")) {
            return "RUT";
        }
        
        return "USERNAME";
    }

    /**
     * Cambiar contraseña de usuario
     */
    @Transactional
    public String changePassword(Integer usuCod, ChangePasswordRequestDto changePasswordRequest) {
        try {
            System.out.println("🔍 DEBUG - Iniciando cambio de contraseña para usuario: " + usuCod);

            // Validar que las contraseñas nuevas coincidan
            if (!changePasswordRequest.isPasswordsMatching()) {
                return "<div class=\"alert alert-danger\">Las contraseñas nuevas no coinciden</div>";
            }

            // Llamar a la función stored procedure
            String result = usuarioRepository.changePassword(
                usuCod,
                changePasswordRequest.getCurrentPassword(),
                changePasswordRequest.getNewPassword(),
                changePasswordRequest.getConfirmPassword()
            );

            System.out.println("✅ DEBUG - Resultado del cambio de contraseña: " + result);
            return result;

        } catch (Exception e) {
            System.err.println("❌ DEBUG - Error en cambio de contraseña: " + e.getMessage());
            e.printStackTrace();
            return "<div class=\"alert alert-danger\">Error interno del servidor al cambiar la contraseña</div>";
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
     * 🔥 NUEVO: Obtener usuario por RUT
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioResponseDto> findByRut(String rut) {
        return usuarioRepository.findByUsuRut(rut)
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