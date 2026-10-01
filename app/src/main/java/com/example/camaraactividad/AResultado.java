package com.example.camaraactividad;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AResultado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aresultado);

        TextView textoNombre = findViewById(R.id.TVNombre);
        Button botonVolver = findViewById(R.id.btnVolver);

        // Recibimos el dato enviado desde MainActivity.
        String nombre = getIntent().getStringExtra("STNombre");

        if (nombre == null || nombre.trim().isEmpty()) {
            nombre = "Sin nombre";
        }

        textoNombre.setText("Nombre de la foto: " + nombre);

        // Cierra esta pantalla y regresa a la anterior.
        botonVolver.setOnClickListener(view -> finish());
    }
}