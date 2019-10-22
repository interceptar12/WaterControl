package com.example.watercontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class newpassword extends AppCompatActivity {

    Button savechangesbutton, gobackbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_newpassword);

        //Declaración de variables
        savechangesbutton = findViewById(R.id.button_savechanges);
        gobackbutton = findViewById(R.id.button_goback);
        //Llamada de metodos

        savechangesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(newpassword.this, MainActivity.class));
            }
        });

        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(newpassword.this, email_activity.class));
            }
        });


    }
}
