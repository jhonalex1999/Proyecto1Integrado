/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.serviceImpl;

import co.edu.unicauca.servidor.dto.PracticaDTO;
import co.edu.unicauca.servidor.dto.Variables_Ley_HookeDTO;
import co.edu.unicauca.servidor.dto.Variables_Caida_LibreDTO;
import co.edu.unicauca.servidor.dto.Variables_Movimiento_ParabolicoDTO;

import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.PracticaManagementService;
import co.edu.unicauca.servidor.service.VariablesManagementService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USUARIO
 */
@Service
public class variablesManagementServiceImpl implements VariablesManagementService {

    @Autowired
    private FirebaseInitializer firebase;
    List<PracticaDTO> response = new ArrayList<>();

    @Override
    public Object add(Variables_Caida_LibreDTO variables) {
        Map<String, Object> docData = getDocDataVariablesCaidaLibre(variables);

        CollectionReference Variables = getCollection("variables_caida_libre");
        ApiFuture<WriteResult> writeResultApiFuture = Variables.document().create(docData);

        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Object add(Variables_Ley_HookeDTO variables) {
        Map<String, Object> docData = getDocDataVariablesLeyHooke(variables);

        CollectionReference Variables = getCollection("variables_ley_hooke");
        ApiFuture<WriteResult> writeResultApiFuture = Variables.document().create(docData);

        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Object add(Variables_Movimiento_ParabolicoDTO variables) {
        Map<String, Object> docData = getDocDataVariablesMovimientoParabolico(variables);

        CollectionReference Variables = getCollection("variables_ley_hooke");
        ApiFuture<WriteResult> writeResultApiFuture = Variables.document().create(docData);

        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Object edit(String idvariable, Variables_Caida_LibreDTO variables) {
        Map<String, Object> docData = getDocDataVariablesCaidaLibre(variables);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("variables_caida_libre").document(idvariable).set(docData);
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Object edit(String idvariable, Variables_Movimiento_ParabolicoDTO variables) {
        Map<String, Object> docData = getDocDataVariablesMovimientoParabolico(variables);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("variables_movimiento_parabolico").document(idvariable).set(docData);
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Object edit(String idvariable, Variables_Ley_HookeDTO variables) {
        Map<String, Object> docData = getDocDataVariablesLeyHooke(variables);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("variables_ley_hooke").document(idvariable).set(docData);
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Variables_Caida_LibreDTO listarVariables(String idvariables, Variables_Caida_LibreDTO variables) {

        List<Variables_Caida_LibreDTO> response = new ArrayList<>();
        Variables_Caida_LibreDTO Variable;
       
        

        Query query = firebase.getFirestore().collection("variable_caida_libre").whereEqualTo("id_practica", idvariables);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                Variable = doc.toObject(Variables_Caida_LibreDTO.class);
                Variable.setId_practica(doc.getId());
                response.add(Variable);
            }
            return (Variables_Caida_LibreDTO) response;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Object listarVariables(String idvariables, Variables_Movimiento_ParabolicoDTO variables) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object listarVariables(String idvariables, Variables_Ley_HookeDTO variables) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object delete(String idvariable, Variables_Caida_LibreDTO variables) {
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("variables_caida_libre").document(idvariable).delete();
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Object delete(String idvariable, Variables_Ley_HookeDTO variables) {
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("variables_ley_hooke").document(idvariable).delete();
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Object delete(String idvariable, Variables_Movimiento_ParabolicoDTO variables) {

        ApiFuture<WriteResult> writeResultApiFuture = getCollection("variables_movimiento_parabolico").document(idvariable).delete();
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    private CollectionReference getCollection(String Colecion) {
        return firebase.getFirestore().collection(Colecion);
    }

    private Map<String, Object> getDocDataVariablesLeyHooke(Variables_Ley_HookeDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("id_practica", post.getId_practica());
        docData.put("rango_fuerza", post.getRango_fuerza());
        docData.put("tipo_resorte", post.getTipo_resorte());
        return docData;
    }

    private Map<String, Object> getDocDataVariablesCaidaLibre(Variables_Caida_LibreDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("id_practica", post.getId_practica());
        docData.put("num_lanzamientos", post.getNum_lanzamientos());
        docData.put("rango_altura", post.getRango_altura());
        return docData;
    }

    private Map<String, Object> getDocDataVariablesMovimientoParabolico(Variables_Movimiento_ParabolicoDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("id_practica", post.getId_practica());
        docData.put("rango_angulo", post.getRango_angulo());
        docData.put("velocidad", post.getVelocidad());
        return docData;
    }

    private Map<String, Object> getDataPractica(PracticaDTO post) {
        Map<String, Object> docData = new HashMap<>();

        //docData.put("archivos", post.);
        docData.put("cod_planta", post.getCod_planta());
        docData.put("id_curso", post.getId_curso());
        docData.put("descripcion", post.getDescripcion());
        docData.put("estado", post.getEstado());
        docData.put("fecha_entrega", post.getFecha_entrega());
        docData.put("titulo", post.getTitulo());

        return docData;
    }

}
