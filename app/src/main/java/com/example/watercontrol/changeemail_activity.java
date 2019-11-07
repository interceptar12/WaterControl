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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class changeemail_activity extends AppCompatActivity {
    //Variable para poder ver el error en consola
    private static final String TAG = "Error";

    Button gobackbutton, savechangesbutton;
    ProgressDialog progressDialog;
    EditText edit_email, edit_password, edit_newemail;
    TextInputLayout input_email, input_password, input_newemail;

    //Declaramos lo necesario para firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        setContentView(R.layout.activity_changeemail_activity);

        //Inicializamos el objeto Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Declaración de variables
        //Buttons
        gobackbutton = findViewById(R.id.button_goback);
        savechangesbutton = findViewById(R.id.button_savechanges);

        //EditText e Inputtext
        edit_email = findViewById(R.id.edit_text_email);
        edit_password = findViewById(R.id.edit_text_password);
        edit_newemail = findViewById(R.id.edit_text_new_email);

        input_email = findViewById(R.id.text_input_email);
        input_password = findViewById(R.id.text_input_password);
        input_newemail = findViewById(R.id.text_input_new_email);

        //Llamada de métodos
        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        savechangesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();
            }
        });
    }

    //Metodos

    public void changeEmail(){
        startProgressdialog();
        final String emailtext = edit_email.getText().toString().trim();
        final String passwordtext = edit_password.getText().toString().trim();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(emailtext, passwordtext);
        assert user != null;
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            assert user != null;
                            user.updateEmail(edit_newemail.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                endProgressdialog();
                                                Toast.makeText(changeemail_activity.this, R.string.email_update_successful,
                                                        Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                endProgressdialog();
                                                Log.d(TAG, "UpdateEmail: Failed", task.getException());
                                                Toast.makeText(changeemail_activity.this, R.string.email_update_failed,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            endProgressdialog();
                            Log.d(TAG, "ReauthenticateEmail: Failed", task.getException());
                            Toast.makeText(changeemail_activity.this, R.string.log_in_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void startProgressdialog(){
        progressDialog = new ProgressDialog(changeemail_activity.this);
        progressDialog.setTitle("Cambio de Correo");
        progressDialog.setMessage("...Realizando cambio de Correo Electrónico...");

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
