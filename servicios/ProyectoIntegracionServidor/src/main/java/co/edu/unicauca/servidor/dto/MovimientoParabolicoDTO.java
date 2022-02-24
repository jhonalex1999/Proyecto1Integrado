package co.edu.unicauca.servidor.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author julio
 */

@Data
public class MovimientoParabolicoDTO {

  
    private String id_practica;
    private Integer cod_planta;
    private ArrayList<String> x_max;
    private ArrayList<String> y_max;
    private ArrayList<String> tiempo;
    private ArrayList<String> foto;
}
