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
 * @author admin
 */
@Data
public class Variables_Ley_HookeDTO {

    private String id;
    private Integer codigo_planta;
    private ArrayList<String> rango_pesos;
}