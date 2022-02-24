package co.edu.unicauca.serviciousuario.controller;

import co.edu.unicauca.serviciousuario.dto.UsuarioDTO;
import co.edu.unicauca.serviciousuario.service.UsuarioManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController

@RequestMapping(value = "/usuario")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioManagementService service;

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
