package co.edu.unicauca.servidor.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import lombok.Data;

/**
 *
 * @author Integracion Modulo Estudiante y Modulo Docente
 */
@Data
public class ProblemaDTO {

    private String id_problema;
    private Integer id_practica;
    private String descripcion;
}
