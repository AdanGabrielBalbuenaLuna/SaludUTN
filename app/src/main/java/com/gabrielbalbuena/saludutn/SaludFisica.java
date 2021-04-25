package com.gabrielbalbuena.saludutn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SaludFisica extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salud_fisica);

        //Find the TextView Datos Personales
        TextView datosPersonalesTV = (TextView)findViewById(R.id.textView_datos_personales);

        datosPersonalesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an explicit intent
                Intent datosPersonalesIntent = new Intent(SaludFisica.this, DatosPersonales.class);

                //Use intent method
                startActivity(datosPersonalesIntent);
            }
        });

        //Find the TextView Alergias
        TextView alergiasTV = (TextView)findViewById(R.id.textView_alergias);

        alergiasTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an explicit intent
                Intent alergiasIntent = new Intent(SaludFisica.this, Alergias.class);

                //Use intent method
                startActivity(alergiasIntent);
            }
        });
    }
}