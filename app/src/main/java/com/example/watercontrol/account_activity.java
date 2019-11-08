package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class account_activity extends AppCompatActivity {
    //Variable para poder ver el error en consola
    private static final String TAG = "Error";

    Button gobackbutton, changeemailbutton, changepasswordbutton;
    EditText edit_name, edit_lastname, edit_email;
    TextInputLayout input_name, input_lastname, input_email;

    //Declaramos lo necesario para firebase
    DatabaseReference mDatabase;
    DatabaseReference changereference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activity);

        //Inicializamos el objeto Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Declaración de variables
        //Botones
        gobackbutton = findViewById(R.id.button_goback);
        changeemailbutton = findViewById(R.id.button_change_email);
        changepasswordbutton = findViewById(R.id.button_change_password);

        //Edit Text e Input Text
        edit_name = findViewById(R.id.edit_text_name);
        edit_lastname = findViewById(R.id.edit_text_lastname);
        edit_email = findViewById(R.id.edit_text_email);

        input_name = findViewById(R.id.text_input_name);
        input_lastname = findViewById(R.id.text_input_lastname);
        input_email = findViewById(R.id.text_input_email);


        //Llamada de métodos
        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(account_activity.this, home_activity.class));
            }
        });

        changeemailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(account_activity.this, changeemail_activity.class));
            }
        });

        changepasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(account_activity.this, changepassword_activity.class));
            }
        });

        loadValues();
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
                startActivity(new Intent(account_activity.this, support_activity.class));
                return true;
            case R.id.settings:
                Toast.makeText(this, "Actualmente estás en la sección seleccionada", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                startActivity(new Intent(account_activity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadValues(){
        //Sacando nombre, apellido y correo electronico
        String iduser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        changereference = FirebaseDatabase.getInstance().getReference().child(iduser).child("User");

        changereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name= Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                String lastname= Objects.requireNonNull(dataSnapshot.child("lastname").getValue()).toString();
                String email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();

                edit_name.setText(name);
                edit_email.setText(email);
                edit_lastname.setText(lastname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
