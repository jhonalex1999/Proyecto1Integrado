/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

import java.util.ArrayList;

/**
 *
 * @author julio
 */
public interface UsuarioManagamentService {

    public ArrayList<String> buscarCursosImpartidos(String correo);

    public Object crearCurso(String correo);

    public Object modificarCurso(String correo, int codigoCurso);

    public Object eliminarCurso(String correo, int codigoCurso);

    public ArrayList<String> buscarCursosMatriculados(String correo);

    public Boolean agregarCurso(String correo_institucional, int codigo_curso);

    public Boolean ingresarUsuario(String correo_institucional, String nombre);

    public String sacarRol(String correo);

    Boolean cambiarEstadoParticipanteEntrada(String correo);

    Boolean cambiarEstadoParticipanteSalida(String correo);
}
