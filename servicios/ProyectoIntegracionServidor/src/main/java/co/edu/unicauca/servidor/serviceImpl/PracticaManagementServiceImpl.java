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
import co.edu.unicauca.servidor.dto.Variables_Ley_HookeDTO;
import co.edu.unicauca.servidor.dto.Variables_Caida_LibreDTO;
import co.edu.unicauca.servidor.dto.Variables_Movimiento_ParabolicoDTO;
import co.edu.unicauca.servidor.dto.UsuarioDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.PracticaManagementService;
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

    @Override
    public List<PracticaDTO> listarPracticas() {
        List<PracticaDTO> response = new ArrayList<>();
        PracticaDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("practica").get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(PracticaDTO.class);
                post.setId_practica(doc.getId());
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean verificarAgendamiento(int codGrupal) {
        AgendamientoDTO Agendamiento = new AgendamientoDTO();
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("agendamiento").whereEqualTo("cod_grupal", codGrupal).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                Agendamiento = doc.toObject(AgendamientoDTO.class);
            }

            LocalDateTime hoy = LocalDateTime.now();
            int dia = hoy.getDayOfMonth();
            int mes = hoy.getMonthValue();
            int anio = hoy.getYear();
            int minuto = hoy.getMinute();
            int hora = hoy.getHour();

            String fecha = Agendamiento.getFecha();

            int AnioBd = Integer.parseInt(fecha.split("-")[0]);
            int MesBd = Integer.parseInt(fecha.split("-")[1]);
            int DiaBd = Integer.parseInt(fecha.split("-")[2]);

            String horaInicio = Agendamiento.getHora_inicio();
            String horaFinal = Agendamiento.getHora_fin();

            int horaInBd = Integer.parseInt(horaInicio.split(":")[0]);
            int MinutosInBd = Integer.parseInt(horaInicio.split(":")[1]);
            int horaFinBd = Integer.parseInt(horaFinal.split(":")[0]);
            int MinutosFinBd = Integer.parseInt(horaFinal.split(":")[1]);

            if (dia == DiaBd && mes == MesBd && anio == AnioBd) {
                if (hora == horaInBd) {
                    if (minuto >= MinutosInBd) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (hora == horaFinBd) {
                    if (minuto < MinutosFinBd) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (hora > horaInBd && hora < horaFinBd) {
                    return true;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public Integer agregarParticipantes(ArrayList<String> participantes, int idAgendamiento) {
        ApiFuture<WriteResult> writeResultApiFuture = null;
        int codGrupal = codGrupal();
        boolean existen = BuscarParticipantes(participantes);
        if (existen == true) {
            for (int i = 0; i < participantes.size(); i++) {
                ParticipantesDTO post = new ParticipantesDTO();

                post.setCod_grupal(codGrupal);
                post.setCorreo(participantes.get(i));
                post.setEstado(0);
                if (i == 0) {
                    post.setRol("Lider");
                } else {
                    post.setRol("Observador");
                }
                Map<String, Object> docData = getDocDataParticipantes(post);
                writeResultApiFuture = getCollection("participantes").document().create(docData);
            }
            try {
                if (null != writeResultApiFuture.get()) {
                    agregarHorario(idAgendamiento, codGrupal);
                    return 1;
                }
                return -1;
            } catch (Exception e) {
                return -1;
            }
        } else {
            return 0;
        }

    }

    private Boolean agregarHorario(int idAgendamiento, int codGrupal) {
        String Agendamiento = BuscarAgendamiento(idAgendamiento);

        ApiFuture<WriteResult> writeResultApiFuture = getCollection("agendamiento").document(Agendamiento).update("cod_grupal", codGrupal, "estado_disposicion", false);
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }

    }

    private Boolean BuscarParticipantes(ArrayList<String> participantes) {
        boolean bandera = false;
        for (int i = 0; i < participantes.size(); i++) {
            ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("usuario").whereEqualTo("correo", participantes.get(i)).get();
            try {
                if (querySnapshotApiFuture.get().isEmpty()) {
                    bandera = false;
                    break;
                } else {
                    bandera = true;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(LaboratorioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(LaboratorioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bandera;
    }

    private int codGrupal() {
        ParticipantesDTO participantes;
        List<Integer> codActuales = new ArrayList<>();
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("participantes").get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                participantes = doc.toObject(ParticipantesDTO.class);
                codActuales.add(participantes.getCod_grupal());

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(UsuarioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(UsuarioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (codActuales.isEmpty()) {
            return 1;
        } else {

            int mayor = codActuales.get(0);
            // Recorrer arreglo y ver si no es así
            // (comenzar desde el 1 porque el 0 ya lo tenemos contemplado arriba)
            for (int x = 1; x < codActuales.size(); x++) {
                if (codActuales.get(x) > mayor) {
                    mayor = codActuales.get(x);
                }
            }
            int codigo = mayor + 1;

            return codigo;
        }
    }

    private String BuscarAgendamiento(int idAgendamiento) {
        String Agendamiento = "vacio";
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("agendamiento").whereEqualTo("id_agendamiento", idAgendamiento).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                Agendamiento = doc.getId();
                return Agendamiento;

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(LaboratorioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(LaboratorioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Agendamiento;
    }

    @Override
    public Boolean buscarHorario(int idAgendamiento, int codGrupal) {
        ApiFuture<QuerySnapshot> querySnapshotApiFutur = firebase.getFirestore().collection("agendamiento").whereEqualTo("id_agendamiento", idAgendamiento).whereEqualTo("cod_grupal", codGrupal).get();
        try {
            if (querySnapshotApiFutur.get().isEmpty()) {
                return false;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(LaboratorioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(LaboratorioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public Integer saberCodigoGrupo(String correo) {
        ParticipantesDTO participantes;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("participantes").whereEqualTo("correo", correo).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                participantes = doc.toObject(ParticipantesDTO.class);
                return participantes.getCod_grupal();
            }
            return 0;

        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public Object crearPractica(String codigoCurso) {
        PracticaDTO Practica = null;
        Map<String, Object> docData = getDataPractica(Practica);

        CollectionReference practicas = getCollection("practica");
        ApiFuture<WriteResult> writeResultApiFuture = practicas.document().create(docData);

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
    public Object modificarPractica(String codigoCurso, String idPractica) {
        PracticaDTO Practica = null;
        Map<String, Object> docData = getDataPractica(Practica);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("practica").document(idPractica).set(docData);
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
    public Object eliminarPractica(String codigoCurso, String idPractica) {
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("practica").document(idPractica).delete();
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
    public PracticaDTO listById(String id) throws ExecutionException, InterruptedException {
        DocumentReference ref = getCollection("practica").document(id);
        ApiFuture<DocumentSnapshot> docData = ref.get();
        DocumentSnapshot doc = docData.get();

        if (doc.exists()) {
            PracticaDTO practica = doc.toObject(PracticaDTO.class);
            practica.setId_practica(doc.getId());
            return practica;
        }

        return null;
    }

    @Override
    public Boolean add(PracticaDTO practica) {

        Map<String, Object> docData = getDataPractica(practica);

        CollectionReference practicas = getCollection("practica");
        ApiFuture<WriteResult> writeResultApiFuture = practicas.document().create(docData);

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
    public Boolean edit(String id, PracticaDTO practica) {
        Map<String, Object> docData = getDataPractica(practica);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("practica").document(id).set(docData);
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
    public Boolean delete(String id) {
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("practica").document(id).delete();
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
    public List<PracticaDTO> list() {
        List<PracticaDTO> response = new ArrayList<>();
        PracticaDTO practice;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("practica").get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                practice = doc.toObject(PracticaDTO.class);
                practice.setId_practica(doc.getId());
                response.add(practice);
            }
            return response;

        } catch (Exception a) {
            System.out.println(a);
            return null;
        }

    }

    @Override
    public List<PracticaDTO> listByIdDocente(String correo) {
        List<PracticaDTO> response = new ArrayList<>();
        PracticaDTO practica;
       
        

        Query query = firebase.getFirestore().collection("practica").whereEqualTo("id_curso", correo);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                practica = doc.toObject(PracticaDTO.class);
                practica.setId_practica(doc.getId());
                response.add(practica);
            }
            return response;

        } catch (Exception e) {
            return null;
        }
    }

    private CollectionReference getCollection(String Colecion) {
        return firebase.getFirestore().collection(Colecion);
    }

    private Map<String, Object> getDataPractica(PracticaDTO post) {
        Map<String, Object> docData = new HashMap<>();

        docData.put("archivos", post.getArchivos());
        docData.put("cod_planta", post.getCod_planta());
        docData.put("id_curso", post.getId_curso());
        docData.put("descripcion", post.getDescripcion());
        docData.put("estado", post.getEstado());
        docData.put("fecha_entrega", post.getFecha_entrega());
        docData.put("titulo", post.getTitulo());

        return docData;
    }

    private Map<String, Object> getDocDataParticipantes(ParticipantesDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("codGrupal", post.getCod_grupal());
        docData.put("correo", post.getCorreo());
        docData.put("estado", post.getEstado());
        docData.put("rol", post.getRol());
        return docData;
    }

    private Map<String, Object> getDocDataVariablesLeydeHooke(Variables_Caida_LibreDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("id_practica", post.getId_practica());
        docData.put("num_lanzamientos", post.getNum_lanzamientos());
        docData.put("rango_altura", post.getRango_altura());
        return docData;
    }

}
