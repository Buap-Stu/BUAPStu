package com.buap.stu.buapstu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RegistroActivity extends AppCompatActivity {

    ImageButton conductor,students;
    LinearLayout formContainerDriver,LLImaButtons,DriverContainer,StudentContainer,formContainerStuden,LLContBTNResgitro;
    TextView mensajeRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

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
    }
}