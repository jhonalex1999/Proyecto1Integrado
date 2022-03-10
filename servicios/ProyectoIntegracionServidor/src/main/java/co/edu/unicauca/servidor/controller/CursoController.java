/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.controller;

import co.edu.unicauca.servidor.dto.CursoDTO;
import co.edu.unicauca.servidor.service.CursoManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/curso")
@CrossOrigin(origins = "http://localhost:4200")

/**
 *
 * @author Modulo Docente
 */
public class CursoController {
    
    @Autowired
    private CursoManagementService service;

    @GetMapping(value = "/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @GetMapping(value = "/list/{id}")
    public ResponseEntity listId(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(service.listById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/listbydocente/{id}")
    public ResponseEntity listIdDocente(@PathVariable(value = "id") String id)  {
        return new ResponseEntity(service.listByIdDocente(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity add(@RequestBody CursoDTO curso){
        return new ResponseEntity(service.add(curso), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity edit(@PathVariable(value = "id") String id, @RequestBody CursoDTO curso){
        return new ResponseEntity(service.edit(id, curso), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
