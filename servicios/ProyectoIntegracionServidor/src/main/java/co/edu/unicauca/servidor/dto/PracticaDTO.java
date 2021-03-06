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
public class PracticaDTO {

    private String id_practica;
    private ArrayList<String> archivos;
    private String cod_planta;
    private String descripcion;
    private String estado;
    private String fecha_entrega;
    private String id_curso;
    private String titulo;

}
