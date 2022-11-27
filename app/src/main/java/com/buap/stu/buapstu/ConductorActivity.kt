package com.buap.stu.buapstu

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.buap.stu.buapstu.core.utils.launchActivity
import com.buap.stu.buapstu.databinding.ActivityConductorBinding
import com.buap.stu.buapstu.presentation.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConductorActivity : AppCompatActivity() {
    private var _binding: ActivityConductorBinding? = null
    private val binding: ActivityConductorBinding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityConductorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListeners()
    }

    private fun setOnClickListeners()=with(binding){
        buttonLogOut.setOnClickListener {
            authViewModel.signOut()
            launchActivity(
                activity = SelectOptionAuthActivity::class.java,
                clearStack = true,
                finishActivity = true
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}