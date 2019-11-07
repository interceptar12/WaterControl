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

public class changepassword_activity extends AppCompatActivity {
    //Variable para poder ver el error en consola
    private static final String TAG = "Error";

    Button gobackbutton, savechangesbutton;
    ProgressDialog progressDialog;
    EditText edit_email, edit_lastpassword, edit_newpassword, edit_newpasswordagain;
    TextInputLayout input_email, input_lastpassword, input_newpassword, input_newpasswordagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        setContentView(R.layout.activity_changepassword_activity);

        //Declaración de variables
        //Buttons
        gobackbutton = findViewById(R.id.button_goback);
        savechangesbutton = findViewById(R.id.button_savechanges);

        //EditText e Inputtext
        edit_email = findViewById(R.id.edit_text_email);
        edit_lastpassword = findViewById(R.id.edit_text_lastpassword);
        edit_newpassword = findViewById(R.id.edit_text_new_password);
        edit_newpasswordagain = findViewById(R.id.edit_text_password_again);

        input_email = findViewById(R.id.text_input_email);
        input_lastpassword = findViewById(R.id.text_input_lastpassword);
        input_newpassword = findViewById(R.id.text_input_new_password);
        input_newpasswordagain = findViewById(R.id.text_input_password_again);

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
                //VALIDACIONES
                changePassword();
            }
        });
    }

    //Metodos

    public void changePassword(){
        startProgressdialog();

        final String emailtext = edit_email.getText().toString().trim();//Eliminar espacios al principio y final de la caja de texto
        final String passwordtext = edit_lastpassword.getText().toString().trim();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential(emailtext, passwordtext);

        assert user != null;
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(edit_newpassword.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        endProgressdialog();
                                        Toast.makeText(changepassword_activity.this, R.string.password_update_successful,
                                                Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        endProgressdialog();
                                        Log.d(TAG, "UpdatePassword: Failed", task.getException());
                                        Toast.makeText(changepassword_activity.this, R.string.password_update_failed,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            endProgressdialog();
                            Log.d(TAG, "ReauthenticatePassword: Failed", task.getException());
                            Toast.makeText(changepassword_activity.this, R.string.log_in_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    public void startProgressdialog(){
        progressDialog = new ProgressDialog(changepassword_activity.this);
        progressDialog.setTitle("Cambio de Contraseña");
        progressDialog.setMessage("...Realizando cambio de Contraseña...");

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
