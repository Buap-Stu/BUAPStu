package com.buap.stu.buapstu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.buap.stu.buapstu.core.states.AuthState
import com.buap.stu.buapstu.core.utils.launchActivity
import com.buap.stu.buapstu.core.utils.showToast
import com.buap.stu.buapstu.databinding.ActivityPrincipalBinding
import com.buap.stu.buapstu.models.Alumno
import com.buap.stu.buapstu.presentation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PrincipalActivity : AppCompatActivity() {

    private var _binding: ActivityPrincipalBinding? = null
    private val binding: ActivityPrincipalBinding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
        initFlows()
    }

    private fun initFlows() = with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                lifecycleScope.launch {
                    authViewModel.userState.collect { state ->
                        (state as? AuthState.Authenticated)?.let { user ->
                            (user.user as? Alumno)?.let { user ->
                                textCreditsStudents.text = "${user.creditos}"
                            }
                        }
                    }
                }
            }
        }
    }


    private fun initButtons() = with(binding) {
        buttonLogOut.setOnClickListener {
            authViewModel.signOut()
            showToast("Ha cerrado sesion")
            launchActivity(
                activity = LoginActivity::class.java,
                clearStack = true,
                finishActivity = true
            )
        }

        buttonsAddCredits.setOnClickListener {
            authViewModel.addCredits()
        }

        buttonSaveTravel.setOnClickListener {
            launchActivity(
                activity = ReservaActivity::class.java,
                finishActivity = false,
                clearStack = false
            )
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}