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
public class PracticaDTO {

    private String id;
    private Integer idPractica;
    private String titulo;
    private String descripcion;
    private Integer estado;
    private String fechaEntrega;
    private Integer codigoPlanta;
}
