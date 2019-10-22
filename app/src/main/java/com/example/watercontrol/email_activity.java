package com.example.watercontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class email_activity extends AppCompatActivity {

    Button verificartokenbutton, gobackbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_email_activity);

        //Declaración de variables
        verificartokenbutton = findViewById(R.id.button_tokenverify);
        gobackbutton = findViewById(R.id.button_goback);

        //Llamada de métodos
        verificartokenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(email_activity.this, newpassword.class));
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
