package com.example.camaraactividad;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nombreFoto;
    private ImageView imagenFoto;

    // Recibe el resultado cuando se cierra la aplicación de cámara.
    private final ActivityResultLauncher<Intent> camaraLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    resultado -> {
                        if (resultado.getResultCode() != RESULT_OK) {
                            return;
                        }

                        Intent datos = resultado.getData();

                        if (datos != null && datos.getExtras() != null) {
                            Object foto = datos.getExtras().get("data");

                            if (foto instanceof Bitmap) {
                                imagenFoto.setImageBitmap((Bitmap) foto);
                                return;
                            }
                        }

                        Toast.makeText(
                                this,
                                "La cámara no devolvió una vista previa",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreFoto = findViewById(R.id.ETNombreFoto);
        imagenFoto = findViewById(R.id.imageView);

        Button botonFoto = findViewById(R.id.btnFoto);
        Button botonEnviar = findViewById(R.id.BTAceptar);

        botonFoto.setOnClickListener(view -> abrirCamara());
        botonEnviar.setOnClickListener(view -> enviarNombre());
    }

    private void abrirCamara() {
        // Intent implícito: solicitamos una acción a la app de cámara.
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            camaraLauncher.launch(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(
                    this,
                    "No hay una aplicación de cámara disponible",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private void enviarNombre() {
        String stNombre = nombreFoto.getText().toString().trim();

        if (stNombre.isEmpty()) {
            nombreFoto.setError("Escribe el nombre de la foto");
            nombreFoto.requestFocus();
            return;
        }

        // Intent explícito: indicamos qué Activity queremos abrir.
        Intent intent = new Intent(MainActivity.this, AResultado.class);

        // Enviamos el texto a la segunda pantalla.
        intent.putExtra("STNombre", stNombre);

        startActivity(intent);
    }
}