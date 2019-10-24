package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class support_activity extends AppCompatActivity {

    Button gobackbutton, button_contact_support1,button_request_pipa1,button_request_pipa2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_activity);

        //Declaración de variables
        gobackbutton = findViewById(R.id.button_goback);
        button_request_pipa1 = findViewById(R.id.button_request_pipa1);
        button_request_pipa2 = findViewById(R.id.button_request_pipa2);
        button_contact_support1 = findViewById(R.id.button_contact_support1);

        //Llamada de métodos
        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(support_activity.this, home_activity.class));
            }
        });

        button_contact_support1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dial = "tel:" + "073";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });

        button_request_pipa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dial = "tel:" + "4494481721";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });
        button_request_pipa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dial = "tel:" + "4491571189";
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.call:
                Toast.makeText(this, "Actualmente estás en la sección seleccionada", Toast.LENGTH_LONG).show();
                return true;
            case R.id.settings:
                startActivity(new Intent(support_activity.this, account_activity.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(support_activity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
