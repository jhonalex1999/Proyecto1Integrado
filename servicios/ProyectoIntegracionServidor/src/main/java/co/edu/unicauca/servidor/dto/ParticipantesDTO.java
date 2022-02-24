package co.edu.unicauca.servidor.dto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import lombok.Data;

/**
 *
 * @author julio
 */
@Data
public class ParticipantesDTO {
    private String id_participante;
    private Integer cod_grupal;
    private String correo;
    private String rol;
    private Integer estado;
}
