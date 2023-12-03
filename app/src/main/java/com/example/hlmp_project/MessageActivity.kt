package com.example.hlmp_project

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.hlmp_project.databinding.ActivityMessageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    private val db: FirebaseFirestore = Firebase.firestore // 데이터베이스 객체
    private val itemsCollectionRef = db.collection("messages") //콜렉션 객체
    private var myAdapter: MyAdapter? = null
    lateinit var products : ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val text = binding.text

    }

}


