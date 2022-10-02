package com.buap.stu.buapstu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SelectOptionAuthActivity extends AppCompatActivity {
    TextView pruebas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);


        Bundle usuario = this.getIntent().getExtras();
        pruebas=(TextView) findViewById(R.id.prueba1);
        String datos =usuario.getString("usuario");
        pruebas.setText(datos);
    }
}