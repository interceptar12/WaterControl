package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class register_activity extends AppCompatActivity {

    //Variable para poder ver el error en consola
    private static final String TAG = "Error";

    Button gobackbutton, registerbutton;
    ProgressDialog progressDialog;
    EditText edit_name, edit_lastname, edit_email, edit_password, edit_password_again;
    TextInputLayout input_name, input_lastname, input_email, input_password, input_password_again;

    //Declaramos lo necesario para firebase
    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){

        }

        setContentView(R.layout.activity_register_activity);

        //Inicializamos el objeto Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Declaración de variables
        //Buttons
        gobackbutton = findViewById(R.id.button_goback);
        registerbutton = findViewById(R.id.button_register);

        //EditText e Inputtext
        edit_name = findViewById(R.id.edit_text_name);
        edit_lastname = findViewById(R.id.edit_text_lastname);
        edit_email = findViewById(R.id.edit_text_email);
        edit_password = findViewById(R.id.edit_text_password);
        edit_password_again = findViewById(R.id.edit_text_password_again);

        input_name = findViewById(R.id.text_input_name);
        input_lastname = findViewById(R.id.text_input_lastname);
        input_email = findViewById(R.id.text_input_email);
        input_password = findViewById(R.id.text_input_password);
        input_password_again = findViewById(R.id.text_input_password_again);

        //Llamada a metodos
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VALIDACIONES
                registrarUsuario();
            }
        });

        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        }



        //Metodos
        private void registrarUsuario(){
            //ProgressDialog
            startProgressdialog();

            final String emailtext = edit_email.getText().toString().trim();
            final String passwordtext = edit_password.getText().toString().trim();
            final String nametext = edit_name.getText().toString().trim();
            final String lastnametext = edit_lastname.getText().toString().trim();


            //Creando el nuevo usuario
            mAuth.createUserWithEmailAndPassword(emailtext, passwordtext)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Logica luego de crear al usuario

                                String iduser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                                String anomalieid = "1";

                                Map<String, Object> mapuser = new HashMap<>();
                                mapuser.put("userid", iduser );
                                mapuser.put("name", nametext);
                                mapuser.put("lastname", lastnametext);
                                mapuser.put("email", emailtext);


                                Map<String, Object> mapanomalie = new HashMap<>();
                                mapanomalie.put("description", "Puede que haya una obstrucción en la entrada del agua");
                                mapanomalie.put("date", "06/11/2019");

                                Map<String, Object> mapwhater = new HashMap<>();
                                mapwhater.put("velocitywater", "12" );
                                mapwhater.put("distancewater", "15");
                                mapwhater.put("volume", "224" );
                                mapwhater.put("long", "123");
                                mapwhater.put("high", "245" );
                                mapwhater.put("wide", "423");

                                mDatabase.child(iduser).child("User").setValue(mapuser);

                                mDatabase.child(iduser).child("Anomalies").child(anomalieid).setValue(mapanomalie);

                                mDatabase.child(iduser).child("Watertower").setValue(mapwhater);


                                endProgressdialog();
                                Toast.makeText(register_activity.this, R.string.signup_successful,
                                        Toast.LENGTH_SHORT).show();

                                finish();


                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                progressDialog.dismiss();
                                Toast.makeText(register_activity.this, R.string.signup_failed,
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
    }

    public void startProgressdialog(){
        progressDialog = new ProgressDialog(register_activity.this);
        progressDialog.setTitle("Registro");
        progressDialog.setMessage("...Realizando registro en linea...");

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
