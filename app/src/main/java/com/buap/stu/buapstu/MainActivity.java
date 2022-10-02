package com.buap.stu.buapstu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button alumno,conductor;

    private void goToSelectAuth(String usuario){
        Bundle extras = new Bundle();
        extras.putString("usuario",usuario);
        Intent intent = new Intent(MainActivity.this, SelectOptionAuthActivity.class);
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alumno = findViewById(R.id.SoyAlumno);
        conductor = findViewById(R.id.SoyConductor);

        alumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectAuth("alumno");
            }
        });

        conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSelectAuth("conductor");
            }
        });

    }
}