package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

public class configurestorage_activity extends AppCompatActivity {

    Button gobackbutton, savechanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurestorage_activity);



        //Llenado de Spinner
        String [] valuesstorage = {
                "Tinaco (Cilindro Vertical)",
                "Cisterna (Cuadrada)"
        };

        //Definir y crear el objeto de tipo adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, valuesstorage);
        AutoCompleteTextView textView =
                findViewById(R.id.filled_storagetype);
        textView.setAdapter(adapter);

        //Declaraciòn de variables
        gobackbutton = findViewById(R.id.button_goback);
        savechanges = findViewById(R.id.button_savechanges);

        //Llamada de metodos
        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(configurestorage_activity.this, home_activity.class));
            }
        });

        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(configurestorage_activity.this, home_activity.class));
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
                startActivity(new Intent(configurestorage_activity.this, support_activity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(configurestorage_activity.this, account_activity.class));
                return true;
            case R.id.logout:
                startActivity(new Intent(configurestorage_activity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
