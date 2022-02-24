package co.edu.unicauca.serviciousuario.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String idUsuario;
    private String correo;
    private String nombre_completo;
    private String rol;
}
