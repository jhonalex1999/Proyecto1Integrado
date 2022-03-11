/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.serviceImpl;

import co.edu.unicauca.servidor.dto.FranjaHorariaDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.FranjaHorariaManagementService;
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
 * @author USUARIO
 */
@Service
public class FranjaHorariaManagementServiceImpl implements FranjaHorariaManagementService {
    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<FranjaHorariaDTO> list() {
        List<FranjaHorariaDTO> response = new ArrayList<>();
        FranjaHorariaDTO franja;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                franja = doc.toObject(FranjaHorariaDTO.class);
                franja.setId_franja_horaria(doc.getId());
                response.add(franja);
            }
            return response;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public FranjaHorariaDTO listById(String id) throws ExecutionException, InterruptedException {
        DocumentReference ref = getCollection().document(id);
        ApiFuture<DocumentSnapshot> docData = ref.get();
        DocumentSnapshot doc = docData.get();

        if(doc.exists()){
            FranjaHorariaDTO franja = doc.toObject(FranjaHorariaDTO.class);
            franja.setId_franja_horaria(doc.getId());
            return franja;
        }

        return null;
    }

    @Override
    public Boolean add(FranjaHorariaDTO franja) {
        Map<String, Object> docData = getDocData(franja);

        CollectionReference franjas = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = franjas.document().create(docData);

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
    public Boolean edit(String id, FranjaHorariaDTO franja) {
        Map<String, Object> docData = getDocData(franja);
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
        return firebase.getFirestore().collection("franja_horaria");
    }

    private Map<String, Object> getDocData(FranjaHorariaDTO franja) {
        Map<String, Object> docData = new HashMap<>();

        docData.put("id_practica", franja.getId_practica());
        docData.put("fecha", franja.getFecha());
        docData.put("hora_inicio", franja.getHora_inicio());
        docData.put("hora_fin", franja.getHora_fin());

        return docData;
    }
}
