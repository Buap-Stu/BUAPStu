package com.buap.stu.buapstu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.buap.stu.buapstu.models.Alumno;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrincipalActivity extends AppCompatActivity {
    Button LogOut, transferir;
    ImageButton addCreditos;
    TextView TextCreditos;
    private FirebaseAuth mAuth;
    private FirebaseUser User;
    DatabaseReference mDatabase;
    Alumno usuario;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_principal);

        Bundle parametros = this.getIntent().getExtras();
        usuario = (Alumno) parametros.getSerializable("usuario");
        TextCreditos = (TextView) findViewById(R.id.textCreditos);
        mostrarCreditos();

        addCreditos = (ImageButton) findViewById(R.id.addCredit);

        LogOut = (Button) findViewById(R.id.CerrarSesion);
        transferir = (Button) findViewById(R.id.TC);
        mAuth = FirebaseAuth.getInstance();
        User = mAuth.getCurrentUser();

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(PrincipalActivity.this, "Ha cerrado sesion", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PrincipalActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        addCreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario.setCreditos(usuario.getCreditos()+10);
                mDatabase.child("Usuarios").child(usuario.getUid()).setValue(usuario);
                mostrarCreditos();
            }
        });

        transferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,TransferirActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mostrarCreditos() {
        TextCreditos.setText("Saldo: "+String.valueOf(usuario.getCreditos()+"    "));
    }
}