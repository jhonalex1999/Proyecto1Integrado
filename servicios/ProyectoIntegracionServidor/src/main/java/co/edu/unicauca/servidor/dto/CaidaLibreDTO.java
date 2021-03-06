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
 * @author Julio
 */
@Data
public class CaidaLibreDTO {
    private String id;
    private Integer codigo_planta;
    private HashMap<String, Double> errores;
    private HashMap<String, Double> gravedadN;
    private int nRep;
    private HashMap<String, Double> tiempo;
    private HashMap<String, Double> altura;
    private ArrayList<Integer> pesos_utilizados;
}
