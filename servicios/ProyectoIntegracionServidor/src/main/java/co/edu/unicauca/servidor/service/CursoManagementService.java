/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

import co.edu.unicauca.servidor.dto.CursoDTO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Modulo Docentes
 */
public interface CursoManagementService {

    List<CursoDTO> list();

    CursoDTO listById(String id) throws ExecutionException, InterruptedException;

    List<CursoDTO> listByIdDocente(String id);

    Boolean add(CursoDTO curso);

    Boolean edit(String id, CursoDTO curso);

    Boolean delete(String id);
}
