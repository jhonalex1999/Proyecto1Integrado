/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.controller;

import co.edu.unicauca.servidor.dto.UsuarioDTO;
import co.edu.unicauca.servidor.service.UsuarioManagamentService;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioManagamentService service;

    @GetMapping(value = "/{correo}/buscarCursosImpartidos")
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

    @PostMapping(value = "{correo}/{codCurso}/matricularCurso")
    public ResponseEntity matricularCurso(@PathVariable(value = "codCurso") String codigoCurso, @PathVariable(value = "correo") String correo_institucional) {
        return new ResponseEntity(service.agregarCurso(correo_institucional, codigoCurso), HttpStatus.OK);
    }

    @GetMapping(value = "/{correo}/buscarCursosMatriculados")
    public ResponseEntity buscarCursosMatriculados(@PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.buscarCursosMatriculados(correo), HttpStatus.OK);
    }

    @PostMapping(value = "{correo}/{nombreCompleto}/ingresarUsuario")
    public ResponseEntity ingresarUsuario(@PathVariable(value = "correo") String correo_institucional, @PathVariable(value = "nombreCompleto") String nombre) {
        return new ResponseEntity(service.ingresarUsuario(correo_institucional, nombre), HttpStatus.OK);
    }

    @GetMapping(value = "/{correo}/sacarRol")
    public ResponseEntity sacarRol(@PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.sacarRol(correo), HttpStatus.OK);
    }

    @GetMapping(value = "/{correo}/cambiarEstadoParticipanteEntrada")
    public ResponseEntity cambiarEstadoParticipanteEntrada(@PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.cambiarEstadoParticipanteEntrada(correo), HttpStatus.OK);
    }

    @GetMapping(value = "/{correo}/cambiarEstadoParticipanteSalida")
    public ResponseEntity cambiarEstadoParticipanteSalida(@PathVariable(value = "correo") String correo) {
        return new ResponseEntity(service.cambiarEstadoParticipanteSalida(correo), HttpStatus.OK);
    }

    @GetMapping(value = "/listestcurso/{id}")
    public ResponseEntity listEstCurso(@PathVariable(value = "id") String id)  {
        return new ResponseEntity(service.listarEstudiantesCurso(id), HttpStatus.OK);
    }

     @GetMapping(value = "/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @GetMapping(value = "/list/{id}")
    public ResponseEntity edit(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(service.listById(id), HttpStatus.OK);
    }
    @PostMapping(value = "/add")
    public ResponseEntity add(@RequestBody UsuarioDTO usuario) {
        return new ResponseEntity(service.add(usuario), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity edit(@PathVariable(value = "id") String id, @RequestBody UsuarioDTO usuario){
        return new ResponseEntity(service.edit(id,usuario), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
    

}
