package com.buap.stu.buapstu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    Button ini_sesion;

    private void goToPrincipal() {
        Intent intent = new Intent(LoginActivity.this,PrincipalActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ini_sesion = (Button) findViewById(R.id.ini_sesion);

        ini_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPrincipal();
            }
        });
    }
}