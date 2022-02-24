package co.edu.unicauca.servidor.controller;


import co.edu.unicauca.servidor.service.LaboratorioManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author admin
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/laboratorio")

public class LaboratorioController {

    @Autowired
    private LaboratorioManagementService service;


    @GetMapping(value = "/{codigoPlanta}/listarAgendamiento")
    public ResponseEntity listarAgendamiento(@PathVariable(value = "codigoPlanta") int codigoPlanta) {
        return new ResponseEntity(service.listarAgendamiento(codigoPlanta), HttpStatus.OK);
    }

}
