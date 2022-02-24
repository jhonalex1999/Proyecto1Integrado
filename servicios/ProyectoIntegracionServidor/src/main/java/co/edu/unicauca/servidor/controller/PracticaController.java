/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.controller;

import co.edu.unicauca.servidor.service.PracticaManagementService;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @GetMapping(value = "/listarPracticas")
    public ResponseEntity listarPracticas(){
        return new ResponseEntity(service.listarPracticas(), HttpStatus.OK);
    }
    @GetMapping(value = "/{codGrupal}/verificarAgendamiento")
    public ResponseEntity verificarAgendamiento(@PathVariable(value = "codGrupal") int codGrupal){
        return new ResponseEntity(service.verificarAgendamiento(codGrupal), HttpStatus.OK);
    }
    @PostMapping(value = "/{idAgendamiento}/agregarParticipantes")
    public ResponseEntity agregarParticipantes(@RequestBody ArrayList<String> participantes, @PathVariable(value = "idAgendamiento") Integer idAgendamiento) {
        return new ResponseEntity(service.agregarParticipantes(participantes, idAgendamiento), HttpStatus.OK);
    }
    @GetMapping(value = "/{idAgendamiento}/{codGrupal}/buscarHorario")
    public ResponseEntity buscarHorario(@PathVariable(value = "idAgendamiento") int idAgendamiento, @PathVariable(value = "codGrupal") int codGrupal) {
        return new ResponseEntity(service.buscarHorario(idAgendamiento, codGrupal), HttpStatus.OK);
    }
    @GetMapping(value = "/{correo}/saberCodigoGrupo")
    public ResponseEntity saberCodigoGrupo(@PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.saberCodigoGrupo(correo), HttpStatus.OK);
    }
    
    
    
    
    
    @GetMapping(value = "/listarPracticasCurso/{correo}")
    public ResponseEntity listarPracticasCurso(@PathVariable(value = "correo") String correo){
        return new ResponseEntity(service.listarPracticasCurso(correo), HttpStatus.OK);
    }
}