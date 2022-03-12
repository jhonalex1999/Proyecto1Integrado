package co.edu.unicauca.servidor.serviceImpl;

import co.edu.unicauca.servidor.dto.CursoDTO;
import co.edu.unicauca.servidor.dto.ProblemaDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.ProblemaManagementService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Modulo de Docentes
 */
@Service
public class ProblemaManagementServiceImpl implements ProblemaManagementService {

    @Autowired
    private FirebaseInitializer firebase;


    @Override
    public List<ProblemaDTO> list() {
        List<ProblemaDTO> response = new ArrayList<>();
        ProblemaDTO problema;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                problema = doc.toObject(ProblemaDTO.class);
                problema.setId_problema(doc.getId());
                response.add(problema);
            }
            return response;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean add(ProblemaDTO problema) {
        Map<String, Object> docData = getDocData(problema);

        CollectionReference problemas = getCollection();
        ApiFuture<WriteResult> writeResultApiFuture = problemas.document().create(docData);

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
    public Boolean edit(String id, ProblemaDTO problema) {
        Map<String, Object> docData = getDocData(problema);
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
        return firebase.getFirestore().collection("problema");
    }

    private Map<String, Object> getDocData(ProblemaDTO problema) {
        Map<String, Object> docData = new HashMap<>();

        docData.put("id_practica", problema.getId_practica());
        docData.put("descripcion", problema.getDescripcion());

        return docData;
    }
}
