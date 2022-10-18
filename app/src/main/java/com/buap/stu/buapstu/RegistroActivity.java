package com.buap.stu.buapstu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RegistroActivity extends AppCompatActivity {

    ImageButton conductor,students;
    LinearLayout formContainerDriver,LLImaButtons,DriverContainer,StudentContainer,formContainerStuden,LLContBTNResgitro;
    TextView mensajeRegistro;

    private EditText correo, contrasena, nombre, matricula;
    private Button btnRegistrarse;

    private String name= "";
    private String mail= "";
    private String mat= "";
    private String password= "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        correo = findViewById(R.id.AEmail);
        contrasena =findViewById(R.id.APassword);
        nombre = findViewById(R.id.ANombreCompleto);
        matricula = findViewById(R.id.AMAtricula);
        btnRegistrarse =  findViewById(R.id.BTNRegistro);


        conductor=(ImageButton) findViewById(R.id.imageButtonDriver);
        students=(ImageButton) findViewById(R.id.imageButtonStudent);

        LLImaButtons = (LinearLayout) this.findViewById(R.id.LLImaButtons);
        DriverContainer = (LinearLayout) this.findViewById(R.id.DriverContainer);
        StudentContainer = (LinearLayout) this.findViewById(R.id.StudentContainer);
        formContainerDriver = (LinearLayout) this.findViewById(R.id.fromContainerDriver);
        formContainerStuden = (LinearLayout) this.findViewById(R.id.fromContainerAlumno);
        LLContBTNResgitro = (LinearLayout) this.findViewById(R.id.LLContBTNResgidro);

        mensajeRegistro = (TextView) this.findViewById(R.id.mensajeRegistro);

        conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensajeRegistro.setText("A elegido el usuario tipo:");
                formContainerDriver.setVisibility(View.VISIBLE);
                LLImaButtons.setGravity(Gravity.CENTER);
                StudentContainer.setVisibility(View.GONE);
                LLContBTNResgitro.setVisibility(View.VISIBLE);

            }
        });

        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensajeRegistro.setText("A elegido el usuario tipo:");
                formContainerStuden.setVisibility(View.VISIBLE);
                LLImaButtons.setGravity(Gravity.CENTER);
                DriverContainer.setVisibility(View.GONE);
                LLContBTNResgitro.setVisibility(View.VISIBLE);
            }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nombre.getText().toString();
                mat = matricula.getText().toString();
                mail = correo.getText().toString();
                password = contrasena.getText().toString();

                if(!name.isEmpty() && !mail.isEmpty() && !password.isEmpty() && !mat.isEmpty()){
                    if(password.length()>=6){
                        registrarUsuario();
                    }
                    else{
                        Toast.makeText(RegistroActivity.this, "El password debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    //Metodo que nos hara registrarnos en la firebase
    private void registrarUsuario(){

        mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> datosUsuario = new HashMap<>();
                    datosUsuario.put("Nombre", name);
                    datosUsuario.put("Email", mail);
                    datosUsuario.put("Matricula", mat);
                    datosUsuario.put("Contraseña", password);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Usuarios").child(id).setValue(datosUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(RegistroActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Error al crear usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}