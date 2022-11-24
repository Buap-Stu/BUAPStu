package com.buap.stu.buapstu

import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.buap.stu.buapstu.R
import com.buap.stu.buapstu.databinding.ActivityConductorBinding
import com.buap.stu.buapstu.presentation.AuthViewModel

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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}