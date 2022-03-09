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
public class CursoDTO {
    private String codigo_matricula;
    private String fecha_creacion;
    private String fecha_eliminacion;
    private String id_docente;
    private String nombre_curso;
    private Integer tamanio_grupo;
}
