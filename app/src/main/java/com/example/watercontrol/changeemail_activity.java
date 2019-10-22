package com.example.watercontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class changeemail_activity extends AppCompatActivity {

    Button gobackbutton, savechangesbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_changeemail_activity);

        //Declaración de variables
        gobackbutton = findViewById(R.id.button_goback);
        savechangesbutton = findViewById(R.id.button_savechanges);

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
