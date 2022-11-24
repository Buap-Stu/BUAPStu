package com.buap.stu.buapstu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.buap.stu.buapstu.databinding.ActivityDetallesCompraBinding

class DetallesCompraActivity : AppCompatActivity() {

    private var _binding:ActivityDetallesCompraBinding?=null
    private val binding:ActivityDetallesCompraBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityDetallesCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
}