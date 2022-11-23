package com.buap.stu.buapstu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class TransferirActivity extends AppCompatActivity {

    Button btntransferir, Buscar, Cancelar;
    LinearLayout TransferirCreditos2, transferir, buscarMatricula;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferir);

        btntransferir=(Button) findViewById(R.id.TransferirCreditos);
        Buscar =  findViewById(R.id.buscar);
        Cancelar=findViewById(R.id.Cancelar);
        TransferirCreditos2 = (LinearLayout) this.findViewById(R.id.TransferirCreditos2);
        buscarMatricula = (LinearLayout) this.findViewById(R.id.BuscarMatricula);
        transferir = (LinearLayout) this.findViewById(R.id.transferir);

        Buscar.setOnClickListener(view -> {
            TransferirCreditos2.setVisibility(View.VISIBLE);
            buscarMatricula.setVisibility(View.GONE);
            transferir.setVisibility(View.VISIBLE);
        });

        Cancelar.setOnClickListener(view -> {
            Intent intent = new Intent(TransferirActivity.this, TransferirActivity.class);
            startActivity(intent);


        });

    }
}