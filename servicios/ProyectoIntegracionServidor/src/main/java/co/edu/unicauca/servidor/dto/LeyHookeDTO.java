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
 * @author Integracion Modulo Estudiante
 */
@Data
public class LeyHookeDTO {

    private String id;
    private Integer codigo_planta;
    private ArrayList<String> elongacion;
    private String foto;
    private ArrayList<String> fuerza;
    private String id_practica;
    private ArrayList<String> rangos_elongacion;
    private ArrayList<String> rangos_fuerza;
}
