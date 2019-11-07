package com.example.watercontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class register_activity extends AppCompatActivity {

    Button gobackbutton;
    EditText edit_name, edit_lastname, edit_email, edit_password, edit_password_again;
    TextInputLayout input_name, input_lastname, input_email, input_password, input_password_again;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_register_activity);

        //Declaración de variables
        //Buttons
        gobackbutton = findViewById(R.id.button_goback);

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
        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_activity.this, MainActivity.class));
            }
        });
    }
}
