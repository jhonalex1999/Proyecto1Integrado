/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.controller;


import co.edu.unicauca.servidor.service.UsuarioManagamentService;
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
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioManagamentService service;
 
    
     @GetMapping(value = "/{correo}/buscarCursosMatriculados")
    public ResponseEntity buscarCursosImpartidos(@PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.buscarCursosImpartidos(correo), HttpStatus.OK);
    }
 
    @PostMapping(value = "{correo}/CrearCurso")
    public ResponseEntity crearCurso(@PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.crearCurso(correo), HttpStatus.OK);
    }
    
     @PutMapping(value = "{correo}/{codCurso}/matricularCurso")
    public ResponseEntity modificarCurso(@PathVariable(value = "codCurso") int codigoCurso, @PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.modificarCurso(correo, codigoCurso), HttpStatus.OK);
    }
    
     @DeleteMapping(value = "{correo}/{codCurso}/matricularCurso")
    public ResponseEntity eliminarCurso(@PathVariable(value = "codCurso") int codigoCurso, @PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.eliminarCurso(correo, codigoCurso), HttpStatus.OK);
    }
    
    
    

}
