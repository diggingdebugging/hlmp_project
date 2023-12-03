package com.example.hlmp_project

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.hlmp_project.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        println("ProductActivity")

        setContentView(binding.root)
        val product = intent.getSerializableExtra("product", Product::class.java)
        binding.textTitle.text = product?.title.toString()
        binding.textSeller.text = product?.seller.toString()
        binding.textDetail.text = product?.detail.toString()
        binding.textPrice.text = product?.price.toString()

        binding.msgBtn.setOnClickListener {

        }
    }
}

