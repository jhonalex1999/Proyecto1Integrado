/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.serviceImpl;

import co.edu.unicauca.servidor.dto.CursoDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.LaboratorioManagementService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author julio
 */
@Service
public class LaboratorioManagementServiceImpl implements LaboratorioManagementService{

    private FirebaseInitializer firebase;
    List<CursoDTO> response = new ArrayList<>();
}
