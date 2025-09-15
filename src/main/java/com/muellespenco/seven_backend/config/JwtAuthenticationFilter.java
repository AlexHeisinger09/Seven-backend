package com.muellespenco.seven_backend.config;

import com.muellespenco.seven_backend.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain chain) throws ServletException, IOException {
        
        final String authorizationHeader = request.getHeader("Authorization");
        
        String username = null;
        String jwt = null;
        
        // Extraer JWT del header Authorization
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                logger.error("Error extrayendo username del JWT: " + e.getMessage());
            }
        }
        
        // Si tenemos username y no hay autenticación previa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Cargar detalles del usuario
            UserDetails userDetails = usuarioService.loadUserByUsername(username);
            
            // Validar token
            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                
                // Crear token de autenticación
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, 
                        userDetails.getAuthorities()
                    );
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Establecer autenticación en el contexto
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        chain.doFilter(request, response);
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        
        // No filtrar estas rutas
        return path.startsWith("/api/v1/auth/") ||
               path.startsWith("/api/v1/actuator/") ||
               path.equals("/api/v1/") ||
               path.startsWith("/favicon.ico") ||
               path.startsWith("/error");
    }
}