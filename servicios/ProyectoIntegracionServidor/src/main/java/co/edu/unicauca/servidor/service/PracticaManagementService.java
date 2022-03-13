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
import java.util.concurrent.ExecutionException;

/**
 *
 * @author julio
 */
public interface PracticaManagementService {

    List<AgendamientoDTO> listarAgendamiento(int codigoPlanta);//practica

    List<PracticaDTO> listarPracticas();

    Boolean verificarAgendamiento(int codGrupal);

    Integer agregarParticipantes(ArrayList<String> participantes, int idAgendamiento);//practica

    Boolean buscarHorario(int idAgendamiento, int codGrupal);//practica

    Integer saberCodigoGrupo(String correo);//practica

    public ArrayList<String> listarPracticasCurso(String codigoCurso);

    public Object crearPractica(String codigoCurso);

    public Object modificarPractica(String codigoCurso, String idPractica);

    public Object eliminarPractica(String codigoCurso, String idPractica);
    
    public ArrayList<String> listarVariablesPorDefecto(String idPractica);
    
    public ArrayList<String> listarVariablesPracticasCurso(String idPractica);

    public Object crearVariablesPractica(String idPractica);

    public Object modificarVariablesPractica(String codigoCurso, String idPractica);

    public Object eliminarVariablesPractica(String codigoCurso, String idPractica);
    
    List<PracticaDTO> list();
    
    PracticaDTO listById(String id) throws ExecutionException, InterruptedException;

    Boolean add(PracticaDTO practica);
    Boolean edit(String id, PracticaDTO practica);
    Boolean delete(String id);

    public List<PracticaDTO> listByIdDocente(String correo);
    
}
