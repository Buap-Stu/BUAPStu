package com.buap.stu.buapstu

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import android.annotation.SuppressLint
import android.os.Bundle
import com.buap.stu.buapstu.R
import com.google.firebase.database.FirebaseDatabase
import android.view.Gravity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import android.content.Intent
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.buap.stu.buapstu.LoginActivity
import com.buap.stu.buapstu.databinding.ActivityRegistroBinding
import com.buap.stu.buapstu.databinding.ActivityReservaBinding
import com.buap.stu.buapstu.models.Alumno
import com.buap.stu.buapstu.models.Conductor
import com.buap.stu.buapstu.presentation.AuthViewModel
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.HashMap

@AndroidEntryPoint
class RegistroActivity() : AppCompatActivity() {


    private var _binding:ActivityRegistroBinding?= null
    private val binding get() = _binding!!

    private val authViewModel:AuthViewModel by viewModels()

    private var type=""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
        initFlows()
    }

    private fun initFlows() = with(binding){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch{
                    authViewModel.isLoading.collect{
                        if(type.isNotEmpty()){
                            if( it){
                                btnRegistro2.visibility=View.GONE
                                progressSignUp.visibility=View.VISIBLE
                            }else{
                                btnRegistro2.visibility=View.VISIBLE
                                progressSignUp.visibility=View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setOnClickListeners()= with(binding){
        imageButtonDriver.setOnClickListener {
            textMessageRegistro.text = "A elegido el usuario tipo:"
            fromContainerDriver.visibility = View.VISIBLE
            StudentContainer.visibility = View.GONE
            LLImaButtons.gravity = Gravity.CENTER
            type="Conductor"
            btnRegistro2.visibility=View.VISIBLE
        }

        imageButtonStudent.setOnClickListener {
            textMessageRegistro.text = "A elegido el usuario tipo:"
            fromContainerAlumno.visibility = View.VISIBLE
            LLImaButtons.gravity = Gravity.CENTER
            DriverContainer.visibility = View.GONE
            LLContBTNRegistro.visibility = View.VISIBLE
            type="Alumno"
            btnRegistro2.visibility=View.VISIBLE
        }


        btnRegistro2.setOnClickListener {
            if(type=="Alumno"){
                val name = ANombreCompleto.text.toString()
                val mat = AMAtricula.text.toString()
                val mail = AEmail.text.toString()
                val password = APassword.text.toString()
                validateStudents(name, mail, password, mat)
            }else{
                val name2 = DNombreCompleto.text.toString()
                val af = DNumeroAfiliacion.text.toString()
                val mail2 = DCorreo.text.toString()
                val password2 = DPassword.text.toString()
                validateDriver(name2, mail2, password2, af)
            }


        }
    }

    private fun validateDriver(
        name2: String,
        mail2: String,
        password2: String,
        af: String
    ) {
        if (name2.isNotEmpty() && mail2.isNotEmpty() && password2.isNotEmpty() && af.isNotEmpty()) {
            if (name2.length > 30 || name2.length < 3) {
                Toast.makeText(
                    this@RegistroActivity,
                    "Introduzca un nombre valido",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (af.length < 9 || af.length > 9) {
                    Toast.makeText(
                        this@RegistroActivity,
                        "Introduzca un numero de afiliacion valido",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (!mail2.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                        Toast.makeText(
                            this@RegistroActivity,
                            "Introduzca un correo valido",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (password2.length < 6) {
                            Toast.makeText(
                                this@RegistroActivity,
                                "La contraseña debe tener almenos 6 caracteres",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            authViewModel.signUp(
                                newUser = Conductor(
                                    contrasena = password2,
                                    correo = mail2,
                                    nombre_completo = name2,
                                    numero_afiliacion = af,
                                    type = "Conductor"
                                ),
                                callBackSuccess = ::finish
                            )
                        }
                    }
                }
            }
        } else {
            Toast.makeText(
                this@RegistroActivity,
                "Debe llenar todos los campos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateStudents(
        name: String,
        mail: String,
        password: String,
        mat: String
    ) {
        if (name.isNotEmpty() && mail.isNotEmpty() && password.isNotEmpty() && mat.isNotEmpty()) {
            if (name.length > 100 || name.length < 3) {
                Toast.makeText(
                    this@RegistroActivity,
                    "Introduzca un nombre valido",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (mat.length < 9 || mat.length > 9) {
                    Toast.makeText(
                        this@RegistroActivity,
                        "Introduzca una matricula valida",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (!mail.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))) {
                        Toast.makeText(
                            this@RegistroActivity,
                            "Introduzca un correo valido",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (password.length < 6) {
                            Toast.makeText(
                                this@RegistroActivity,
                                "La contraseña debe tener almenos 6 caracteres",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            authViewModel.signUp(
                                newUser = Alumno(
                                    contrasena = password,
                                    correo = mail,
                                    nombre_completo = name,
                                    matricula = mat,
                                    type = "Alumno"
                                ),
                                callBackSuccess = ::finish
                            )
                        }
                    }
                }
            }
        } else {
            Toast.makeText(
                this@RegistroActivity,
                "Debe llenar todos los campos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}