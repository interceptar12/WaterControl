package com.example.watercontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class configurestorage_activity extends AppCompatActivity {

    Button gobackbutton, savechanges;
    EditText edit_large, edit_high, edit_wide;
    TextInputLayout input_large, input_high, input_wide;
    AutoCompleteTextView spinner1;
    ProgressDialog progressDialog;
    public ArrayAdapter<String> adapter;
    public String itemname = "";
    //Declaramos lo necesario para firebase
    DatabaseReference mDatabase;
    DatabaseReference changereference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurestorage_activity);

        //Inicializamos el objeto Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Declaraciòn de variables
        //Buttons
        gobackbutton = findViewById(R.id.button_goback);
        savechanges = findViewById(R.id.button_savechanges);

        //EditText e Inputtext
        edit_large = findViewById(R.id.edit_text_long);
        edit_high = findViewById(R.id.edit_text_high);
        edit_wide = findViewById(R.id.edit_text_wide);

        input_large = findViewById(R.id.text_input_long);
        input_high = findViewById(R.id.text_input_high);
        input_wide = findViewById(R.id.text_input_wide);

        //AutoComplete
        spinner1 = findViewById(R.id.filled_storagetype);

        //Llenado de Spinner
        String [] valuesstorage = {
                "Tinaco (Cilindro Vertical)",
                "Cisterna (Cuadrada)"
        };

        //Definir y crear el objeto de tipo adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, valuesstorage);
        spinner1.setAdapter(adapter);


        //Llamada de metodos
        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(configurestorage_activity.this, home_activity.class));
            }
        });

        spinner1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemname = parent.getItemAtPosition(position).toString();
            }
        });


        savechanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VALIDACIONES
                registerStorage();
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

    public void registerStorage(){


        startProgressdialog();

        String text_long = edit_large.getText().toString();
        String text_wide = edit_wide.getText().toString();
        String text_high = edit_high.getText().toString();

        double diametro = Double.parseDouble(text_wide);
        double radio = diametro / 2;
        double volumen = (radio * radio) * Double.parseDouble(text_high);
        String text_volumen = String.valueOf(volumen);

        String iduser = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        String watertowerid = "1";

        Map<String, Object> mapwhater = new HashMap<>();
        mapwhater.put("velocitywater", "0" );
        mapwhater.put("distancewater", "25");
        mapwhater.put("type", itemname);
        mapwhater.put("volume", text_volumen );
        mapwhater.put("long", text_long);
        mapwhater.put("high", text_high);
        mapwhater.put("wide", text_wide);

        mDatabase.child(iduser).child("Watertower").child(watertowerid).setValue(mapwhater);

        Toast.makeText(configurestorage_activity.this, R.string.successfull_storage,
                Toast.LENGTH_SHORT).show();
        endProgressdialog();
        startActivity(new Intent(configurestorage_activity.this, home_activity.class));
    }

    public void startProgressdialog(){
        progressDialog = new ProgressDialog(configurestorage_activity.this);
        progressDialog.setTitle("Registro");
        progressDialog.setMessage("...Realizando registro en linea...");

        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void endProgressdialog(){
        progressDialog.dismiss();
    }
}
