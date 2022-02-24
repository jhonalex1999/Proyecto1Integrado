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
public class CaidaLibreDTO {
    private ArrayList<Integer> altura;
    private Integer codigo_planta;
    private String foto;
    private String id_practica;
    private ArrayList<Integer> tiempo;
}
