/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.serviceImpl;

import co.edu.unicauca.servidor.dto.AgendamientoDTO;
import co.edu.unicauca.servidor.dto.CaidaLibreDTO;
import co.edu.unicauca.servidor.dto.CursoDTO;
import co.edu.unicauca.servidor.dto.LeyHookeDTO;
import co.edu.unicauca.servidor.dto.MovimientoParabolicoDTO;
import co.edu.unicauca.servidor.dto.ParticipantesDTO;
import co.edu.unicauca.servidor.dto.UsuarioDTO;
import co.edu.unicauca.servidor.dto.Variables_Caida_LibreDTO;
import co.edu.unicauca.servidor.dto.Variables_Ley_HookeDTO;
import co.edu.unicauca.servidor.dto.Variables_Movimiento_ParabolicoDTO;
import co.edu.unicauca.servidor.firebase.FirebaseInitializer;
import co.edu.unicauca.servidor.firebase.RealTime;
import co.edu.unicauca.servidor.service.LaboratorioManagementService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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
 *
 */
@Service
public class LaboratorioManagementServiceImpl implements LaboratorioManagementService {

    @Autowired
    private FirebaseInitializer firebase;

    @Autowired
    private RealTime firebase2;

    //Listas de pesos
    ArrayList<Integer> listaLeyHooke = new ArrayList();
    ArrayList<Integer> listaCaidaLibre = new ArrayList();
    ArrayList<Integer> listaMovimientoParabolicoAngulo = new ArrayList();
    ArrayList<Integer> listaMovimientoParabolicoVelocidad = new ArrayList();

    @Override
    public Boolean descargarDatos(int codigo_planta) {
        try {
            //Datos
            ParticipantesDTO participantes;
            LeyHookeDTO labLeyHooke;
            CaidaLibreDTO labCaidaLibre;
            MovimientoParabolicoDTO labMovimientoParabolico;
            int codigo_grupo = 0;
            String lider = "";
            String nombre_planta = "";
            ArrayList<String> nombres_estudiantes = new ArrayList<>();
            //Array de cada planta
            //Planta 1
            ArrayList<Double> valores_elongaciones = new ArrayList<>();
            ArrayList<Double> valores_pesos = new ArrayList<>();
            //Planta 2
            ArrayList<Double> valores_errores = new ArrayList<>();
            ArrayList<Double> valores_tiempo = new ArrayList<>();
            //Planta 3
            ArrayList<Double> valores_x = new ArrayList<>();
            ArrayList<Double> valores_y = new ArrayList<>();
            //Carpeta
            String ruta = System.getProperty("user.home");
            String currentPath = Paths.get("").toAbsolutePath().normalize().toString();
            //String downloadFolder = ruta + "/Downloads/";
            String downloadPath = ruta + "/Downloads/";
            File newFolder = new File(downloadPath);
            boolean dirCreated = newFolder.mkdir();

            if (codigo_planta == 1) {
                nombre_planta = "Ley de Hooke";
            } else if (codigo_planta == 2) {
                nombre_planta = "Caida Libre";
            } else {
                nombre_planta = "Movimiento Parabolico";
            }

            // get current time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-dd_HH-mm-ss");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));
            String fileName = "Entrega de datos_Planta_" + codigo_planta + "_" + nombre_planta + "_" + dtf.format(now) + ".csv";

            // Whatever the file path is.
            File statText = new File(downloadPath + "/" + fileName);
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);

            //Sacamos los datos del grupo
            try {
                //Datos necesarios
                ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("participantes").get();
                for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                    participantes = doc.toObject(ParticipantesDTO.class);
                    nombres_estudiantes.add(participantes.getCorreo());
                    codigo_grupo = participantes.getCodGrupal();
                    if (participantes.getRol().equals("Lider")) {
                        lider = participantes.getCorreo();
                    }
                }
                //Datos de cada planta
                if (codigo_planta == 1) {
                    ApiFuture<QuerySnapshot> querySnapshotApiFuture2 = firebase.getFirestore().collection("laboratorio_ley_hooke").get();
                    for (DocumentSnapshot doc : querySnapshotApiFuture2.get().getDocuments()) {
                        labLeyHooke = doc.toObject(LeyHookeDTO.class);
                        Collection<Double> valores = labLeyHooke.getElongaciones().values();
                        ArrayList<Double> elongaciones = new ArrayList<>(valores);
                        for (int i = 0; i < elongaciones.size(); i++) {
                            valores_elongaciones.add(elongaciones.get(i));
                        }
                        Collection<Double> peso = labLeyHooke.getPesos().values();
                        ArrayList<Double> pesos = new ArrayList<>(peso);
                        for (int j = 0; j < pesos.size(); j++) {
                            valores_pesos.add(pesos.get(j));
                        }
                    }
                } else if (codigo_planta == 2) {
                    ApiFuture<QuerySnapshot> querySnapshotApiFuture2 = firebase.getFirestore().collection("laboratorio_caida_libre").get();
                    for (DocumentSnapshot doc : querySnapshotApiFuture2.get().getDocuments()) {
                        labCaidaLibre = doc.toObject(CaidaLibreDTO.class);
                        Collection<Double> tiempo = labCaidaLibre.getTiempo().values();
                        ArrayList<Double> tiempos = new ArrayList<>(tiempo);
                        for (int i = 0; i < labCaidaLibre.getErrores().size(); i++) {
                            valores_errores.add(tiempos.get(i));
                        }
                        Collection<Double> altura = labCaidaLibre.getTiempo().values();
                        ArrayList<Double> alturas = new ArrayList<>(altura);
                        for (int j = 0; j < labCaidaLibre.getTiempo().size(); j++) {
                            valores_tiempo.add(alturas.get(j));
                        }
                    }
                } else if (codigo_planta == 3) {
                    ApiFuture<QuerySnapshot> querySnapshotApiFuture2 = firebase.getFirestore().collection("laboratorio_movimiento_parabolico").get();
                    for (DocumentSnapshot doc : querySnapshotApiFuture2.get().getDocuments()) {
                        labMovimientoParabolico = doc.toObject(MovimientoParabolicoDTO.class);
                        for (int i = 0; i < labMovimientoParabolico.getDatos_x().size(); i++) {
                            valores_x.add(Double.parseDouble(labMovimientoParabolico.getDatos_x().get(i)));
                        }
                        for (int j = 0; j < labMovimientoParabolico.getDatos_y().size(); j++) {
                            valores_y.add(Double.parseDouble(labMovimientoParabolico.getDatos_y().get(j)));
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(UsuarioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(LaboratorioManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(codigo_grupo);
            w.write("------------------------------------------------------ \n");
            w.write("Codigo Planta: " + codigo_planta + "\n");
            w.write("Nombre Planta: " + nombre_planta + "\n");
            w.write("Codigo Grupo: " + codigo_grupo + "\n");
            w.write("Correos estudiantes: " + nombres_estudiantes + "\n");
            w.write("Lider y simulador del equipo: " + lider + "\n");
            if (codigo_planta == 1) {
                w.write("-------------------------------------------------- \n");
                for (int k = 0; k < listaLeyHooke.size(); k++) {
                    w.write("Peso: " + listaLeyHooke.get(k) + "\n");
                }
                w.write("----------------------Valores X--------------------- \n");
                for (int i = 0; i < valores_elongaciones.size(); i++) {
                    w.write("Elongacion: " + valores_elongaciones.get(i) + "\n");
                }
                w.write("----------------------Valores Y--------------------- \n");
                for (int j = 0; j < valores_pesos.size(); j++) {
                    w.write("Peso: " + valores_pesos.get(j) + "\n");
                }
                listaLeyHooke.clear();
            } else if (codigo_planta == 2) {
                w.write("-------------------------------------------------- \n");
                for (int k = 0; k < listaCaidaLibre.size(); k++) {
                    w.write("Peso: " + listaCaidaLibre.get(k) + "\n");
                    w.write("----------------------Valores X--------------------- \n");
                    for (int i = 0; i < valores_errores.size(); i++) {
                        w.write("Error: " + valores_errores.get(i) + "\n");
                        w.write("----------------------Valores Y--------------------- \n");
                        for (int j = 0; j < valores_tiempo.size(); j++) {
                            w.write("Tiempo: " + valores_tiempo.get(j) + "\n");
                        }
                    }
                }
                listaCaidaLibre.clear();
            } else if (codigo_planta == 3) {
                w.write("-------------------------------------------------- \n");
                for (int k = 0; k < listaMovimientoParabolicoAngulo.size(); k++) {
                    w.write("Angulo: " + listaMovimientoParabolicoAngulo.get(k) + " - Velocidad: " + listaMovimientoParabolicoVelocidad.get(k) + "\n");
                    w.write("----------------------Valores X--------------------- \n");
                    for (int i = 0; i < valores_x.size(); i++) {
                        w.write("X: " + valores_x.get(i) + "\n");
                    }
                    w.write("----------------------Valores Y--------------------- \n");
                    for (int j = 0; j < valores_y.size(); j++) {
                        w.write("Y: " + valores_y.get(j) + "\n");
                    }
                }
                listaMovimientoParabolicoAngulo.clear();
                listaMovimientoParabolicoVelocidad.clear();
            }
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file " + e);
            return false;
        }
        return true;
    }

    @Override
    public List<Variables_Ley_HookeDTO> listarDatosHardwareLeyDeHooke() {
        List<Variables_Ley_HookeDTO> response = new ArrayList<>();
        Variables_Ley_HookeDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("laboratorio_ley_hooke").get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(Variables_Ley_HookeDTO.class);
                post.setId(doc.getId());
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Variables_Movimiento_ParabolicoDTO> listarDatosHardwareMovimientoParabolico() {
        List<Variables_Movimiento_ParabolicoDTO> response = new ArrayList<>();
        Variables_Movimiento_ParabolicoDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("laboratorio_movimiento_parabolico").get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(Variables_Movimiento_ParabolicoDTO.class);
                post.setId(doc.getId());
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Variables_Caida_LibreDTO> listarDatosHardwareCaidaLibre() {
        List<Variables_Caida_LibreDTO> response = new ArrayList<>();
        Variables_Caida_LibreDTO post;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection("laboratorio_caida_libre").get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                post = doc.toObject(Variables_Caida_LibreDTO.class);
                post.setId(doc.getId());
                response.add(post);
            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean insertarProblema(int idLaboratorio, String problema) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("id_practica", idLaboratorio);
        docData.put("descripcion", problema);
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

    @Override
    public Boolean finalizarPractica(int codGrupal) {
        List<String> response = new ArrayList<>();
        List<String> agendamiento = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        response = buscarGrupo(codGrupal);
        agendamiento = buscarAgendamiento(codGrupal);
        int cod_planta = saberPlanta(codGrupal);
        Boolean bandera = false;
        Map<String, Object> docData = new HashMap<>();
        nombres = obtenerNombresEstudiantes(codGrupal);
        docData.put("cod_grupal", codGrupal);
        docData.put("historial_usuario", nombres);
        ApiFuture<WriteResult> writeResultApiFutureHistorial = getCollection("historial").document().create(docData);
        for (int i = 0; i < agendamiento.size(); i++) {
            ApiFuture<WriteResult> writeResultApiFutureNombres = getCollection("historial").document(agendamiento.get(i)).update("codGrupal", codGrupal, "historial_usuarios", FieldValue.arrayUnion(nombres));
        }      
        for (int i = 0; i < agendamiento.size(); i++) {
            ApiFuture<WriteResult> writeResultApiFutureAgendamiento = getCollection("agendamiento").document(agendamiento.get(i)).delete();
        }
        for (int id = 0; id < response.size(); id++) {
            ApiFuture<WriteResult> writeResultApiFuture = getCollection("participantes").document(response.get(id)).delete();
            try {
                if (null != writeResultApiFutureHistorial.get()) {
                    bandera = true;
                } else {
                    bandera = false;
                }
            } catch (Exception e) {
                bandera = false;
            }
        }
        firebase2.finalizarProceso(String.valueOf(cod_planta));
        return bandera;
    }

    public int saberPlanta(int codGrupal) {
        AgendamientoDTO participantes;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("agendamiento").whereEqualTo("cod_grupal", codGrupal).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                participantes = doc.toObject(AgendamientoDTO.class);
                participantes.setId(doc.getId());
                System.out.println(participantes.getCod_planta());
                return participantes.getCod_planta();
            }
            //return cursos;
        } catch (Exception e) {
            return 0;
        }
        return 0;
    }

    public ArrayList<String> obtenerNombresEstudiantes(int codGrupal) {
        ArrayList<String> nombres = new ArrayList();
        ParticipantesDTO participantes;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("participantes").whereEqualTo("cod_grupal", codGrupal).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                participantes = doc.toObject(ParticipantesDTO.class
                );
                participantes.setId_participante(doc.getId());
                nombres.add(participantes.getCorreo());
                //System.out.println(cursos);
            }
            return nombres;
            //return cursos;
        } catch (Exception e) {
            return null;
        }
    }

    private List<String> buscarAgendamiento(int codGrupal) {
        List<String> response = new ArrayList<>();
        ParticipantesDTO participantes;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("agendamiento").whereEqualTo("cod_grupal", codGrupal).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                participantes = doc.toObject(ParticipantesDTO.class
                );
                participantes.setId_participante(doc.getId());
                response.add(participantes.getId_participante());

            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    private List<String> buscarGrupo(int codGrupal) {
        List<String> response = new ArrayList<>();
        ParticipantesDTO participantes;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("participantes").whereEqualTo("cod_grupal", codGrupal).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                participantes = doc.toObject(ParticipantesDTO.class
                );
                participantes.setId_participante(doc.getId());
                response.add(participantes.getId_participante());

            }
            return response;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean buscarCompletitudEstudiantes(int codGrupal) {
        UsuarioDTO usuario;
        ParticipantesDTO participantes;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("participantes").whereEqualTo("codGrupal", codGrupal).get();

        int contados = 0;
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                participantes = doc.toObject(ParticipantesDTO.class);
                participantes.setId_participante(doc.getId());
                String id = participantes.getId_participante();
                String correo = participantes.getCorreo();
                ApiFuture<QuerySnapshot> querySnapshotApiFutureUsuario = firebase.getFirestore().collection("usuario").whereEqualTo("correo", correo).get();
                try {
                    for (DocumentSnapshot doc2 : querySnapshotApiFutureUsuario.get().getDocuments()) {
                        usuario = doc2.toObject(UsuarioDTO.class);
                        usuario.setId(doc2.getId());
                        if (usuario.getEstado() == 1) {
                            contados += 1;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
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
                participantes = doc.toObject(ParticipantesDTO.class
                );
                return participantes.getRol();
            }
            return "";

        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public Integer saberCodigoGrupo(String correo) {
        ParticipantesDTO participantes;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("participantes").whereEqualTo("correo", correo).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                participantes = doc.toObject(ParticipantesDTO.class
                );
                return participantes.getCodGrupal();
            }
            return 0;

        } catch (Exception e) {
            return -1;
        }
    }

    private CollectionReference getCollection(String Colecion) {
        return firebase.getFirestore().collection(Colecion);
    }

    @Override
    public ArrayList<Integer> listar_Altura_CL(int codigo_planta) {
        ArrayList<Integer> rangos_altura;
        Variables_Caida_LibreDTO laboratorio_caida_libre;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("variables_caida_libre").whereEqualTo("id_practica", codigo_planta).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                laboratorio_caida_libre = doc.toObject(Variables_Caida_LibreDTO.class);
                laboratorio_caida_libre.setId(doc.getId());
                rangos_altura = laboratorio_caida_libre.getRango_altura();
                return rangos_altura;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Integer> listar_Pesos_LH(int codigo_planta) {
        ArrayList<Integer> rangos_pesos;
        Variables_Ley_HookeDTO laboratorio_ley_hooke;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("variables_ley_hooke").whereEqualTo("id_practica", codigo_planta).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                laboratorio_ley_hooke = doc.toObject(Variables_Ley_HookeDTO.class);
                laboratorio_ley_hooke.setId(doc.getId());
                rangos_pesos = laboratorio_ley_hooke.getRango_pesos();
                return rangos_pesos;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Integer> listar_Angulo_MP(int codigo_planta) {
        ArrayList<Integer> rangos_angulo;
        Variables_Movimiento_ParabolicoDTO laboratorio_movimiento_parabolico;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("variables_movimiento_parabolico").whereEqualTo("id_practica", codigo_planta).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                laboratorio_movimiento_parabolico = doc.toObject(Variables_Movimiento_ParabolicoDTO.class);
                laboratorio_movimiento_parabolico.setId(doc.getId());
                rangos_angulo = laboratorio_movimiento_parabolico.getRango_angulo();
                return rangos_angulo;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Integer> listar_Velocidad_MP(int codigo_planta) {
        ArrayList<Integer> rangos_velocidad;
        Variables_Movimiento_ParabolicoDTO laboratorio_movimiento_parabolico;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("variables_movimiento_parabolico").whereEqualTo("id_practica", codigo_planta).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                laboratorio_movimiento_parabolico = doc.toObject(Variables_Movimiento_ParabolicoDTO.class);
                laboratorio_movimiento_parabolico.setId(doc.getId());
                rangos_velocidad = laboratorio_movimiento_parabolico.getRango_velocidad();
                return rangos_velocidad;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public Boolean GuardarCaidaLibre() {
        CaidaLibreDTO objCaidaLibre = firebase2.getCaidaLibre();
        if (pasarCaidaLibre(objCaidaLibre)) {
            return true;
        }
        return false;
    }

    private boolean pasarCaidaLibre(CaidaLibreDTO objCaidaLibre) {
        Map<String, Object> docData = new HashMap<>();
        Map<String, Double> altura = new HashMap<>();
        objCaidaLibre.setCodigo_planta(2);
        altura.put("h1", 10.0);
        altura.put("h2", 20.0);
        altura.put("h3", 30.0);
        altura.put("h4", 40.0);
        altura.put("h5", 50.0);
        objCaidaLibre.setPesos_utilizados(listaCaidaLibre);
        docData.put("codigo_planta", objCaidaLibre.getCodigo_planta());
        docData.put("errores", objCaidaLibre.getErrores());
        docData.put("gravedadN", objCaidaLibre.getGravedadN());
        docData.put("nRep", objCaidaLibre.getNRep());
        docData.put("tiempo", objCaidaLibre.getTiempo());
        docData.put("altura", altura);
        docData.put("pesos_utilizados", objCaidaLibre.getPesos_utilizados());
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("laboratorio_caida_libre").document().create(docData);

        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public Boolean GuardarLeyHooke() {

        LeyHookeDTO objHooke = firebase2.getLeyHooke();
        if (pasarDatosLeyHooke(objHooke)) {
            return true;
        }
        return false;
    }

    private boolean pasarDatosLeyHooke(LeyHookeDTO objHooke) {
        Map<String, Object> docData = new HashMap<>();
        objHooke.setCodigo_planta(1);
        objHooke.setPesos_utilizados(listaLeyHooke);
        docData.put("codigo_planta", objHooke.getCodigo_planta());
        docData.put("elongaciones", objHooke.getElongaciones());
        docData.put("nRep", objHooke.getNRep());
        docData.put("pesos", objHooke.getPesos());
        docData.put("pesos_utilizados", objHooke.getPesos_utilizados());
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("laboratorio_ley_hooke").document().create(docData);
        try {
            if (null != writeResultApiFuture.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }

    public Boolean GuardarMovimientoParabolico() {
        MovimientoParabolicoDTO objMovimientoParabolico = firebase2.getMovimientoParabolico();
        if (pasarDatosMovimientoParabolico(objMovimientoParabolico)) {
            return true;
        }
        return false;
    }

    private boolean pasarDatosMovimientoParabolico(MovimientoParabolicoDTO objMovimientoParabolico) {
        Map<String, Object> docData = new HashMap<>();
        objMovimientoParabolico.setCodigo_planta(3);
        objMovimientoParabolico.setAngulos_utilizados(listaMovimientoParabolicoAngulo);
        objMovimientoParabolico.setVelocidades_utilizados(listaMovimientoParabolicoVelocidad);
        docData.put("codigo_planta", objMovimientoParabolico.getCodigo_planta());
        docData.put("datos_x", objMovimientoParabolico.getDatos_x());
        docData.put("datos_y", objMovimientoParabolico.getDatos_y());
        docData.put("nRep", objMovimientoParabolico.getNRep());
        docData.put("tiempo", objMovimientoParabolico.getTiempo());
        docData.put("velocidad", objMovimientoParabolico.getVelocidad());
        docData.put("url", objMovimientoParabolico.getUrl_imagen());
        docData.put("angulos_utilizados", objMovimientoParabolico.getAngulos_utilizados());
        docData.put("velocidades_utilizados", objMovimientoParabolico.getVelocidades_utilizados());
        ApiFuture<WriteResult> writeResultApiFuture = getCollection("laboratorio_movimiento_parabolico").document().create(docData);

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
    public ArrayList<Double> retornarTiempo(int codigo_planta) {
        CaidaLibreDTO caida_libre;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("laboratorio_caida_libre").whereEqualTo("codigo_planta", codigo_planta).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                caida_libre = doc.toObject(CaidaLibreDTO.class);
                caida_libre.setId(doc.getId());
                Collection<Double> valores = caida_libre.getTiempo().values();
                ArrayList<Double> tiempo = new ArrayList<>(valores);
                //datos_tiempo =;
                //System.out.println(cursos);
                return tiempo;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Double> retornarAltura(int codigo_planta) {
        CaidaLibreDTO caida_libre;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("laboratorio_caida_libre").whereEqualTo("codigo_planta", codigo_planta).get();

        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                caida_libre = doc.toObject(CaidaLibreDTO.class);
                caida_libre.setId(doc.getId());
                Collection<Double> valores = caida_libre.getAltura().values();
                ArrayList<Double> altura = new ArrayList<>(valores);
                return altura;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Double> retornarElongaciones(int codigo_planta) {
        LeyHookeDTO ley_hooke;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("laboratorio_ley_hooke").whereEqualTo("codigo_planta", codigo_planta).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                ley_hooke = doc.toObject(LeyHookeDTO.class);
                ley_hooke.setId(doc.getId());
                Collection<Double> valores = ley_hooke.getElongaciones().values();
                ArrayList<Double> elongaciones = new ArrayList<>(valores);
                return elongaciones;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Double> retornarPesos(int codigo_planta) {
        LeyHookeDTO ley_hooke;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("laboratorio_ley_hooke").whereEqualTo("codigo_planta", codigo_planta).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                ley_hooke = doc.toObject(LeyHookeDTO.class);
                ley_hooke.setId(doc.getId());
                Collection<Double> valores = ley_hooke.getPesos().values();
                ArrayList<Double> pesos = new ArrayList<>(valores);
                return pesos;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Double> retornarX(int codigo_planta) {
        MovimientoParabolicoDTO movimiento_parabolico;
        ArrayList<Double> x = new ArrayList<>();;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("laboratorio_movimiento_parabolico").whereEqualTo("codigo_planta", codigo_planta).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                movimiento_parabolico = doc.toObject(MovimientoParabolicoDTO.class);
                movimiento_parabolico.setId(doc.getId());
                for (int i = 0; i < movimiento_parabolico.getDatos_x().size(); i++) {
                    x.add(Double.parseDouble(movimiento_parabolico.getDatos_x().get(i)));
                }

                return x;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<Double> retornarY(int codigo_planta) {
        MovimientoParabolicoDTO movimiento_parabolico;
        ArrayList<Double> y = new ArrayList<>();;
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = firebase.getFirestore().collection("laboratorio_movimiento_parabolico").whereEqualTo("codigo_planta", codigo_planta).get();
        try {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                movimiento_parabolico = doc.toObject(MovimientoParabolicoDTO.class);
                movimiento_parabolico.setId(doc.getId());
                for (int i = 0; i < movimiento_parabolico.getDatos_y().size(); i++) {
                    y.add(Double.parseDouble(movimiento_parabolico.getDatos_y().get(i)));
                }

                return y;
            }
            //return cursos;
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public Boolean iniciarLeyHooke(int peso) {
        int pesobd = 0;
        if (peso == 100) {
            pesobd = 1;
        } else if (peso == 150) {
            pesobd = 2;
        } else if (peso == 200) {
            pesobd = 3;
        } else if (peso == 250) {
            pesobd = 4;
        } else if (peso == 300) {
            pesobd = 5;
        }
        listaLeyHooke.add(peso);
        Boolean bandera = firebase2.iniciarLeyHooke(pesobd);
        return bandera;
    }

    @Override
    public Boolean iniciarCaidaLibre(int peso) {
        int pesobd = 0;
        if (peso == 50) {
            pesobd = 1;
        } else if (peso == 100) {
            pesobd = 2;
        } else if (peso == 150) {
            pesobd = 3;
        } else if (peso == 200) {
            pesobd = 4;
        } else if (peso == 250) {
            pesobd = 5;
        }
        listaCaidaLibre.add(peso);
        Boolean bandera = firebase2.iniciarCaidaLibre(pesobd);
        return bandera;
    }

    @Override
    public Boolean iniciarMovimientoParabolico(int angulo, int velocidad) {
        int angulobd = 0;
        int velocidadbd = 0;
        if (angulo == 50) {
            angulobd = 1;
        } else if (angulo == 100) {
            angulobd = 2;
        } else if (angulo == 150) {
            angulobd = 3;
        } else if (angulo == 200) {
            angulobd = 4;
        } else if (angulo == 250) {
            angulobd = 5;
        }

        if (velocidad == 50) {
            velocidadbd = 1;
        } else if (velocidad == 100) {
            velocidadbd = 2;
        } else if (velocidad == 150) {
            velocidadbd = 3;
        } else if (velocidad == 200) {
            velocidadbd = 4;
        } else if (velocidad == 250) {
            velocidadbd = 5;
        }
        listaMovimientoParabolicoAngulo.add(angulo);
        listaMovimientoParabolicoVelocidad.add(velocidad);
        Boolean bandera = firebase2.iniciarMovimientoParabolico(angulobd, velocidadbd);
        return bandera;
    }

    @Override
    public Boolean finalizarProceso(String planta) {
        if (planta.equals("1")) {
            GuardarLeyHooke();
        } else if (planta.equals("2")) {
            GuardarCaidaLibre();
        } else {
            GuardarMovimientoParabolico();
        }
        return true;
    }

}
