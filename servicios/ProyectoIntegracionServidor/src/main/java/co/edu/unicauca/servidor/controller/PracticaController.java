/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.controller;

import co.edu.unicauca.servidor.service.PracticaManagementService;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author julio
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/practica")

public class PracticaController {

    @Autowired
    private PracticaManagementService service;

    @GetMapping(value = "/{codigoPlanta}/listarAgendamiento")
    public ResponseEntity listarAgendamiento(@PathVariable(value = "codigoPlanta") int codigoPlanta) {
        return new ResponseEntity(service.listarAgendamiento(codigoPlanta), HttpStatus.OK);
    }

    @GetMapping(value = "/listarPracticasCurso/{correo}")
    public ResponseEntity listarPracticasCurso(@PathVariable(value = "correo") String correo){
        return new ResponseEntity(service.listarPracticasCurso(correo), HttpStatus.OK);
    }
    
     @PostMapping(value = "{codigoCurso}/CrearPractica")
    public ResponseEntity crearPractica(@PathVariable(value = "codigoCurso") int codigoCurso) {
        return new ResponseEntity(service.crearPractica(codigoCurso), HttpStatus.OK);
    }
    
     @PutMapping(value = "{codCurso}/modificarPractica/{idPractica}")
    public ResponseEntity modificarPractica(@PathVariable(value = "codCurso") int codigoCurso, @PathVariable(value = "idPractica") String idPractica) {
        return new ResponseEntity(service.modificarPractica(codigoCurso, idPractica), HttpStatus.OK);
    }
    
     @DeleteMapping(value = "{codCurso}/eliminarPractica/{idPractica}")
    public ResponseEntity eliminarPractica(@PathVariable(value = "codCurso") int codigoCurso, @PathVariable(value = "idPractica") String idPractica) {
        return new ResponseEntity(service.eliminarPractica(codigoCurso, idPractica), HttpStatus.OK);
    }
    
    
    
}