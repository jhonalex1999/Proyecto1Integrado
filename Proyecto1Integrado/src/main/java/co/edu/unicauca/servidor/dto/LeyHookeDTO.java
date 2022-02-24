/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.dto;

import java.util.ArrayList;
import lombok.Data;

/**
 *
 * @author julio
 */

@Data
public class LeyHookeDTO {
    private Integer codigo_planta;
    private ArrayList<String> elongacion;
    private String foto;
    private ArrayList<String> fuerza; 
    private String id_practica;
    
}
