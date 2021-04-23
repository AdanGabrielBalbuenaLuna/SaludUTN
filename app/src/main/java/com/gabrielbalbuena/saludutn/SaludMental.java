package com.gabrielbalbuena.saludutn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SaludMental extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salud_mental);

        //Find the TextView
        TextView diarioEmocionesTV = (TextView)findViewById(R.id.textView_diario_de_emociones);

        diarioEmocionesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an explicit intent
                Intent datosPersonalesIntent = new Intent(SaludMental.this, DiarioEmociones.class);

                //Use intent method
                startActivity(datosPersonalesIntent);
            }
        });
    }
}