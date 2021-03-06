/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.servidor.firebase;

import co.edu.unicauca.servidor.dto.CaidaLibreDTO;
import co.edu.unicauca.servidor.dto.LeyHookeDTO;
import co.edu.unicauca.servidor.dto.MovimientoParabolicoDTO;
import co.edu.unicauca.servidor.serviceImpl.LaboratorioManagementServiceImpl;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/**
 *
 * @author admin
 */
@Service
public class RealTime {

    DatabaseReference ref1;
    DatabaseReference ref2;
    DatabaseReference ref3;
    MovimientoParabolicoDTO MovimientoParabolico;
    LeyHookeDTO LeyHooke;
    CaidaLibreDTO CaidaLibre;
    Boolean bandera2 = false;

    @PostConstruct
    private void conectarReal() throws IOException {

        // Fetch the service account key JSON file contents
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/RealTime.json");

        // Initialize the app with a service account, granting admin privileges
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                // The database URL depends on the location of the database
                .setDatabaseUrl("https://plantas-db84a-default-rtdb.firebaseio.com")
                .build();
        FirebaseApp prueba = FirebaseApp.initializeApp(options, "secondary");

        // As an admin, the app has access to read and write all data, regardless of Security Rules
        ref1 = FirebaseDatabase.getInstance(prueba).getReference("Planta1");
        ref2 = FirebaseDatabase.getInstance(prueba).getReference("Planta2");
        ref3 = FirebaseDatabase.getInstance(prueba).getReference("Planta3");

        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LeyHooke = dataSnapshot.getValue(LeyHookeDTO.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CaidaLibre = dataSnapshot.getValue(CaidaLibreDTO.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        ref3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MovimientoParabolico = dataSnapshot.getValue(MovimientoParabolicoDTO.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public LeyHookeDTO getLeyHooke() {

        return LeyHooke;
    }

    public CaidaLibreDTO getCaidaLibre() {
        return CaidaLibre;
    }

    public MovimientoParabolicoDTO getMovimientoParabolico() {

        return MovimientoParabolico;
    }

    public Boolean iniciarLeyHooke(int peso) {
        DatabaseReference hopperRef;
        hopperRef = ref1;
        hopperRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.println(dataSnapshot.child("iniciar").getValue().toString());
                if (dataSnapshot.child("iniciar").getValue().toString().equals("false")) {
                    Map<String, Object> hopperUpdates = new HashMap<>();
                    hopperUpdates.put("peso", peso);
                    hopperUpdates.put("nRep", 1);
                    hopperUpdates.put("iniciar", true);
                    hopperRef.updateChildrenAsync(hopperUpdates);
                    bandera2 = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        return bandera2;
    }

    public Boolean iniciarCaidaLibre(int peso) {
        DatabaseReference hopperRef;
        hopperRef = ref2;
        hopperRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.println(dataSnapshot.child("iniciar").getValue().toString());
                if (dataSnapshot.child("iniciar").getValue().toString().equals("false")) {
                    Map<String, Object> hopperUpdates = new HashMap<>();
                    hopperUpdates.put("peso", peso);
                    hopperUpdates.put("iniciar", true);
                    hopperRef.updateChildrenAsync(hopperUpdates);
                    bandera2 = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        return bandera2;
    }

    public Boolean iniciarMovimientoParabolico(int angulo, int velocidad) {
        DatabaseReference hopperRef;
        hopperRef = ref3;
        hopperRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //System.out.println(dataSnapshot.child("iniciar").getValue().toString());
                if (dataSnapshot.child("iniciar").getValue().toString().equals("false")) {
                    Map<String, Object> hopperUpdates = new HashMap<>();
                    hopperUpdates.put("angulo", angulo);
                    hopperUpdates.put("velocidad", velocidad);
                    hopperUpdates.put("iniciar", true);
                    hopperRef.updateChildrenAsync(hopperUpdates);
                    bandera2 = true;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        return bandera2;
    }

    public void finalizarProceso(String planta) {
        DatabaseReference hopperRef;
        Map<String, Object> hopperUpdates = new HashMap<>();
        if (planta.equals("1")) {
            hopperUpdates.put("datos", false);
            hopperRef = ref1;
        } else if (planta.equals("2")) {
            hopperRef = ref2;
        } else {
            hopperUpdates.put("subir_datos", "OFF");
            hopperRef = ref3;
        }
        //hopperUpdates.put("finalizado", true);
        hopperRef.updateChildrenAsync(hopperUpdates);
    }

}
