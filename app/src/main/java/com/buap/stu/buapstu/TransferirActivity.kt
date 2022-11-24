package com.buap.stu.buapstu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.buap.stu.buapstu.core.states.AuthState
import com.buap.stu.buapstu.core.utils.isStudentAuth
import com.buap.stu.buapstu.core.utils.showToast
import com.buap.stu.buapstu.databinding.ActivityTransferirBinding
import com.buap.stu.buapstu.models.Alumno
import com.buap.stu.buapstu.presentation.AuthViewModel
import com.buap.stu.buapstu.presentation.DatabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransferirActivity : AppCompatActivity() {

    private var _binding: ActivityTransferirBinding?=null
    private val binding:ActivityTransferirBinding get() = _binding!!

    private val databaseViewModel:DatabaseViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityTransferirBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()
        initFlows()
    }

    private fun initFlows()= with(binding) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    databaseViewModel.isProcess.collect{
                        if(it){
                            buttonTransferCredits.visibility=View.GONE
                            buttonCancel.visibility=View.GONE
                            progressTransfer.visibility=View.VISIBLE
                        }else{
                            buttonTransferCredits.visibility=View.VISIBLE
                            buttonCancel.visibility=View.VISIBLE
                            progressTransfer.visibility=View.GONE
                        }
                    }
                }

                launch {
                    databaseViewModel.messageDatabase.collect{
                        showToast(it)
                    }
                }

                launch {
                    authViewModel.userState.collect {
                        it.isStudentAuth{
                            textRestCredits.text = "Creditos restantes ${it.creditos}"
                        }
                    }
                }
            }
        }
    }

    private fun setOnClickListener()= with(binding){
            buttonCancel.setOnClickListener{
                finish()
            }
        buttonTransferCredits.setOnClickListener{
            val matricula=inputMatricula.text.toString()
            val credits=inputCreditos.text.toString().toIntOrNull()?:0
            if(matricula.isEmpty() || credits<=0 ){
                showToast("Verifique sus datos")
            }else{
                databaseViewModel.transferCredits(matricula, credits)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

