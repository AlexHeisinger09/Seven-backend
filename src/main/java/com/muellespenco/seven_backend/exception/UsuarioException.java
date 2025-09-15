package com.muellespenco.seven_backend.exception;

public class UsuarioException extends RuntimeException {
    
    private final String codigo;
    
    public UsuarioException(String message) {
        super(message);
        this.codigo = "USUARIO_ERROR";
    }
    
    public UsuarioException(String message, String codigo) {
        super(message);
        this.codigo = codigo;
    }
    
    public UsuarioException(String message, Throwable cause) {
        super(message, cause);
        this.codigo = "USUARIO_ERROR";
    }
    
    public UsuarioException(String message, String codigo, Throwable cause) {
        super(message, cause);
        this.codigo = codigo;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    // Excepciones específicas
    public static class UsuarioNoEncontradoException extends UsuarioException {
        public UsuarioNoEncontradoException(String message) {
            super(message, "USUARIO_NO_ENCONTRADO");
        }
    }
    
    public static class CredencialesInvalidasException extends UsuarioException {
        public CredencialesInvalidasException(String message) {
            super(message, "CREDENCIALES_INVALIDAS");
        }
    }
    
    public static class UsuarioNoVigenteException extends UsuarioException {
        public UsuarioNoVigenteException(String message) {
            super(message, "USUARIO_NO_VIGENTE");
        }
    }
    
    public static class TokenInvalidoException extends UsuarioException {
        public TokenInvalidoException(String message) {
            super(message, "TOKEN_INVALIDO");
        }
    }
}