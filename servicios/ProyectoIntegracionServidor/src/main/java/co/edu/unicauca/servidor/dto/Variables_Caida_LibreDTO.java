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
    private Integer codigo_planta;
    private Integer num_lanzamientos;
    private ArrayList<String> rangos_altura;
    private Integer peso;
}
