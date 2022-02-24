/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.serviceImpl;

import co.edu.unicauca.servidor.dto.CaidaLibreDTO;
import co.edu.unicauca.servidor.dto.CursoDTO;
import co.edu.unicauca.servidor.dto.LeyHookeDTO;
import co.edu.unicauca.servidor.dto.MovimientoParabolicoDTO;
import co.edu.unicauca.servidor.dto.ParticipantesDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.service.LaboratorioManagementService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class LaboratorioManagementServiceImpl implements LaboratorioManagementService{
      
    @Autowired
    private FirebaseInitializer firebase;

    @Override
    public List<LeyHookeDTO> listarDatosHardwareLeyDeHooke() {
        List<LeyHookeDTO> response = new ArrayList<>();
        LeyHookeDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("laboratorio_ley_hooke").get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(LeyHookeDTO.class);
                post.setId_practica(doc.getId());
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MovimientoParabolicoDTO> listarDatosHardwareMovimientoParabolico() {
         List<MovimientoParabolicoDTO> response = new ArrayList<>();
        MovimientoParabolicoDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("laboratorio_movimiento_parabolico").get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(MovimientoParabolicoDTO.class);
                post.setId_practica(doc.getId());
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CaidaLibreDTO> listarDatosHardwareCaidaLibre() {
        List<CaidaLibreDTO> response = new ArrayList<>();
        CaidaLibreDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("laboratorio_caida_libre").get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(CaidaLibreDTO.class);
                post.setId_practica(doc.getId());
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean crearPdf() {
     List<CaidaLibreDTO> response = new ArrayList<>();
     CaidaLibreDTO post;

        Document documento = new Document();

        String ruta = System.getProperty("user.home");
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Downloads/Datos_Practica_Caida_Libre.pdf"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PracticaManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PracticaManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        documento.open();

        PdfPTable tabla = new PdfPTable(5);
        tabla.addCell("altura");
        tabla.addCell("codigo_planta");
        tabla.addCell("foto");
        tabla.addCell("id_practica");
        tabla.addCell("tiempo");

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("laboratorio_caida_libre").get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(CaidaLibreDTO.class);
                post.setId_practica(doc.getId());
                response.add(post);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(PracticaManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(PracticaManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < response.size(); i++) {
            tabla.addCell(String.valueOf(response.get(i).getAltura()));
            tabla.addCell(String.valueOf(response.get(i).getCodigo_planta()));
            tabla.addCell(String.valueOf(response.get(i).getFoto()));
            tabla.addCell(String.valueOf(response.get(i).getId_practica()));
            tabla.addCell(String.valueOf(response.get(i).getTiempo()));

        }
        try {
            documento.add(tabla);
        } catch (DocumentException ex) {
            Logger.getLogger(PracticaManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        documento.close();

        return Boolean.TRUE;
   
    }



    @Override
    public Boolean finalizarPractica(int codGrupal) {
        return false;
    }
    private List<String> buscarGrupo(int codGrupal) {
       return null;
    }

    @Override
    public Boolean buscarCompletitudEstudiantes(int codGrupal) {
        ParticipantesDTO grupo;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("participantes").whereEqualTo("cod_grupal", codGrupal).get();
       
        int contados = 0;
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                grupo = doc.toObject(ParticipantesDTO.class);
                grupo.setId_participante(doc.getId());
                String id = grupo.getId_participante();
                if (grupo.getEstado() == 1) {
                    contados += 1;
                }
            }
            //AQUI DEBE PONERSE DEPENDIENDO DEL NUMERO POR GRUPOS
            if (contados == 3) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String buscarQuienEsLider(String correo) {
        ParticipantesDTO participantes;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("participantes").whereEqualTo("correo", correo).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                participantes = doc.toObject(ParticipantesDTO.class);
                return participantes.getRol();
            }
            return "";

        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public Boolean reportarError(int idLaboratorio, String descripcion) {
      Map<String, Object> docData = new HashMap<>();
        docData.put("id_laboratorio", idLaboratorio);
        docData.put("problema", descripcion);
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("problema").document().create(docData);
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
}
