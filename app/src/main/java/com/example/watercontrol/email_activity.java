package com.example.watercontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class email_activity extends AppCompatActivity {

    Button passwordverify, gobackbutton;
    EditText edit_email;
    TextInputLayout input_email;

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
                startActivity(new Intent(email_activity.this, MainActivity.class));
            }
        });

        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(email_activity.this, MainActivity.class));
            }
        });
    }
}
