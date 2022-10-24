package com.buap.stu.buapstu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
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
    LinearLayout formContainerDriver,LLImaButtons,DriverContainer,StudentContainer,formContainerStuden,LLContBTNRegistro;
    TextView mensajeRegistro;

    private EditText correo, contrasena, nombre, matricula;
    private EditText correo2, contrasena2, nombre2, afiliacion;
    private Button btnRegistrarse,btnRegistrarse2;

    private String name= "";
    private String mail= "";
    private String mat= "";
    private String password= "";

    private String name2= "";
    private String mail2= "";
    private String af= "";
    private String password2= "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @SuppressLint("MissingInflatedId")
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

        correo2 = findViewById(R.id.DCorreo);
        contrasena2 =findViewById(R.id.DPassword);
        nombre2 = findViewById(R.id.DNombreCompleto);
        afiliacion = findViewById(R.id.DNumeroAfiliacion);
        btnRegistrarse2 =  findViewById(R.id.btnRegistro2);


        conductor=(ImageButton) findViewById(R.id.imageButtonDriver);
        students=(ImageButton) findViewById(R.id.imageButtonStudent);

        LLImaButtons = (LinearLayout) this.findViewById(R.id.LLImaButtons);
        DriverContainer = (LinearLayout) this.findViewById(R.id.DriverContainer);
        StudentContainer = (LinearLayout) this.findViewById(R.id.StudentContainer);
        formContainerDriver = (LinearLayout) this.findViewById(R.id.fromContainerDriver);
        formContainerStuden = (LinearLayout) this.findViewById(R.id.fromContainerAlumno);
        LLContBTNRegistro = (LinearLayout) this.findViewById(R.id.LLContBTNRegistro);

        mensajeRegistro = (TextView) this.findViewById(R.id.mensajeRegistro);

        conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensajeRegistro.setText("A elegido el usuario tipo:");
                formContainerDriver.setVisibility(View.VISIBLE);
                LLImaButtons.setGravity(Gravity.CENTER);
                StudentContainer.setVisibility(View.GONE);
                LLContBTNRegistro.setVisibility(View.VISIBLE);


            }
        });

        students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensajeRegistro.setText("A elegido el usuario tipo:");
                formContainerStuden.setVisibility(View.VISIBLE);
                LLImaButtons.setGravity(Gravity.CENTER);
                DriverContainer.setVisibility(View.GONE);
                LLContBTNRegistro.setVisibility(View.VISIBLE);
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
                    if(name.length()>30 || name.length()<3){
                        Toast.makeText(RegistroActivity.this, "Introduzca un nombre valido", Toast.LENGTH_SHORT).show();
                    }else{
                        if(mat.length()<9 || mat.length()>9){
                            Toast.makeText(RegistroActivity.this, "Introduzca una matricula valida", Toast.LENGTH_SHORT).show();
                        }else{
                            if(!mail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                                Toast.makeText(RegistroActivity.this, "Introduzca un correo valido", Toast.LENGTH_SHORT).show();
                            }else{
                                if(password.length()<6){
                                    Toast.makeText(RegistroActivity.this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                                }else{
                                    registrarAlumno();
                                }
                            }
                        }
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegistrarse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name2 = nombre2.getText().toString();
                af = afiliacion.getText().toString();
                mail2 = correo2.getText().toString();
                password2 = contrasena2.getText().toString();

                if(!name2.isEmpty() && !mail2.isEmpty() && !password2.isEmpty() && !af.isEmpty()){
                    if(name2.length()>30 || name2.length()<3){
                        Toast.makeText(RegistroActivity.this, "Introduzca un nombre valido", Toast.LENGTH_SHORT).show();
                    }else{
                        if(af.length()<9 || af.length()>9){
                            Toast.makeText(RegistroActivity.this, "Introduzca un numero de afiliacion valido", Toast.LENGTH_SHORT).show();
                        }else{
                            if(!mail2.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                                Toast.makeText(RegistroActivity.this, "Introduzca un correo valido", Toast.LENGTH_SHORT).show();
                            }else{
                                if(password2.length()<6){
                                    Toast.makeText(RegistroActivity.this, "La contraseña debe tener almenos 6 caracteres", Toast.LENGTH_SHORT).show();
                                }else{
                                    registrarConductor();
                                }
                            }
                        }
                    }
                }else{
                    Toast.makeText(RegistroActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //Metodo que nos hara registrarnos en la firebase
    private void registrarAlumno(){

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
                    mDatabase.child("Alumnos").child(id).setValue(datosUsuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                                Toast.makeText(RegistroActivity.this, "Usuario alumno creado con exito", Toast.LENGTH_SHORT).show();
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

    //Metodo que nos hara registrarnos en la firebase
    private void registrarConductor(){

        mAuth.createUserWithEmailAndPassword(mail2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> datosUsuario2 = new HashMap<>();
                    datosUsuario2.put("Nombre", name2);
                    datosUsuario2.put("Email", mail2);
                    datosUsuario2.put("Numero de afiliacion", af);
                    datosUsuario2.put("Contraseña", password2);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Conductores").child(id).setValue(datosUsuario2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                                Toast.makeText(RegistroActivity.this, "Usuario conductor creado con exito", Toast.LENGTH_SHORT).show();
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