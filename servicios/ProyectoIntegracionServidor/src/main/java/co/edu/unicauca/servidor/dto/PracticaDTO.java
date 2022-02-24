package co.edu.unicauca.servidor.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import lombok.Data;

/**
 *
 * @author julio
 */
@Data
public class PracticaDTO {

    private String id_practica;
    private Integer cod_planta;
    private String titulo;
    private String descripcion;
    private Integer estado;
    private String fecha_entrega;
   
}
