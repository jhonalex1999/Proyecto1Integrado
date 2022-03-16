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
 * @author Integracion Modulo Estudiante y Modulo Docente
 */
@Data
public class Variables_Movimiento_ParabolicoDTO {
    private String id;
    private Integer id_practica;
    private ArrayList<Integer> rango_angulo;
    private ArrayList<Integer> rango_velocidad;

}
