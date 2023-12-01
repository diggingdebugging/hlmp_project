package com.example.hlmp_project

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hlmp_project.databinding.ActivityRegisterBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity(){
    private lateinit var binding: ActivityRegisterBinding // viewBinding사용
    private val db: FirebaseFirestore = Firebase.firestore
    private val itemsCollectionRef = db.collection("items")
    private var myAdapter: MyAdapter? = null
    private lateinit var products : ArrayList<Product>
    private val defaultImg = "gs://hlmp-project.appspot.com/default_img.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar에 뒤로가기버튼 넣어주기
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //글등록버튼 리스너
        binding.addBtn.setOnClickListener {
            addItem()
            finish()
        }
    }

    private fun addItem(){
        var title = binding.addTitle.text.toString()
        var seller = binding.addSeller.text.toString()
        var price= binding.addPrice.text.toString().toIntOrNull()
        var detail = binding.addDetail.text.toString()
        var email = UserInformation.getInstance().getUserEmail()



        val itemMap = hashMapOf(
          "title" to title,
            "seller" to seller,
            "price" to price,
            "detail" to detail,
            "imageUrl" to defaultImg, // 이미지 추가기능 없음, 기본 이미지 사용
            "email" to email,
            "status" to true
      )

        itemsCollectionRef.add(itemMap)
            .addOnSuccessListener { updateList() }.addOnFailureListener {  }
    }

    private fun updateList() {
        itemsCollectionRef.get().addOnSuccessListener { // 콜렉션에서 document 리스트 리턴
            products = arrayListOf()
            for (doc in it) { //document를 하나하나 리턴받음
                products.add(Product(doc)) //products에 추가
            }
            myAdapter?.updateList(products)
            println("아이템 개수${myAdapter?.itemCount}")
        }
    }

}