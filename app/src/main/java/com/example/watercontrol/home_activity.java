package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class home_activity extends AppCompatActivity {

    Button recordbutton, configurestorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        //Declaración de variables
        recordbutton = findViewById(R.id.button_record);
        configurestorage = findViewById(R.id.button_configurestorage);

        //Llamada de metodos
        recordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_activity.this, record_activity.class));
            }
        });

        configurestorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home_activity.this, configurestorage_activity.class));
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
                startActivity(new Intent(home_activity.this, support_activity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(home_activity.this, account_activity.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(home_activity.this, MainActivity.class));
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
