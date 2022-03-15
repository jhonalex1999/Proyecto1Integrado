/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

import co.edu.unicauca.servidor.dto.CaidaLibreDTO;
import co.edu.unicauca.servidor.dto.LeyHookeDTO;
import co.edu.unicauca.servidor.dto.MovimientoParabolicoDTO;
import co.edu.unicauca.servidor.dto.Variables_Caida_LibreDTO;
import co.edu.unicauca.servidor.dto.Variables_Ley_HookeDTO;
import co.edu.unicauca.servidor.dto.Variables_Movimiento_ParabolicoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julio
 */
public interface LaboratorioManagementService {
    Boolean descargarDatos(int codigo_planta);

    List<Variables_Ley_HookeDTO> listarDatosHardwareLeyDeHooke();

    List<Variables_Movimiento_ParabolicoDTO> listarDatosHardwareMovimientoParabolico();

    List<Variables_Caida_LibreDTO> listarDatosHardwareCaidaLibre();

    Boolean insertarProblema(String idLaboratorio, String problema);

    Boolean finalizarPractica(int codGrupal);

    Boolean buscarCompletitudEstudiantes(int codGrupal);

    String buscarQuienEsLider(String correo);

    Integer saberCodigoGrupo(String correo);

    ArrayList<String> listar_Altura_CL(int codigo_planta);

    ArrayList<String> listar_Pesos_LH(int codigo_planta);

    ArrayList<String> listar_Angulo_MP(int codigo_planta);

    ArrayList<String> listar_Velocidad_MP(int codigo_planta);

    Boolean iniciarLeyHooke(int peso);
    Boolean iniciarCaidaLibre(int peso);
    Boolean iniciarMovimientoParabolico(int angulo, int velocidad);

    Boolean finalizarProceso(String planta);

    ArrayList<Double> retornarTiempo(int id_planta);

    ArrayList<Double> retornarAltura(int codigo_planta);

    ArrayList<Double> retornarElongaciones(int codigo_planta);

    ArrayList<Double> retornarPesos(int codigo_planta);

    ArrayList<Double> retornarX(int codigo_planta);

    ArrayList<Double> retornarY(int codigo_planta);

}
