/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.service;

import co.edu.unicauca.servidor.dto.CaidaLibreDTO;
import co.edu.unicauca.servidor.dto.LeyHookeDTO;
import co.edu.unicauca.servidor.dto.MovimientoParabolicoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julio
 */
public interface LaboratorioManagementService {

    List<LeyHookeDTO> listarDatosHardwareLeyDeHooke();

    List<MovimientoParabolicoDTO> listarDatosHardwareMovimientoParabolico();

    List<CaidaLibreDTO> listarDatosHardwareCaidaLibre();

    Boolean crearPdf();

    Boolean finalizarPractica(int codGrupal);

    Boolean buscarCompletitudEstudiantes(int codGrupal);

    String buscarQuienEsLider(String correo);

    Boolean reportarError(int idLaboratorio, String descripcion);

    ArrayList<String> listar_Altura_CL(int codigo_planta);

    ArrayList<String> listar_Elongacion_LH(int codigo_planta);

    ArrayList<String> listar_Fuerza_LH(int codigo_planta);

    ArrayList<String> listar_Angulo_MP(int codigo_planta);

    ArrayList<String> listar_Velocidad_MP(int codigo_planta);
}
