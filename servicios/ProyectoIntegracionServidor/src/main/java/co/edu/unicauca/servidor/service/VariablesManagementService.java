/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

import co.edu.unicauca.servidor.dto.Variables_Caida_LibreDTO;
import co.edu.unicauca.servidor.dto.Variables_Ley_HookeDTO;
import co.edu.unicauca.servidor.dto.Variables_Movimiento_ParabolicoDTO;

/**
 *
 * @author USUARIO
 */
public interface VariablesManagementService {

    public Object add(Variables_Caida_LibreDTO variables);

    public Object add(Variables_Ley_HookeDTO variables);

    public Object add(Variables_Movimiento_ParabolicoDTO variables);

    public Object edit(String idvariable, Variables_Caida_LibreDTO variables);

    public Object edit(String idvariable, Variables_Movimiento_ParabolicoDTO variables);

    public Object edit(String idvariable, Variables_Ley_HookeDTO variables);

    public Object delete(String idvariable, Variables_Caida_LibreDTO variables);

    public Object listarVariables(String idvariables, Variables_Caida_LibreDTO variables);

    public Object listarVariables(String idvariables, Variables_Movimiento_ParabolicoDTO variables);

    public Object listarVariables(String idvariables, Variables_Ley_HookeDTO variables);

    public Object delete(String idvariable, Variables_Ley_HookeDTO variables);

    public Object delete(String idvariable, Variables_Movimiento_ParabolicoDTO variables);

}
