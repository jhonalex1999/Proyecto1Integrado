/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.dto;

import lombok.Data;

/**
 *
 * @author julio
 */
@Data
public class ProblemaDTO {
    private String id_problema;
    private Integer id_laboratorio;
    private String descripcion;
}
