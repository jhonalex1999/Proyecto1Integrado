/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

import co.edu.unicauca.servidor.dto.AgendamientoDTO;

import co.edu.unicauca.servidor.dto.PracticaDTO;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author julio
 */
public interface PracticaManagementService {

    List<AgendamientoDTO> listarAgendamiento(int codigoPlanta);//practica
    List<PracticaDTO> listarPracticas();
    Boolean verificarAgendamiento(int codGrupal);
    Integer agregarParticipantes(ArrayList<String> participantes,int idAgendamiento);//practica
    Boolean buscarHorario(int idAgendamiento, int codGrupal);//practica
    Integer saberCodigoGrupo(String correo);//practica
    
    
    ArrayList<String> listarPracticasCurso(String correo);

    public Object crearPractica(int codigoCurso);

    public Object modificarPractica(int codigoCurso, String idPractica);

    public Object eliminarPractica(int codigoCurso, String idPractica);
}
