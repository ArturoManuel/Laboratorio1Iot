package com.example.laboratorio1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.teleAhorcado);

        registerForContextMenu(mTextView);

        Button butJugar = findViewById(R.id.idjugar);
        butJugar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.teleAhorcado) {
            getMenuInflater().inflate(R.menu.context_menu, menu);
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.azul) {
            mTextView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
            return true;
        } else if (id == R.id.verde) {
            mTextView.setTextColor(Color.GREEN);
            return true;
        } else if (id == R.id.rojo) {
            mTextView.setTextColor(Color.RED);
            return true;
        }
        return super.onContextItemSelected(item);
    }




}