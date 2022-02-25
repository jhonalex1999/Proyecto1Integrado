/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.serviceImpl;

import co.edu.unicauca.servidor.dto.AgendamientoDTO;
import co.edu.unicauca.servidor.dto.CursoDTO;
import co.edu.unicauca.servidor.dto.ParticipantesDTO;
import co.edu.unicauca.servidor.dto.PracticaDTO;
import co.edu.unicauca.servidor.dto.UsuarioDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.PracticaManagementService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;

import com.google.cloud.firestore.DocumentSnapshot;
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
 * @author julio
 */
@Service
public class PracticaManagementServiceImpl implements PracticaManagementService {

    @Autowired
    private FirebaseInitializer firebase;
    List<CursoDTO> response = new ArrayList<>();

    @Override
    public List<AgendamientoDTO> listarAgendamiento(int codigoPlanta) {
        List<AgendamientoDTO> response = new ArrayList<>();
        AgendamientoDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("agendamiento").whereEqualTo("cod_planta", codigoPlanta).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(AgendamientoDTO.class);
                post.setId_franja(doc.getId());
                response.add(post);
                System.out.println(response);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ArrayList<String> listarPracticasCurso(String correo) {
        ArrayList<String> cursos;
        UsuarioDTO grupo;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("usuario").whereEqualTo("correo", correo).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                grupo = doc.toObject(UsuarioDTO.class);
                grupo.setId_usuario(doc.getId());
                cursos = grupo.getCursos();
                //System.out.println(cursos);
                return cursos;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    private Map<String, Object> getDocDataParticipantes(ParticipantesDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("codGrupal", post.getCod_grupal());
        docData.put("correo", post.getCorreo());
        docData.put("estado", post.getEstado());
        docData.put("rol", post.getRol());
        return docData;
    }
    
        private Map<String, Object> getDataPractica(PracticaDTO post) {
        Map<String, Object> docData = new HashMap<>();

        //docData.put("archivos", post.);
        docData.put("cod_planta", post.getCod_planta());
        docData.put("id_curso", post.getId_practica());
        docData.put("descripcion", post.getDescripcion());
        docData.put("estado", post.getEstado());
        docData.put("fecha_entrega", post.getFecha_entrega());
        docData.put("titulo", post.getTitulo());

        return docData;
    }
      

    private CollectionReference getCollection(String Colecion) {
        return firebase.getFirestore().collection(Colecion);
    }

    @Override
    public Object crearPractica(int codigoCurso) {
        PracticaDTO Practica = null;
        Map<String, Object> docData = getDataPractica(Practica);

        CollectionReference practicas = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = practicas.document().create(docData);

        try {
            if(null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e){
            return Boolean.FALSE;
        }
    }

    @Override
    public Object modificarPractica(int codigoCurso, String idPractica) {
        PracticaDTO Practica = null;
        Map<String, Object> docData = getDataPractica(Practica);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(idPractica).set(docData);
        try {
            if(null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    @Override
    public Object eliminarPractica(int codigoCurso, String idPractica) {
         ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(idPractica).delete();
        try {
            if(null != writeResultApiFuture.get()){
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }
    private CollectionReference getCollection() {

        return firebase.getFirestore().collection("practica");
    }

}
