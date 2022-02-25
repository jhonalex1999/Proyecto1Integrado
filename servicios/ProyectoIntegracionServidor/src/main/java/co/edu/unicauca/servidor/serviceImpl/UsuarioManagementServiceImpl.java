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
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
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
public class UsuarioManagementServiceImpl implements UsuarioManagamentService {

    @Autowired
    private FirebaseInitializer firebase;
  
    @Override
    public Boolean ingresarUsuario(String correo, String nombre) {
        UsuarioDTO usuario = new UsuarioDTO();
        Boolean existe = buscarUsuario(correo);
        if (existe == false) {       
            String rol = "Estudiante";
            usuario.setNombre_completo(nombre);
            usuario.setRol(rol);
            usuario.setCorreo(correo);
            Map<String, Object> docData = getDocData(usuario);
            ApiFuture<WriteResult> writeResultApiFuture = getCollection("usuario").document().create(docData);
            try {
                if (null != writeResultApiFuture.get()) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            } catch (Exception e) {
                return Boolean.FALSE;
            }
        } else {
            System.out.println("Ya existe");
            return false;
        }
    }

    
    @Override
    public Boolean agregarCurso(String correo_institucional, int codigo) {
        String Agendamiento = "vacio";
        //Primero buscara el curso
        String nombre_curso = buscarCodigoCurso(codigo);
        int existe = buscarExiste(correo_institucional);
        //Si lo encontro matriculara y devolvera un TRUE, de lo contrario mandara un FALSE
        if (existe > 0) {
            if (!"".equals(nombre_curso)) {
                ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("usuario").whereEqualTo("correo",correo_institucional).get();
                try {
                    for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                        Agendamiento = doc.getId();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(LaboratorioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ExecutionException ex) {
                    Logger.getLogger(LaboratorioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                ApiFuture<WriteResult> writeResultApiFuture = getCollection("usuario").document(Agendamiento).update("correo", correo_institucional, "cursos", FieldValue.arrayUnion(nombre_curso));

                try {
                    if (null != writeResultApiFuture.get()) {
                        return Boolean.TRUE;
                    }
                    return Boolean.FALSE;
                } catch (Exception e) {
                    return Boolean.FALSE;
                }
            } else {
                System.out.println("No existe curso");
                return false;
            }
        } else {
            System.out.println("No existe estudiante");
            return false;
        }
    }

    private CollectionReference getCollection(String Colecion) {
        return firebase.getFirestore().collection(Colecion);
    }

    @Override
    public ArrayList<String> buscarCursosMatriculados(String correo) {
        ArrayList<String> cursos;
        UsuarioDTO grupo;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("usuario").whereEqualTo("correo", correo).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                grupo = doc.toObject(UsuarioDTO.class);
                cursos = grupo.getCursos();
                return cursos;
            }
           
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    //Metodo para saber el ID del usuario
    public Boolean buscarUsuario(String correo_institucional) {
        UsuarioDTO usuario;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("usuario").whereEqualTo("correo", correo_institucional).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                usuario = doc.toObject(UsuarioDTO.class);
             
                return true;
            }
            return false;
        } catch (Exception e) {
            return null;
        }
    }

    public int buscarExiste(String correo_institucional) {
        UsuarioDTO usuario;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("usuario").whereEqualTo("correo", correo_institucional).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                usuario = doc.toObject(UsuarioDTO.class);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    public String buscarCodigoCurso(int idCurso) {
        CursoDTO curso;
        String nombre_curso = "";
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("curso").whereEqualTo("codigo_matricula", idCurso).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                curso = doc.toObject(CursoDTO.class);
                curso.setNombre_curso(curso.getNombre_curso());
                nombre_curso = curso.getNombre_curso();
                return nombre_curso;
            }
            return nombre_curso;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, Object> getDocData(UsuarioDTO usuario) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("correo", usuario.getCorreo());
        docData.put("rol", usuario.getRol());
        docData.put("nombre_completo", usuario.getNombre_completo());
        
        return docData;
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
