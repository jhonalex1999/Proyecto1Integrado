/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

/**
 *
 * @author julio
 */
public interface UsuarioManagamentService {

    public Object buscarCursosImpartidos(String correo);

    public Object crearCurso(String correo);

    public Object modificarCurso(String correo, int codigoCurso);

    public Object eliminarCurso(String correo, int codigoCurso);
    
}
