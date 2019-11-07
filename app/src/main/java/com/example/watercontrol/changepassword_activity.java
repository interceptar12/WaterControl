package com.example.watercontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class changepassword_activity extends AppCompatActivity {

    Button gobackbutton, savechangesbutton;
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
                finish();
            }
        });
    }
}
