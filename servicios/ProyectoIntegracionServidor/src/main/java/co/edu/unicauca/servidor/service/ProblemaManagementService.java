package co.edu.unicauca.servidor.service;

import co.edu.unicauca.servidor.dto.ProblemaDTO;

import java.util.List;

/**
 *
 * @author Modulo de Docentes
 */
public interface ProblemaManagementService {

    List<ProblemaDTO> list();
    Boolean add(ProblemaDTO problema);
    Boolean edit(String id, ProblemaDTO problema);
    Boolean delete(String id);
}
