/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.serviceImpl;

import co.edu.unicauca.servidor.dto.CursoDTO;
import co.edu.unicauca.servidor.dto.UsuarioDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.UsuarioManagamentService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author julio
 */
@Service
public class UsuarioManagementServiceImpl implements UsuarioManagamentService {

    @Autowired
    private FirebaseInitializer firebase;
    List<CursoDTO> response = new ArrayList<>();

    @Override
    public Object crearCurso(String correo) {
        CursoDTO Curso = null;
        Map<String, Object> docData = getDataCurso(Curso);

        CollectionReference cursos = getCollection("curso");
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
    public Object modificarCurso(String correo, int codigoCurso) {
        String codCurso= Integer.toString(codigoCurso);
        CursoDTO Curso = null;
        Map<String, Object> docData = getDataCurso(Curso);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("curso").document(codCurso).set(docData);
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
    public Object eliminarCurso(String correo, int codigoCurso) {
        String codCurso= Integer.toString(codigoCurso);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("curso").document(codCurso).delete();
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
    public Object buscarCursosImpartidos(String correo) {
        ArrayList<String> cursos;
        UsuarioDTO docente;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("usuario").whereEqualTo("correo", correo).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                docente = doc.toObject(UsuarioDTO.class);
                cursos = docente.getCursos();
                return cursos;
            }
           
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private Map<String, Object> getDataCurso(CursoDTO curso) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("nombre_curso", curso.getNombre_curso());
        docData.put("codigo_matricula", curso.getCodigo_matricula());
        docData.put("fecha_creacion", curso.getFecha_creacion());
        docData.put("fecha_eliminacion", curso.getFecha_eliminacion());
         docData.put("id_docente", curso.getId_docente());
         docData.put("tamanio_grupo", curso.getTamanio_grupo());
        return docData;
    }

    private CollectionReference getCollection(String colecion) {
         return firebase.getFirestore().collection(colecion);
    }

}
