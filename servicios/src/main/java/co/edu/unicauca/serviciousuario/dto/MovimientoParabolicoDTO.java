/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto1.ServidorLaboratorio.dto;

import lombok.Data;

/**
 *
 * @author julio
 */

@Data
public class MovimientoParabolicoDTO {

    private String id_laboratorio;
    private String id_practica;
    private Integer cod_planta;
    private ArrayList<Integer> x_max;
    private ArrayList<Integer> y_max;
    private ArrayList<Integer> tiempo;
    private ArrayList<String> foto;
}
