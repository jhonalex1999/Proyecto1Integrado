/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.impl;

import co.edu.unicauca.servidor.dto.AgendamientoDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.LaboratorioManagementService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author admin
 */
public class LaboratorioManagementServiceImpl implements LaboratorioManagementService{
        @Autowired
    private FirebaseInitializer firebase;
     @Override
    public List<AgendamientoDTO> listarAgendamiento(int codigoPlanta) {
        List<AgendamientoDTO> response = new ArrayList<>();
        AgendamientoDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("AGENDAMIENTO").whereEqualTo("codigoPlanta", codigoPlanta).get();

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
       private CollectionReference getCollection(String Colecion) {
        return firebase.getFirestore().collection(Colecion);
    }
}
