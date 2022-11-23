package com.buap.stu.buapstu

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.buap.stu.buapstu.core.utils.launchActivity
import com.buap.stu.buapstu.core.utils.showToast
import com.buap.stu.buapstu.databinding.ActivityLoginBinding
import com.buap.stu.buapstu.presentation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private var _binding: ActivityLoginBinding? = null
    private val binding: ActivityLoginBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFlows()
        initButtons()
    }

    private fun initFlows() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    authViewModel.isLoading.collect { isAuth ->
                        if (isAuth) {
                            buttonLogin.visibility = View.GONE
                            progressLogin.visibility = View.VISIBLE
                        } else {
                            buttonLogin.visibility = View.VISIBLE
                            progressLogin.visibility = View.GONE
                        }
                    }
                }
                launch {
                    authViewModel.messageAuth.collect(::showToast)
                }
            }
        }
    }


    private fun initButtons() = with(binding) {
        buttonLogin.setOnClickListener {
            val email = textEmail.text.toString()
            val password = textPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.signInWithEmailAndPassword(email, password){
                    when(it){
                        "Alumno"->launchActivity(
                            activity = PrincipalActivity::class.java,
                            finishActivity = true,
                            clearStack = true
                        )
                        "Conductor"->launchActivity(
                            activity = ConductorActivity::class.java,
                            finishActivity = true,
                            clearStack = true
                        )
                    }
                }
            } else {
                showToast("Introduzca usuario y contraseña")
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}