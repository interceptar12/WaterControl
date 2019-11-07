package com.example.watercontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class changeemail_activity extends AppCompatActivity {

    Button gobackbutton, savechangesbutton;
    EditText edit_email, edit_password, edit_newemail;
    TextInputLayout input_email, input_password, input_newemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException ignored){}

        setContentView(R.layout.activity_changeemail_activity);

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
                startActivity(new Intent(changeemail_activity.this, account_activity.class));
            }
        });

        savechangesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(changeemail_activity.this, account_activity.class));
            }
        });
    }
}
