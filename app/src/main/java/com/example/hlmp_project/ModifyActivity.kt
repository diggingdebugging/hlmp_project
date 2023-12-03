package com.example.hlmp_project

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.hlmp_project.databinding.ActivityModifyBinding
import com.example.hlmp_project.databinding.ActivityProductBinding
import com.example.hlmp_project.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ModifyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityModifyBinding
    private val db: FirebaseFirestore = Firebase.firestore // 데이터베이스 객체
    private val itemsCollectionRef = db.collection("items") //콜렉션 객체
    private var myAdapter: MyAdapter? = null
    lateinit var products : ArrayList<Product>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val product = intent.getSerializableExtra("product", Product::class.java)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // editText에 기존 문서에 필드 불러오기
        binding.modifyTitle.setText(product?.title.toString())
        binding.modifySeller.setText(product?.seller.toString())
        binding.modifyDetail.setText(product?.detail.toString())
        binding.modifyPrice.setText(product?.price.toString())

        //modify 버튼을 누르면 글 수정
        var itemID = product?.id
        binding.modifyBtn.setOnClickListener {
            if (itemID != null) {
                modifyItem(itemID)
            }
            finish()
        }
   }

    private fun modifyItem(itemID: String){
        val updatedTitle = binding.modifyTitle.text.toString()
        val updatedSeller = binding.modifySeller.text.toString()
        val updatedDetail = binding.modifyDetail.text.toString()
        val updatedPrice = binding.modifyPrice.text.toString().toIntOrNull() ?: 0

        val updates: MutableMap<String, Any> = hashMapOf(
            "title" to updatedTitle,
            "seller" to updatedSeller,
            "detail" to updatedDetail,
            "price" to updatedPrice,
        )

        itemsCollectionRef.document(itemID).update(updates)
            .addOnSuccessListener { queryItem(itemID) }
    }

    private fun queryItem(itemID: String) {
        itemsCollectionRef.document(itemID).get().addOnSuccessListener { // it: DocumentSnapshot editID.setText(it.id)
            binding.modifyTitle.setText(it["title"].toString())
            binding.modifySeller.setText(it["seller"].toString())
            binding.modifyDetail.setText(it["detail"].toString())
            binding.modifyPrice.setText(it["price"].toString())
        }.addOnFailureListener {

        }
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

