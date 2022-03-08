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
 * @author Julio
 */

@Data
public class CaidaLibreDTO {
    private ArrayList<Integer> altura;
    private Integer codigo_planta;
    private String foto;
    private String id_practica;
    private ArrayList<Integer> tiempo;
}
