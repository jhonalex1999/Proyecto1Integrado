/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.controller;

import co.edu.unicauca.servidor.service.LaboratorioManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author julio
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/laboratorio")
public class LaboratorioController {

    @Autowired
    private LaboratorioManagementService service;
    
    @GetMapping(value = "/pdf")
    public ResponseEntity pdf() {
        return new ResponseEntity(service.crearPdf(), HttpStatus.OK);
    }
    @GetMapping(value = "/listarDatosHardwareLeyDeHooke")
    public ResponseEntity listarDatosHardwareLeyDeHooke() {
        return new ResponseEntity(service.listarDatosHardwareLeyDeHooke(), HttpStatus.OK);
    }

    @GetMapping(value = "/listarDatosHardwareMovimientoParabolico")
    public ResponseEntity listarDatosHardwareMovimientoParabolico() {
        return new ResponseEntity(service.listarDatosHardwareMovimientoParabolico(), HttpStatus.OK);
    }
    @GetMapping(value = "/listarDatosHardwareCaidaLibre")
    public ResponseEntity listarDatosHardwareCaidaLibre() {
        return new ResponseEntity(service.listarDatosHardwareCaidaLibre(), HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/{codGrupal}/finalizarPractica")
    public ResponseEntity finalizarPractica(@PathVariable(value = "codGrupal") int codGrupal) {
        return new ResponseEntity(service.finalizarPractica(codGrupal), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{codCurso}/buscarCompletitudEstudiantes")
    public ResponseEntity buscarCompletitudEstudiantes(@PathVariable(value = "codCurso") int codCurso) {
        return new ResponseEntity(service.buscarCompletitudEstudiantes(codCurso), HttpStatus.OK);
    }

    @GetMapping(value = "/{correo}/buscarQuienEsLider")
    public ResponseEntity buscarQuienEsLider(@PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.buscarQuienEsLider(correo), HttpStatus.OK);
    }


    @PostMapping(value = "/{idLaboratorio}/{problema}/reportarError")
    public ResponseEntity reportarError(@PathVariable(value = "idLaboratorio") int idLaboratorio, @PathVariable(value = "problema") String problema) {
        return new ResponseEntity(service.reportarError(idLaboratorio, problema), HttpStatus.OK);
    }
    
}
