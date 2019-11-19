package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static java.lang.Boolean.TRUE;

public class record_activity extends AppCompatActivity {

    Button gobackbutton;
    TextView dateanomalie,anomalie;

    //Declaramos lo necesario para firebase
    DatabaseReference mDatabase;
    DatabaseReference changereference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_activity);

        //Inicializamos el objeto Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Declaración de variables
        gobackbutton = findViewById(R.id.button_goback);
        dateanomalie = findViewById(R.id.text_date);
        anomalie = findViewById(R.id.text_anomalie);

        //Llamada de métodos
        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(record_activity.this, home_activity.class));
            }
        });

        checkAnomalies();
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
                startActivity(new Intent(record_activity.this, support_activity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(record_activity.this, account_activity.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(record_activity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkAnomalies(){

        String iduser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        changereference = FirebaseDatabase.getInstance().getReference().child(iduser).child("Anomalies");

        changereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int idanomalie = 0;
                String checkanomalie;
                StringBuilder textanomalie = new StringBuilder();
                StringBuilder textdescription = new StringBuilder();

                while(true) {
                    idanomalie = idanomalie +1;
                    checkanomalie = String.valueOf(idanomalie);
                    try {
                        if (idanomalie == 1) {
                            textanomalie.append(Objects.requireNonNull(dataSnapshot.child(checkanomalie).child("date").getValue()).toString());
                            textdescription.append(Objects.requireNonNull(dataSnapshot.child(checkanomalie).child("description").getValue()).toString());

                        }else{
                            textanomalie.append(Html.fromHtml("<br />")).append(Html.fromHtml("<br />")).append(Objects.requireNonNull(dataSnapshot.child(checkanomalie).child("date").getValue()).toString());
                            textdescription.append(Html.fromHtml("<br />")).append(Html.fromHtml("<br />")).append(Objects.requireNonNull(dataSnapshot.child(checkanomalie).child("description").getValue()).toString());

                        }
                    } catch (Exception e) {
                        anomalie.setText(textdescription.toString());
                        dateanomalie.setText(textanomalie.toString());
                        break;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
