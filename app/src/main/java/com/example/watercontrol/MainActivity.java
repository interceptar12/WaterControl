package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //Variable para poder ver el error en consola
    private static final String TAG = "Error";

    Button registerbutton, recoverpasswordbutton, loginbutton;
    ProgressDialog progressDialog;
    EditText edit_email, edit_password;
    TextInputLayout input_email, input_password;

    //Declaramos lo necesario para firebase
    DatabaseReference mDatabase;
    DatabaseReference changereference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        setContentView(R.layout.activity_main);

        //Inicializamos el objeto Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Declaración de Variables
        //Buttons
        registerbutton = findViewById(R.id.button_register);
        recoverpasswordbutton = findViewById(R.id.button_recover_password);
        loginbutton = findViewById(R.id.login);

        //EditText e Inputtext
        edit_email = findViewById(R.id.edit_text_email);
        edit_password = findViewById(R.id.edit_text_password);

        input_email = findViewById(R.id.text_input_email);
        input_password = findViewById(R.id.text_input_password);


        //Llamada de métodos
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, register_activity.class));
            }
        });

        recoverpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, email_activity.class));
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VALIDACIONES
                logIn();
            }
        });
    }

    //Metodos

    public void logIn(){
        startProgressdialog();
        final String emailtext = edit_email.getText().toString().trim();
        final String passwordtext = edit_password.getText().toString().trim();

        //Inciando sesion
        mAuth.signInWithEmailAndPassword(emailtext, passwordtext)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            //Sacanndo el nombre
                            String iduser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            changereference = FirebaseDatabase.getInstance().getReference().child(iduser).child("Users");

                            changereference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String fullname;
                                    String name= Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                                    String lastname= Objects.requireNonNull(dataSnapshot.child("lastname").getValue()).toString();

                                    fullname = name + " " + lastname;

                                    //Recueperar el valor de un recurso
                                    Resources res = getResources();
                                    String welcome = res.getString(R.string.log_in_successful);

                                    Toast.makeText(MainActivity.this, welcome + " " + fullname,
                                            Toast.LENGTH_SHORT).show();
                                    endProgressdialog();

                                    startActivity(new Intent(MainActivity.this, home_activity.class));

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getApplicationContext(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {
                            endProgressdialog();
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, R.string.log_in_failed,
                                    Toast.LENGTH_SHORT).show();

                        }



                    }
                });

    }

    public void startProgressdialog(){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Ingresar");
        progressDialog.setMessage("...Iniciando Sesión...");

        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void endProgressdialog(){
        progressDialog.dismiss();
    }
}
