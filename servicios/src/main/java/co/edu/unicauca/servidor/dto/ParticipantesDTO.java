/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.dto;

import lombok.Data;

/**
 *
 * @author julio
 */
@Data
public class ParticipantesDTO {
    private String id;
    private Integer codGrupal;
    private String correo;
    private String rol;
    private Integer estado;
}
