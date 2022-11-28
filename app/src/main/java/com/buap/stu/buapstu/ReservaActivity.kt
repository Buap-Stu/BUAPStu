package com.buap.stu.buapstu

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.buap.stu.buapstu.core.states.AuthState
import com.buap.stu.buapstu.core.utils.Resource
import com.buap.stu.buapstu.core.utils.isStudentAuth
import com.buap.stu.buapstu.core.utils.showToast
import com.buap.stu.buapstu.core.utils.utcToFormat
import com.buap.stu.buapstu.databinding.ActivityReservaBinding
import com.buap.stu.buapstu.models.Alumno
import com.buap.stu.buapstu.models.Boleto
import com.buap.stu.buapstu.models.Horario
import com.buap.stu.buapstu.models.Ruta
import com.buap.stu.buapstu.presentation.AuthViewModel
import com.buap.stu.buapstu.presentation.DatabaseViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import com.buap.stu.buapstu.R

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
        initButtonsClicks()
        initFlows()
    }

    private fun initFlows()= with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                lifecycleScope.launch {
                    authViewModel.userState.collect { userState ->
                        userState.isStudentAuth {
                            textCreditsAvariable.text = "Creditos: ${it.creditos}"
                        }
                    }
                }
                lifecycleScope.launch {
                    databaseViewModel.listRoute.collect{
                        when (it) {
                            Resource.Failure -> Unit
                            Resource.Loading -> progressRoutes.visibility = View.VISIBLE
                            is Resource.Success -> {
                                progressRoutes.visibility = View.GONE
                                createListRoutes(it.data)
                            }
                        }
                    }
                }

                lifecycleScope.launch {
                    databaseViewModel.listHours.collect {
                        when (it) {
                            Resource.Failure -> Unit
                            Resource.Loading -> progressHours.visibility = View.VISIBLE
                            is Resource.Success -> {
                                progressHours.visibility = View.GONE
                                createListHours(it.data)
                            }
                        }
                    }
                }

                lifecycleScope.launch {
                    databaseViewModel.isProcess.collect{
                        if(currentBoleto.horario.isNotEmpty()){
                            if(it){
                                buttonCheckOut.visibility = View.GONE
                                progressCheckOut.visibility = View.VISIBLE
                            }else{
                                buttonCheckOut.visibility = View.VISIBLE
                                progressCheckOut.visibility = View.GONE
                            }
                        }

                    }
                }

                lifecycleScope.launch {
                    databaseViewModel.messageDatabase.collect{
                        showToast(it)
                    }
                }

            }
        }
    }

    private fun createListHours(listHoursRoutes: List<Horario>) = with(binding) {
        val listHoursShow =
            listHoursRoutes.map { "Hora de Salida: " + it.horaSalida + ", Hora de llegada: " + it.horaAproxLlegada }
        val adapter = ArrayAdapter(this@ReservaActivity, android.R.layout.simple_list_item_1, listHoursShow)
        listHours.adapter = adapter
        listHours.setOnItemClickListener { parent, view, position, id ->
            textHourSelect.text = listHoursShow[position]
            currentBoleto.horario = listHoursShow[position]
            buttonSelectDate.visibility = View.VISIBLE
            containerSelectHours.visibility = View.GONE
        }
    }


    private fun initButtonsClicks() = with(binding) {
        buttonSelectDate.setOnClickListener {
            if (!pickerDate.isAdded) pickerDate.show(supportFragmentManager, KEY_DATE_PICKER)
        }
        buttonCheckOut.setOnClickListener {
            databaseViewModel.addNewBoleto(currentBoleto){
                finish()
            }
        }
    }

    private fun createMaterialDialogPicker() {
        if (!this::pickerDate.isInitialized) {
            this.pickerDate = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.now()).build()
                ).build().apply {
                    addOnPositiveButtonClickListener(::listenerSelectDate)
                }
        }
    }

    private fun listenerSelectDate(time: Long) = with(binding) {
        Timber.d("time ${time.utcToFormat("dd/MM/yyyy")}")
        currentBoleto.fecha = time.utcToFormat("dd/MM/yyyy")
        textDate.text = time.utcToFormat("dd/MM/yyyy")
        textDate.visibility = View.VISIBLE
        textCosto.visibility = View.VISIBLE
        buttonSelectDate.visibility = View.GONE
        buttonCheckOut.visibility = View.VISIBLE
    }

    private fun createListRoutes(listRoute: List<Ruta>) = with(binding) {
        val listTextShow = listRoute.map { ruta -> ruta.punto_inicial + " - " + ruta.punto_final }
        listRoutes.adapter =
            ArrayAdapter(this@ReservaActivity, android.R.layout.simple_list_item_1, listTextShow)
        listRoutes.setOnItemClickListener { _, _, position, _ ->

            currentBoleto.horario = listTextShow[position]
            textSelectRoute.text = listTextShow[position]

            // * hidden interfaces


            containerSelectHours.visibility = View.VISIBLE
            containerSelectRoute.visibility = View.GONE

            // * init request in view model
            if (listRoute[position].punto_inicial == "CU") {
                databaseViewModel.requestHours("HorarioDirrecionCU")
            } else {
                databaseViewModel.requestHours("HorarioDesdeCU")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}