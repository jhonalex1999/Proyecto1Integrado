/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.serviceImpl;

import co.edu.unicauca.servidor.dto.CursoDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.CursoManagementService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author Modulo de Docentes
 */
@Service
public class CursoManagementServiceImpl implements CursoManagementService {

    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<CursoDTO> list() {
        List<CursoDTO> response = new ArrayList<>();
        CursoDTO curso;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                curso = doc.toObject(CursoDTO.class);
                curso.setId_curso(doc.getId());
                response.add(curso);
            }
            return response;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public CursoDTO listById(String id) throws ExecutionException, InterruptedException {
        DocumentReference ref = getCollection().document(id);
        ApiFuture<DocumentSnapshot> docData = ref.get();
        DocumentSnapshot doc = docData.get();

        if(doc.exists()){
            CursoDTO curso = doc.toObject(CursoDTO.class);
            curso.setId_curso(doc.getId());
            return curso;
        }

        return null;
    }

    @Override
    public List<CursoDTO> listByIdDocente(String id) {
        List<CursoDTO> response = new ArrayList<>();
        CursoDTO curso;

        Query query =  firebase.getFirestore().collection("curso").whereEqualTo("id_docente", id);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                curso = doc.toObject(CursoDTO.class);
                curso.setId_curso(doc.getId());
                response.add(curso);
            }
            return response;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean add(CursoDTO curso) {

        Map<String, Object> docData = getDocData(curso);

        CollectionReference cursos = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = cursos.document().create(docData);

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
    public Boolean edit(String id, CursoDTO curso) {
        Map<String, Object> docData = getDocData(curso);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).set(docData);
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
    public Boolean delete(String id) {
        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document(id).delete();
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
        return firebase.getFirestore().collection("curso");
    }

    private Map<String, Object> getDocData(CursoDTO curso) {
        Map<String, Object> docData = new HashMap<>();

        docData.put("nombre_curso", curso.getNombre_curso());
        docData.put("id_docente", curso.getId_docente());
        docData.put("codigo_matricula", curso.getCodigo_matricula());
        docData.put("fecha_creacion", curso.getFecha_creacion());
        docData.put("fecha_eliminacion", curso.getFecha_eliminacion());
        docData.put("tamanio_grupo", curso.getTamanio_grupo());

        return docData;
    }
}
