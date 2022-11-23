package com.buap.stu.buapstu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.buap.stu.buapstu.core.states.AuthState
import com.buap.stu.buapstu.core.utils.launchActivity
import com.buap.stu.buapstu.databinding.ActivitySelectOptionAuthBinding
import com.buap.stu.buapstu.models.Alumno
import com.buap.stu.buapstu.models.Conductor
import com.buap.stu.buapstu.presentation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SelectOptionAuthActivity : AppCompatActivity() {

    private var _binding: ActivitySelectOptionAuthBinding? = null
    private val binding: ActivitySelectOptionAuthBinding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySelectOptionAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
        initFlows()
    }

    private fun initButtons() = with(binding) {
        buttonInitSesion.setOnClickListener {
            launchActivity(
                activity = LoginActivity::class.java,
                clearStack = false,
                finishActivity = false
            )
        }
        buttonCreateAccount.setOnClickListener {
            launchActivity(
                activity =RegistroActivity::class.java,
                clearStack = false,
                finishActivity = false
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initFlows() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    authViewModel.userState.collect { state ->
                        when (state) {
                            is AuthState.Authenticated -> {
                                when (state.user) {
                                    is Alumno -> {

                                        launchActivity(
                                            PrincipalActivity::class.java,
                                            clearStack = true,
                                            finishActivity = true
                                        )
                                    }
                                    is Conductor -> {
                                        launchActivity(
                                            ConductorActivity::class.java,
                                            clearStack = true,
                                            finishActivity = true
                                        )
                                    }
                                    else -> Timber.d("Error type")
                                }
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}