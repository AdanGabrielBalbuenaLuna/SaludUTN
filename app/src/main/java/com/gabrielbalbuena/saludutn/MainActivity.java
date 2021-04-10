package com.gabrielbalbuena.saludutn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the TextView
        TextView saludFisicaTV = (TextView)findViewById(R.id.textView_salud_fisica);

        saludFisicaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an explicit intent
                Intent saludFisicaIntent = new Intent(MainActivity.this, SaludFisica.class);

                //Use intent method
                startActivity(saludFisicaIntent);
            }
        });

        //Find the TextView
        TextView saludMetalTV = (TextView)findViewById(R.id.textView_salud_mental);

        saludMetalTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an explicit intent
                Intent saludMentalIntent = new Intent(MainActivity.this, SaludMental.class);

                //Use intent method
                startActivity(saludMentalIntent);
            }
        });


    }
}