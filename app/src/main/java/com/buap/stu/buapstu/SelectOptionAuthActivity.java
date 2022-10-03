package com.buap.stu.buapstu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOptionAuthActivity extends AppCompatActivity {
    Button crear_cuenta,iniciar_sesion;
    String usuario;

    public void goToRegister(){

        Intent intent = new Intent(SelectOptionAuthActivity.this, RegistroActivity.class);
        startActivity(intent);

    }

    public void goToLogin(){
        Intent intent = new Intent(SelectOptionAuthActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);

        crear_cuenta=(Button) findViewById(R.id.crear_cuenta);
        iniciar_sesion=(Button) findViewById(R.id.iniciar_sesion);

        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });

        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
    }
}