package com.buap.stu.buapstu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.buap.stu.buapstu.models.Alumno;
import com.buap.stu.buapstu.models.Conductor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    Button ini_sesion;
    private EditText correo2;
    private EditText contrasena;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    String mail = "";
    String password = "";
    String tipo= "";
    /*private void goToPrincipal() {
        Intent intent = new Intent(LoginActivity.this,PrincipalActivity.class);
        startActivity(intent);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ini_sesion = (Button) findViewById(R.id.fecha);
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
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mAuth.signInWithEmailAndPassword(mail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            mDatabase.child("Usuarios").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", task.getException());
                                    }
                                    else {
                                        Bundle extras = new Bundle();
                                        Alumno usuario = task.getResult().getValue(Alumno.class);


                                        if (Objects.equals(usuario.type, "Alumno")){
                                            usuario.setUid(user.getUid());
                                            extras.putSerializable("usuario",usuario);
                                            Intent intent = new Intent(LoginActivity.this,PrincipalActivity.class);
                                            intent.putExtras(extras);
                                            startActivity(intent);
                                        }
                                        else if(Objects.equals(usuario.type, "Conductor")){

                                        }
                                    }
                                }
                            });

                            mDatabase.child("Usuarios").child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    Bundle extras = new Bundle();
                                    Conductor usuario = task.getResult().getValue(Conductor.class);
                                    if (Objects.equals(usuario.type, "Conductor")){
                                        usuario.setUid(user.getUid());
                                        extras.putSerializable("usuario",usuario);
                                        Intent intent = new Intent(LoginActivity.this,ConductorActivity.class);
                                        intent.putExtras(extras);
                                        startActivity(intent);
                                    }
                                }
                            });

                            //Toast.makeText(LoginActivity.this, "Sesion iniciada con exito", Toast.LENGTH_LONG).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña no valido",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


}