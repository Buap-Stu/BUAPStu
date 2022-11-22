package com.buap.stu.buapstu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.buap.stu.buapstu.models.Alumno;
import com.buap.stu.buapstu.models.Boleto;
import com.buap.stu.buapstu.models.Horario;
import com.buap.stu.buapstu.models.Ruta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ReservaActivity extends AppCompatActivity {
    private List<Ruta> rutas = new ArrayList<>();
    private List<String> direcionesRutas = new ArrayList<>();
    private List<Horario> horarios = new ArrayList<>();
    private List<String> horarioCompleto = new ArrayList<>();
    private ListView listViewRutas,listViewHorarios;
    private Alumno usuario;
    private TextView textCreditos,ruta_elegida,horaio_elegido,fecha,instRuta,instHorario,text_Costo;
    private Boleto boleto = new Boleto();

    private Button get_fecha,realizar_compra;
    private DatabaseReference mDatabase;
    private Bundle parametros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        parametros = this.getIntent().getExtras();
        usuario = (Alumno) parametros.getSerializable("usuario");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        crearListaRutas();

        Date d = new Date();
        CharSequence s  = DateFormat.format("dd/MM/yyyy ", d.getTime());

        listViewRutas = (ListView) findViewById(R.id.ListViewRutas);
        listViewHorarios = (ListView) findViewById(R.id.ListViewHoario);
        textCreditos = (TextView) findViewById(R.id.RCredits);
        ruta_elegida = (TextView) findViewById(R.id.RutaElegida);
        horaio_elegido = (TextView) findViewById(R.id.HorarioElegido);
        fecha = (TextView) findViewById(R.id.Fecha);
        instHorario = (TextView) findViewById(R.id.intHorario);
        instRuta = (TextView) findViewById(R.id.intRuta);
        text_Costo = (TextView) findViewById(R.id.textCosto);

        get_fecha = (Button) findViewById(R.id.GetFecha);
        realizar_compra = (Button) findViewById(R.id.realizarCompra);

        textCreditos.setText("Creditos: "+usuario.getCreditos());
        textCreditos.setTextSize(30);
        textCreditos.setGravity(Gravity.RIGHT);
        ruta_elegida.setTextSize(20);
        horaio_elegido.setTextSize(20);

        get_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int anio = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(ReservaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String fechaReserva  = dayOfMonth+"/"+month+"/"+year;
                        String[] partesFecha= (s.toString()).split("/");
                        if(Integer.parseInt(partesFecha[0]) < dayOfMonth && Integer.parseInt(partesFecha[1]) <= month && 2022 <= year){
                            fecha.setText(fechaReserva);
                            fecha.setTextSize(20);
                            fecha.setTextColor(Color.WHITE);
                            get_fecha.setVisibility(View.GONE);
                            boleto.setFecha(fechaReserva);
                            boleto.setCosto(4);
                            text_Costo.setVisibility(View.VISIBLE);
                            realizar_compra.setVisibility(View.VISIBLE);

                        }else {
                            fecha.setText("La fecha debe ser posterior al dia de hoy");
                            fecha.setTextSize(20);
                            fecha.setTextColor(Color.RED);
                        }
                    }
                },anio, mes , dia);
                dpd.show();
            }
        });

        realizar_compra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarBoletoDB();
            }
        });
    }

    private void guardarBoletoDB() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Usuarios").child(usuario.getUid()).child("Boletos").push().setValue(boleto);
        Intent intent = new Intent(ReservaActivity.this,PrincipalActivity.class);
        intent.putExtras(parametros);
        startActivity(intent);
    }

    private void createListViewRutas() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, direcionesRutas);
        listViewRutas.setAdapter(adapter);

        listViewRutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ReservaActivity.this,rutas.get(i).punto_inicial,Toast.LENGTH_SHORT).show();
                listViewRutas.setVisibility(View.GONE);
                horarios.clear();
                horarioCompleto.clear();
                ruta_elegida.setText(direcionesRutas.get(i));
                boleto.setRuta(direcionesRutas.get(i));
                instRuta.setVisibility(View.GONE);
                if(!Objects.equals(rutas.get(i).punto_inicial, "CU")){
                    crearListHorario("HorarioDirrecionCU");
                }else{
                    crearListHorario("HorarioDesdeCU");
                }
            }
        });
    }

    private void crearListHorario(String children){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(children).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for(DataSnapshot HORARIO: task.getResult().getChildren()){
                        Horario horario = HORARIO.getValue(Horario.class);
                        horarios.add(horario);
                        assert horario != null;
                        horarioCompleto.add("Hora de Salida: "+horario.horaSalida+", Hora de llegada: "+ horario.horaAproxLlegada);
                    }
                }
                createListViewHorarios();
            }
        });
    }

    private void createListViewHorarios() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, horarioCompleto);
        listViewHorarios.setAdapter(adapter);
        listViewHorarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listViewHorarios.setVisibility(View.GONE);
                horaio_elegido.setText(horarioCompleto.get(i));
                boleto.setHorario(horarioCompleto.get(i));
                get_fecha.setVisibility(View.VISIBLE);
                instHorario.setVisibility(View.GONE);
            }
        });
    }

    private void crearListaRutas(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Rutas").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for(DataSnapshot RUTA: task.getResult().getChildren()){
                        Ruta ruta = RUTA.getValue(Ruta.class);
                        rutas.add(ruta);
                        direcionesRutas.add(ruta.punto_inicial+" - "+ruta.punto_final);
                    }
                }
                createListViewRutas();
            }
        });
    }
}