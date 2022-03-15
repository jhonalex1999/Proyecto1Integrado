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
 * @author julio
 */
@Data
public class MovimientoParabolicoDTO {

    private String id;
    private Integer codigo_planta;
    private ArrayList<String> datos_x;
    private ArrayList<String> datos_y;
    private int nRep;
    private ArrayList<String> tiempo;
    private String url_imagen;
    private int velocidad;
}
