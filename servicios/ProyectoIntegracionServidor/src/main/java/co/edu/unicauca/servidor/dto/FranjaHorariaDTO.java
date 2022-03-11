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
public class FranjaHorariaDTO {
    private String fecha;
    private String hora_fin;
    private String hora_inicio;
    private String id_practica;
    private String id_franja_horaria;
}
