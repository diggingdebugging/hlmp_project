package com.example.hlmp_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hlmp_project.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySigninBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater) //뷰 바인딩
        setContentView(binding.root) //뷰 바인딩
        auth = Firebase.auth

        binding.signInBtn.setOnClickListener {
            singIn(binding.idText.text.toString(), binding.pwText.text.toString())
        }

        binding.signUpBtn.setOnClickListener {
            val intent = Intent(this ,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun singIn(userEmail: String, password: String) {
        auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        //메인화면으로 넘어감
                } else {
                    println("로그인실패!")
                }
            }
    }


}