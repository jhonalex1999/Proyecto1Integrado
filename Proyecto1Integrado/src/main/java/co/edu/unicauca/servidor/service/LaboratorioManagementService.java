/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

import co.edu.unicauca.servidor.dto.AgendamientoDTO;
import java.util.List;

/**
 *
 * @author admin
 */
public interface LaboratorioManagementService {
        List<AgendamientoDTO> listarAgendamiento(int codigoPlanta);
}
