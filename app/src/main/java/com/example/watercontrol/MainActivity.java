package com.example.watercontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    Button registerbutton, recoverpasswordbutton, loginbutton;
    EditText edit_email, edit_password;
    TextInputLayout input_email, input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);

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
                startActivity(new Intent(MainActivity.this, home_activity.class));
            }
        });
    }
}
