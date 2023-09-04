package com.example.laboratorio1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private String[] palabras = {"Pepito", "Juanito", "Jaime", "Alonso"};
    private String palabraOcuta;
    private int intentosRestantes = 6;
    private boolean[] letrasCorrectas;
    private TextView textViewpalabraOcuta;
    private Button buttonNuevoJuego;
    private long inicio;
    private long tiempoFinJuego;
    private int intentosFallidos = 0;
    List<String> listaMensajes = new ArrayList<>();
    private int numeroJuego = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("TeleAhorcado");
        textViewpalabraOcuta = findViewById(R.id.palabraOculta);
        buttonNuevoJuego = findViewById(R.id.jugarDenuevo);
        buttonNuevoJuego.setOnClickListener(v -> iniciarNuevoJuego());
        iniciarNuevoJuego();
        Button[] buttons = new Button[26];
        
        int[] buttonIds = {
                R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g, R.id.h, R.id.i, R.id.j,
                R.id.k, R.id.l, R.id.m, R.id.n, R.id.o, R.id.p, R.id.q, R.id.r, R.id.s, R.id.t,
                R.id.u, R.id.v, R.id.w, R.id.x, R.id.y, R.id.z
        };
        
        for (int i = 0; i < 26; i++) {
            buttons[i] = findViewById(buttonIds[i]);
            buttons[i].setOnClickListener(this);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.icono,menu);
        return true;
    }
    private void habilitar(boolean habilitar) {
        Button[] botonesLetras = new Button[26];

        for (int i = 0; i < 26; i++) {
            int buttonId = getResources().getIdentifier(String.valueOf((char)('a' + i)), "id", getPackageName());
            botonesLetras[i] = findViewById(buttonId);
        }

        for (Button botonLetra : botonesLetras) {
            botonLetra.setEnabled(habilitar);
        }
    }


    private void iniciarNuevoJuego() {
        inicio = System.currentTimeMillis();

        palabraOcuta = palabras[(int) (Math.random() * palabras.length)];
        letrasCorrectas = new boolean[palabraOcuta.length()];
        for (int i = 0; i < letrasCorrectas.length; i++) {
            letrasCorrectas[i] = false;
        }
        actualizarpalabraOcuta();
        intentosRestantes = 6;
        habilitar(true);
        List<Integer> viewIdsToHide = Arrays.asList(
                R.id.cabeza, R.id.torzo, R.id.brazode, R.id.brazoIzq, R.id.pieIzq, R.id.pieDerecho
        );

        for (int viewId : viewIdsToHide) {
            View view = findViewById(viewId);
            view.setVisibility(View.INVISIBLE);
        }
        intentosFallidos = 0;

    }



    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        char letra = button.getText().charAt(0);
        boolean letraEncontrada = false;

        for (int i = 0; i < palabraOcuta.length(); i++) {
            if (palabraOcuta.charAt(i) == letra) {
                letrasCorrectas[i] = true;
                letraEncontrada = true;
            }
        }

        if (!letraEncontrada) {
            intentosRestantes--;
            mostrarImagenIncorrecta();
        }

        actualizarpalabraOcuta();
        button.setEnabled(false);

        if (intentosRestantes == 0 || palabraOcutaDescubierta()) {
            buttonNuevoJuego.setEnabled(true);
            mostrarmensajes();
        }
    }
    private void mostrarImagenIncorrecta() {
        intentosFallidos++;

        switch (intentosFallidos) {
            case 1:
                findViewById(R.id.cabeza).setVisibility(View.VISIBLE);
                break;
            case 2:
                findViewById(R.id.torzo).setVisibility(View.VISIBLE);
                break;
            case 3:
                findViewById(R.id.brazode).setVisibility(View.VISIBLE);
                break;
            case 4:
                findViewById(R.id.brazoIzq).setVisibility(View.VISIBLE);
                break;
            case 5:
                findViewById(R.id.pieIzq).setVisibility(View.VISIBLE);
                break;
            case 6:
                findViewById(R.id.pieDerecho).setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void actualizarpalabraOcuta() {
        StringBuilder palabra = new StringBuilder();
        for (int i = 0; i < palabraOcuta.length(); i++) {
            if (letrasCorrectas[i]) {
                palabra.append(palabraOcuta.charAt(i));
            } else {
                palabra.append("_ ");
            }
        }
        textViewpalabraOcuta.setText("" + palabra.toString());
        if (palabraOcutaDescubierta()) {
            textViewpalabraOcuta.setText("Ganaste ");
        } else if (intentosRestantes == 0) {
            textViewpalabraOcuta.setText("¡Perdiste! ");
        }
        if (intentosRestantes == 0 || palabraOcutaDescubierta()) {
            buttonNuevoJuego.setEnabled(true);
            mostrarmensajes();
        }

    }

    private void mostrarmensajes() {
        tiempoFinJuego = System.currentTimeMillis();
        String mensaje;
        if (intentosRestantes == 0) {
            mensaje = "La palabra: " + palabraOcuta;
        } else {
            mensaje = "Ganaste.";
        }
        long tiempoTranscurrido = (tiempoFinJuego - inicio) / 1000;
        mensaje += "\nTerminó en: " + tiempoTranscurrido + "s";
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }
    

    private boolean palabraOcutaDescubierta() {
        for (boolean letraCorrecta : letrasCorrectas) {
            if (!letraCorrecta) {
                return false;
            }
        }
        return true;
    }




}