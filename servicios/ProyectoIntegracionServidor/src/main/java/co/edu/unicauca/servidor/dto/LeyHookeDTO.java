package co.edu.unicauca.servidor.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.HashMap;
import lombok.Data;

/**
 *
 * @author Integracion Modulo Estudiante
 */
@Data
public class LeyHookeDTO {

   
    private String id;
    private Integer codigo_planta;
    private HashMap<String, Double> elongaciones;
    private int nRep;
    private HashMap<String, Double> pesos;
    private ArrayList<Integer> pesos_utilizados;
}
