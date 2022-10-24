package com.buap.stu.buapstu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button ini_sesion;
    private EditText correo2;
    private EditText contrasena;
    private FirebaseAuth mAuth;

    String mail = "";
    String password = "";

    /*private void goToPrincipal() {
        Intent intent = new Intent(LoginActivity.this,PrincipalActivity.class);
        startActivity(intent);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ini_sesion = (Button) findViewById(R.id.ini_sesion);
        mAuth = FirebaseAuth.getInstance();
        correo2 = findViewById(R.id.correo);
        contrasena = findViewById(R.id.Password);

        ini_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = correo2.getText().toString();
                password = contrasena.getText().toString();
                if(!mail.isEmpty() && !password.isEmpty()){
                    iniciarSesion();
                }else{
                    Toast.makeText(LoginActivity.this, "Introduzca usuario y contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    //Con este metodo podremos hacer la conexion entre el firebase y la aplicacion
    public void iniciarSesion(){

        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent intent = new Intent(LoginActivity.this,PrincipalActivity.class);
                            Toast.makeText(LoginActivity.this, "Sesion iniciada con exito", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña no valido",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


}