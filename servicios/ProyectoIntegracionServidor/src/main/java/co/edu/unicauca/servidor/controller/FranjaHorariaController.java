/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.controller;


import co.edu.unicauca.servidor.dto.FranjaHorariaDTO;
import co.edu.unicauca.servidor.service.FranjaHorariaManagementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/franja")
@CrossOrigin(origins = "http://localhost:4200")


/**
 *
 * @author USUARIO
 */
public class FranjaHorariaController {
    @Autowired
    private FranjaHorariaManagementService service;

    @GetMapping(value = "/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @GetMapping(value = "/list/{id}")
    public ResponseEntity edit(@PathVariable(value = "id") String id) throws ExecutionException, InterruptedException {
        return new ResponseEntity(service.listById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity add(@RequestBody FranjaHorariaDTO franja){
        return new ResponseEntity(service.add(franja), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity edit(@PathVariable(value = "id") String id, @RequestBody FranjaHorariaDTO franja){
        return new ResponseEntity(service.edit(id, franja), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
