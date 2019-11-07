package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class email_activity extends AppCompatActivity {
    //Variable para poder ver el error en consola
    private static final String TAG = "Error";

    Button passwordverify, gobackbutton;
    ProgressDialog progressDialog;
    EditText edit_email;
    TextInputLayout input_email;

    //Declaramos lo necesario para firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){

        }

        setContentView(R.layout.activity_email_activity);

        //Inicializamos el objeto Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Declaración de variables
        //Buttones
        passwordverify = findViewById(R.id.button_passwordverify);
        gobackbutton = findViewById(R.id.button_goback);

        //EditText e Inputtext
        edit_email = findViewById(R.id.edit_text_email);

        input_email = findViewById(R.id.text_input_email);

        //Llamada de métodos
        passwordverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverdPassword();
            }
        });

        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void recoverdPassword(){
        startProgressdialog();
        String emailtext = edit_email.getText().toString().trim();

        mAuth.sendPasswordResetEmail(emailtext)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            endProgessdialog();
                            Toast.makeText(email_activity.this, R.string.recoveremail_send,
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Log.w(TAG, "recoverPassword:failure", task.getException());;
                            Toast.makeText(email_activity.this, R.string.recoveremail_failed,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void startProgressdialog(){
        progressDialog = new ProgressDialog(email_activity.this);
        progressDialog.setTitle("Recuperar Contraseña");
        progressDialog.setMessage("...Enviando correo de Recuperación...");

        progressDialog.show();
    }

    public void endProgessdialog(){
        progressDialog.dismiss();
    }
}
