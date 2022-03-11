/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

import co.edu.unicauca.servidor.dto.FranjaHorariaDTO;


import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author USUARIO
 */
public interface FranjaHorariaManagementService {
    List<FranjaHorariaDTO> list();
    FranjaHorariaDTO listById(String id) throws ExecutionException, InterruptedException;
    Boolean add(FranjaHorariaDTO franja);
    Boolean edit(String id, FranjaHorariaDTO franja);
    Boolean delete(String id);
}
