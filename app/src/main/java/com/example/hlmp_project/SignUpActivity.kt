package com.example.hlmp_project

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hlmp_project.databinding.ActivitySignBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignBinding
    private lateinit var auth: FirebaseAuth
    private val db: FirebaseFirestore = Firebase.firestore // 데이터베이스 객체
    private val itemsCollectionRef = db.collection("users") //콜렉션 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater) //뷰 바인딩
        setContentView(binding.root) //뷰 바인딩
        auth = Firebase.auth

        val toolbar = binding.toolbar5
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.signBtn.setOnClickListener {
            signUp(binding.idEditText.text.toString(), binding.pwEditText.text.toString())
            addInfo()
        }
    }

    private fun addInfo() {
        var idText = binding.idEditText.text.toString()
        var pwText = binding.pwEditText.text.toString()
        var nameText = binding.nameEditText.text.toString()
        var birthText = binding.birthEditText.text.toString()

        val itemMap = hashMapOf(
            "email" to idText,
            "pw" to pwText,
            "name" to nameText,
            "birth" to birthText,
        )
        itemsCollectionRef.add(itemMap)
    }

    private fun signUp(userEmail: String, password: String) {
        auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    //binding.idEditText.text.clear()
                    //binding.pwEditText.text.clear()
                    //binding.nameEditText.text.clear()
                    //binding.birthEditText.text.clear()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    println("회원가입실패!")
                }
            }

    }
}

