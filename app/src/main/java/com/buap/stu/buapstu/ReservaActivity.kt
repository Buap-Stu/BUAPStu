package com.buap.stu.buapstu

import android.R
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.buap.stu.buapstu.core.states.AuthState
import com.buap.stu.buapstu.core.utils.Resource
import com.buap.stu.buapstu.core.utils.utcToFormat
import com.buap.stu.buapstu.databinding.ActivityReservaBinding
import com.buap.stu.buapstu.models.Alumno
import com.buap.stu.buapstu.models.Boleto
import com.buap.stu.buapstu.models.Ruta
import com.buap.stu.buapstu.presentation.AuthViewModel
import com.buap.stu.buapstu.presentation.DatabaseViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class ReservaActivity : AppCompatActivity() {

    private var _binding: ActivityReservaBinding? = null
    private val binding: ActivityReservaBinding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private val databaseViewModel: DatabaseViewModel by viewModels()
    private val currentBoleto = Boleto()
    private lateinit var pickerDate: MaterialDatePicker<Long>

    companion object {
        private const val KEY_DATE_PICKER = "KEY_DATE_PICKER"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityReservaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createMaterialDialogPicker()
        initButtonDateClick()
        initFlows()
//        parametros = this.intent.extras
//        usuario = parametros!!.getSerializable("usuario") as Alumno?
//        super.onCreate(savedInstanceState)
//        _binding = ActivityReservaBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        crearListaRutas()
//        val d = Date()
//        val s = DateFormat.format("dd/MM/yyyy ", d.time)
//        listViewRutas = findViewById<View>(R.id.list_route) as ListView
//        listViewHorarios = findViewById<View>(R.id.list_hours) as ListView
//        textCreditos = findViewById<View>(R.id.credits_avariable) as TextView
//        ruta_elegida = findViewById<View>(R.id.RutaElegida) as TextView
//        horaio_elegido = findViewById<View>(R.id.horarioSelect) as TextView
//        fecha = findViewById<View>(R.id.text_date) as TextView
//        instHorario = findViewById<View>(R.id.intHorario) as TextView
//        instRuta = findViewById<View>(R.id.intRuta) as TextView
//        text_Costo = findViewById<View>(R.id.text_costo) as TextView
//        get_fecha = findViewById<View>(R.id.button_Select_date) as Button
//        realizar_compra = findViewById<View>(R.id.button_check_out) as Button
//        textCreditos!!.text = "Creditos: " + usuario!!.creditos
//        get_fecha!!.setOnClickListener {
//            val cal = Calendar.getInstance()
//            val anio = cal[Calendar.YEAR]
//            val mes = cal[Calendar.MONTH]
//            val dia = cal[Calendar.DAY_OF_MONTH]
//            val dpd =
//                DatePickerDialog(this@ReservaActivity, { datePicker, year, month, dayOfMonth ->
//                    var month = month
//                    month = month + 1
//                    val fechaReserva = "$dayOfMonth/$month/$year"
//                    val partesFecha = s.toString().split("/").toTypedArray()
//                    if (partesFecha[0].toInt() < dayOfMonth && partesFecha[1].toInt() <= month && 2022 <= year) {
//                        fecha!!.text = fechaReserva
//                        fecha!!.textSize = 20f
//                        fecha!!.setTextColor(Color.WHITE)
//                        get_fecha!!.visibility = View.GONE
//                        boleto.fecha = fechaReserva
//                        boleto.costo = 4
//                        text_Costo!!.visibility = View.VISIBLE
//                        realizar_compra!!.visibility = View.VISIBLE
//                    } else {
//                        fecha!!.text = "La fecha debe ser posterior al dia de hoy"
//                        fecha!!.textSize = 20f
//                        fecha!!.setTextColor(Color.RED)
//                    }
//                }, anio, mes, dia)
//            dpd.show()
//        }
//        realizar_compra!!.setOnClickListener { guardarBoletoDB() }
    }

    private fun initFlows()= with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                lifecycleScope.launch {
                    authViewModel.userState.collect{userState->
                        (userState as? AuthState.Authenticated)?.let {authState->
                            (userState.user as? Alumno)?.let {
                                textCreditsAvariable.text = "Creditos: ${it.creditos}"
                            }
                        }
                    }
                }
                lifecycleScope.launch {
                    databaseViewModel.listRoute.collect{
                        when(it){
                            Resource.Failure -> Unit
                            Resource.Loading -> progressRoutes.visibility = View.VISIBLE
                            is Resource.Success -> {
                                progressRoutes.visibility = View.GONE
                                createListRoutes(it.data)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initText() {
        val timeNow = DateFormat.format("dd/MM/yyyy ", Date().time)
        binding.textDate.text = timeNow
    }

    private fun initButtonDateClick() = with(binding) {
        buttonSelectDate.setOnClickListener {
            if (!pickerDate.isAdded) pickerDate.show(supportFragmentManager, KEY_DATE_PICKER)
        }
    }

    private fun createMaterialDialogPicker() {
        if (!this::pickerDate.isInitialized) {
            this.pickerDate = MaterialDatePicker.Builder.datePicker().setTitleText("Select date")
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now()).build()
                ).build().apply {
                    addOnPositiveButtonClickListener(::listenerSelectDate)
                }
        }
    }

    private fun listenerSelectDate(time: Long) {
        Timber.d("time ${time.utcToFormat("dd/MM/yyyy")}")
        currentBoleto.fecha=time.utcToFormat("dd/MM/yyyy")
        currentBoleto.costo=4
    }


//    private fun guardarBoletoDB() {
//        mDatabase = FirebaseDatabase.getInstance().reference
//        mDatabase!!.child("Usuarios").child(usuario!!.uid).child("Boletos").push().setValue(boleto)
//        val intent = Intent(this@ReservaActivity, PrincipalActivity::class.java)
//        intent.putExtras(parametros!!)
//        startActivity(intent)
//    }
//
//    private fun createListViewRutas() {
//        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, direcionesRutas)
//        listViewRutas!!.adapter = adapter
//        listViewRutas!!.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
//            Toast.makeText(this@ReservaActivity, rutas[i]!!.punto_inicial, Toast.LENGTH_SHORT)
//                .show()
//            listViewRutas!!.visibility = View.GONE
//            horarios.clear()
//            horarioCompleto.clear()
//            ruta_elegida!!.text = direcionesRutas[i]
//            boleto.ruta = direcionesRutas[i]
//            instRuta!!.visibility = View.GONE
//            if (rutas[i]!!.punto_inicial != "CU") {
//                crearListHorario("HorarioDirrecionCU")
//            } else {
//                crearListHorario("HorarioDesdeCU")
//            }
//        }
//    }

//    private fun crearListHorario(children: String) {
//        val mDatabase = FirebaseDatabase.getInstance().reference
//        mDatabase.child(children).get().addOnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.e("firebase", "Error getting data", task.exception)
//            } else {
//                for (HORARIO in task.result.children) {
//                    val horario = HORARIO.getValue(Horario::class.java)
//                    horarios.add(horario)
//                    assert(horario != null)
//                    horarioCompleto.add("Hora de Salida: " + horario!!.horaSalida + ", Hora de llegada: " + horario.horaAproxLlegada)
//                }
//            }
//            createListViewHorarios()
//        }
//    }
//
//    private fun initFlows() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                launch {
//                    databaseViewModel.listRoute.collect {
//
//                    }
//                }
//            }
//        }
//    }
//
    private fun createListRoutes(listRoute: List<Ruta>) = with(binding) {
        val listTextShow = listRoute.map { ruta -> ruta.punto_inicial + " - " + ruta.punto_final }
        listRoutes.adapter =
            ArrayAdapter(this@ReservaActivity, R.layout.simple_list_item_1, listTextShow)
        listRoutes.setOnItemClickListener { _, _, position, _ ->
            listRoutes.visibility = View.GONE
            horarioSelect.text = listTextShow[position]
            buttonSelectDate.visibility = View.VISIBLE
            intHorario.visibility = View.GONE
            currentBoleto.horario = listTextShow[position]
        }
    }


}