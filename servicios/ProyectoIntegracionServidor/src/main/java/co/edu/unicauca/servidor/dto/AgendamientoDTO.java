package co.edu.unicauca.servidor.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import lombok.Data;

/**
 *
 * @author Integracion Modulo Estudiante y Modulo Docente
 */
@Data
public class AgendamientoDTO {

    private String id;
    private Integer cod_grupal;
    private Integer cod_planta;
    private Boolean estado_disposicion;
    private String fecha;
    private String hora_fin;
    private String hora_inicio;
    private String id_franja;
    private Integer id_agendamiento;

}
