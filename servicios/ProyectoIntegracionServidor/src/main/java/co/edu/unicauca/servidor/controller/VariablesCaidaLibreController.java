/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.controller;

import co.edu.unicauca.servidor.dto.Variables_Caida_LibreDTO;

import co.edu.unicauca.servidor.service.VariablesManagementService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ModuloDocentes
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/practica")

public class VariablesCaidaLibreController {

    @Autowired
    private VariablesManagementService service;

    @GetMapping(value = "/2/listarVariables/{idvariables}")
    public ResponseEntity listarPracticasCurso(@PathVariable(value = "idvariables") String idvariables, @RequestBody Variables_Caida_LibreDTO variables) {
        return new ResponseEntity(service.listarVariables(idvariables,variables), HttpStatus.OK);
    }

    @PostMapping(value = "/2/add")
    public ResponseEntity add(@RequestBody Variables_Caida_LibreDTO variables) {
        return new ResponseEntity(service.add(variables), HttpStatus.OK);
    }

    @PutMapping(value = "/2/update/{idvariable}")
    public ResponseEntity edit(@PathVariable(value = "idvariable") String idvariable, @RequestBody Variables_Caida_LibreDTO variables) {
        return new ResponseEntity(service.edit(idvariable, variables), HttpStatus.OK);
    }

    @DeleteMapping(value = "/2/delete/{idvariable}")
    public ResponseEntity delete(@PathVariable(value = "idvariable") String idvariable, @RequestBody Variables_Caida_LibreDTO variables) {
        return new ResponseEntity(service.delete(idvariable,variables), HttpStatus.OK);
    }
}
