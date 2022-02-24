package co.edu.unicauca.serviciousuario.service;

import co.edu.unicauca.serviciousuario.dto.UsuarioDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UsuarioManagementService {

    List<UsuarioDTO> list();
    UsuarioDTO listById(String id) throws ExecutionException, InterruptedException;
    Boolean add(UsuarioDTO usuario);
    Boolean edit(String id, UsuarioDTO usuario);
    Boolean delete(String id);

}
