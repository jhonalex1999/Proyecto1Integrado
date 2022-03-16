package co.edu.unicauca.servidor.dto;

import java.util.ArrayList;
import lombok.Data;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Integracion Modulo Estudiante y Modulo Docente
 */
@Data
public class Variables_Caida_LibreDTO {

    private String id;
    private Integer id_practica;
    private String num_lanzamientos;
    private ArrayList<Integer> rango_altura;
}
