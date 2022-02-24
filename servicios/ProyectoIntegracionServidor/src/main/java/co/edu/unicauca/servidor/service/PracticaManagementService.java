/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

import co.edu.unicauca.servidor.dto.AgendamientoDTO;
import co.edu.unicauca.servidor.dto.CursoDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author julio
 */
public interface PracticaManagementService {

    List<AgendamientoDTO> listarAgendamiento(int codigoPlanta);
    
    ArrayList<String> listarPracticasCurso(String correo);
}
