package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.Boolean.TRUE;

public class home_activity extends AppCompatActivity {

    Button recordbutton, configurestorage;

    //Declaramos lo necesario para firebase
    DatabaseReference mDatabase;
    DatabaseReference changereference;
    private FirebaseAuth mAuth;

    //Variables para acceso a la base de datos
    public int idanomalie = 0;
    public String checkanomalie = "";
    public String anomaliemessage = "";
    public String dateanomalie = "";

    public int dayofmonth = 0;
    public int  hourofmonth = 0;
    public int  year = 0;
    public int  month = 0;

    public int lasthourofmounth = 0;
    public String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        //Inicializamos el objeto Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Declaración de variables
        recordbutton = findViewById(R.id.button_record);
        configurestorage = findViewById(R.id.button_configurestorage);

        //Llamada de metodos
        recordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_activity.this, record_activity.class));
            }
        });

        configurestorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_activity.this, configurestorage_activity.class));
            }
        });

        //Metodos

        checkValues();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.call:
                startActivity(new Intent(home_activity.this, support_activity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(home_activity.this, account_activity.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(home_activity.this, MainActivity.class));
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }


    public void checkValues(){

        String iduser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        changereference = FirebaseDatabase.getInstance().getReference().child(iduser).child("Watertower");

        changereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String distancewater = Objects.requireNonNull(dataSnapshot.child("1").child("distancewater").getValue()).toString();
                String velocitywater = Objects.requireNonNull(dataSnapshot.child("1").child("velocitywater").getValue()).toString();

                int distance = Integer.parseInt(distancewater);
                int velocity = Integer.parseInt(velocitywater);

                if (distance > 30 && velocity == 0){
                    anomaliemessage = "El nivel del agua es bajo, y no está ingresando agua al almacenamiento";
                    getTime();
                    getAnomaliesValues();
                }else{
                    if (distance > 30){
                        anomaliemessage = "El nivel del agua es bajo";
                        getAnomaliesValues();

                    }else{
                       if(velocity == 0){
                           anomaliemessage = "No está ingresando agua al almacenamiento";
                           getTime();
                           getAnomaliesValues();

                       }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }



    public void getAnomaliesValues(){

        String iduser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        changereference = FirebaseDatabase.getInstance().getReference().child(iduser).child("Anomalies");

        changereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                while(true) {
                    idanomalie = idanomalie +1;
                    checkanomalie = String.valueOf(idanomalie);
                    try {

                        date = Objects.requireNonNull(dataSnapshot.child(checkanomalie).child("date").getValue()).toString();

                    } catch (Exception e) {
                        checkanomalie = String.valueOf(idanomalie);
                        idanomalie = 0;
                        break;
                    }
                }
                //noinspection StatementWithEmptyBody
                if (lasthourofmounth == hourofmonth){ }else {
                    lasthourofmounth = hourofmonth;
                    //noinspection StatementWithEmptyBody
                    if (date.equals(dateanomalie) == TRUE ){ }else
                    {
                        registerAnomalie();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getTime(){
        Calendar c = Calendar.getInstance();
        hourofmonth = c.get(Calendar.HOUR_OF_DAY);
        dayofmonth = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);



        dateanomalie = dayofmonth + "/" + month + "/" + year + " Hora:  " + hourofmonth;
    }

    public  void registerAnomalie(){
        String iduser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        Map<String, Object> mapanomalie = new HashMap<>();
        mapanomalie.put("description", anomaliemessage);
        mapanomalie.put("date", dateanomalie);

        mDatabase.child(iduser).child("Anomalies").child(checkanomalie).setValue(mapanomalie);

        new MaterialAlertDialogBuilder(home_activity.this)
                .setMessage(R.string.anomalie_alert)
                .setNegativeButton(getString(R.string.button_accept), null)
                .show();
    }
}
