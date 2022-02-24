package co.edu.unicauca.serviciousuario.impl;

import co.edu.unicauca.serviciousuario.dto.UsuarioDTO;
import co.edu.unicauca.serviciousuario.firebase.FirebaseInitializer;
import co.edu.unicauca.serviciousuario.service.UsuarioManagementService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class UsuarioManagementServiceImpl implements UsuarioManagementService {

    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<UsuarioDTO> list() {

        List<UsuarioDTO> response = new ArrayList<>();
        UsuarioDTO usuario;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                usuario = doc.toObject(UsuarioDTO.class);
                usuario.setIdUsuario(doc.getId());
                response.add(usuario);
            }
            return response;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UsuarioDTO listById(String id) throws ExecutionException, InterruptedException {
        DocumentReference ref = getCollection().document(id);
        ApiFuture<DocumentSnapshot> docData = ref.get();
        DocumentSnapshot doc = docData.get();

        if(doc.exists()){
            UsuarioDTO usuario = doc.toObject(UsuarioDTO.class);
            usuario.setIdUsuario(doc.getId());
            return usuario;
        }

        return null;
    }

    @Override
    public Boolean add(UsuarioDTO usuario) {
        Map<String, Object> docData = getDocData(usuario);

        CollectionReference usuarios = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = usuarios.document().create(docData);

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
    public Boolean edit(String id, UsuarioDTO usuario) {
        Map<String, Object> docData = getDocData(usuario);
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
        return firebase.getFirestore().collection("usuario");
    }

    private Map<String, Object> getDocData(UsuarioDTO usuario) {
        Map<String, Object> docData = new HashMap<>();

        docData.put("nombre_completo", usuario.getNombre_completo());
        docData.put("correo", usuario.getCorreo());
        docData.put("rol", usuario.getRol());
        return docData;
    }
}
