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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class account_activity extends AppCompatActivity {

    Button gobackbutton, changeemailbutton, changepasswordbutton;
    EditText edit_name, edit_lastname, edit_email;
    TextInputLayout input_name, input_lastname, input_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activity);

        //Declaración de variables
        //Botones
        gobackbutton = findViewById(R.id.button_goback);
        changeemailbutton = findViewById(R.id.button_change_email);
        changepasswordbutton = findViewById(R.id.button_change_password);

        //Edit Text e Input Text
        edit_name = findViewById(R.id.edit_text_name);
        edit_lastname = findViewById(R.id.edit_text_lastname);
        edit_email = findViewById(R.id.edit_text_email);

        input_name = findViewById(R.id.text_input_name);
        input_lastname = findViewById(R.id.text_input_lastname);
        input_email = findViewById(R.id.text_input_email);


        //Llamada de métodos
        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(account_activity.this, home_activity.class));
            }
        });

        changeemailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(account_activity.this, changeemail_activity.class));
            }
        });

        changepasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(account_activity.this, changepassword_activity.class));
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
                startActivity(new Intent(account_activity.this, support_activity.class));
                return true;
            case R.id.settings:
                Toast.makeText(this, "Actualmente estás en la sección seleccionada", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                startActivity(new Intent(account_activity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
