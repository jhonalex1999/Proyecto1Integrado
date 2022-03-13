package co.edu.unicauca.servidor.controller;

import co.edu.unicauca.servidor.dto.CursoDTO;
import co.edu.unicauca.servidor.dto.ProblemaDTO;
import co.edu.unicauca.servidor.service.CursoManagementService;
import co.edu.unicauca.servidor.service.ProblemaManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 *
 * @author Modulo Docente
 */
@RestController
@RequestMapping(value = "/problema")
@CrossOrigin(origins = "http://localhost:4200")
public class ProblemaController {

    @Autowired
    private ProblemaManagementService service;

    @GetMapping(value = "/list")
    public ResponseEntity list(){
        return new ResponseEntity(service.list(), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity add(@RequestBody ProblemaDTO problema){
        return new ResponseEntity(service.add(problema), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity edit(@PathVariable(value = "id") String id, @RequestBody ProblemaDTO problema){
        return new ResponseEntity(service.edit(id, problema), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") String id){
        return new ResponseEntity(service.delete(id), HttpStatus.OK);
    }
}
