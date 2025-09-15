package com.muellespenco.seven_backend.repository;

import com.muellespenco.seven_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Buscar usuario por username (para login)
     */
    Optional<Usuario> findByUsuUser(String usuUser);
    
    /**
     * Buscar usuario por email (para login alternativo)
     */
    Optional<Usuario> findByUsuEmail(String usuEmail);
    
    /**
     * Buscar usuario por RUT
     */
    Optional<Usuario> findByUsuRut(String usuRut);
    
    /**
     * Buscar usuario por username Y que esté vigente
     */
    @Query("SELECT u FROM Usuario u WHERE u.usuUser = :usuUser AND :fechaActual BETWEEN u.usuVigDesde AND u.usuVigHasta")
    Optional<Usuario> findByUsuUserAndVigente(@Param("usuUser") String usuUser, @Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Buscar usuario por email Y que esté vigente
     */
    @Query("SELECT u FROM Usuario u WHERE u.usuEmail = :usuEmail AND :fechaActual BETWEEN u.usuVigDesde AND u.usuVigHasta")
    Optional<Usuario> findByUsuEmailAndVigente(@Param("usuEmail") String usuEmail, @Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Verificar si existe un usuario con ese username
     */
    boolean existsByUsuUser(String usuUser);
    
    /**
     * Verificar si existe un usuario con ese email
     */
    boolean existsByUsuEmail(String usuEmail);
    
    /**
     * Verificar si existe un usuario con ese RUT
     */
    boolean existsByUsuRut(String usuRut);
    
    /**
     * Obtener todos los usuarios vigentes
     */
    @Query("SELECT u FROM Usuario u WHERE :fechaActual BETWEEN u.usuVigDesde AND u.usuVigHasta")
    List<Usuario> findAllVigentes(@Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Buscar usuarios por tipo
     */
    List<Usuario> findByUsuTipo(Integer usuTipo);
    
    /**
     * Buscar usuarios por nombre (like)
     */
    @Query("SELECT u FROM Usuario u WHERE LOWER(CONCAT(u.usuNombre, ' ', u.usuApellidoP, ' ', u.usuApellidoM)) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Usuario> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    /**
     * Buscar usuario para autenticación (username o email)
     */
    @Query("SELECT u FROM Usuario u WHERE (u.usuUser = :credential OR u.usuEmail = :credential) AND :fechaActual BETWEEN u.usuVigDesde AND u.usuVigHasta")
    Optional<Usuario> findByCredentialAndVigente(@Param("credential") String credential, @Param("fechaActual") LocalDate fechaActual);
    
    /**
     * Llamar a la función stored procedure para cambiar contraseña
     */
    @Query(value = "SELECT web_eventuales.sp_editar_clave_usuario(:codUser, :passAntiguo, :passNuevo, :passRepite)", nativeQuery = true)
    String changePassword(@Param("codUser") Integer codUser, 
                         @Param("passAntiguo") String passAntiguo, 
                         @Param("passNuevo") String passNuevo, 
                         @Param("passRepite") String passRepite);
}