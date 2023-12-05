package com.example.hlmp_project

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.hlmp_project.databinding.ActivityMessageBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    private val db: FirebaseFirestore = Firebase.firestore // 데이터베이스 객체
    private val messagesCollectionRef = db.collection("messages") //콜렉션 객체
    private var myAdapter: MyAdapter? = null
    private lateinit var products : ArrayList<Product>

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getSerializableExtra("product", Product::class.java)

        binding.textViewTitle.text = "Message Activity"
        binding.textViewMessage.text = "This is a message."

        binding.buttonMessage.setOnClickListener {
            var message = binding.messageText.text.toString()
            var sellerUid = product?.uid
            var uid =Firebase.auth.currentUser?.uid

            val itemMap = hashMapOf(
                "title" to title,
                "sellerUid" to sellerUid,
                "uid" to uid,
            )

            messagesCollectionRef.add(itemMap)
                .addOnSuccessListener { updateList() }.addOnFailureListener {  }
        }
    }

    private fun updateList() {
        messagesCollectionRef.get().addOnSuccessListener { // 콜렉션에서 document 리스트 리턴
            products = arrayListOf()
            for (doc in it) { //document를 하나하나 리턴받음
                products.add(Product(doc)) //products에 추가
            }
            myAdapter?.updateList(products)
            println("아이템 개수${myAdapter?.itemCount}")
        }
    }

    fun sendChatMessage(senderId: String, message: String) {
        val docRef = db.collection("messages").document("")

        val updates = hashMapOf<String, Any>(
            "timestamp" to FieldValue.serverTimestamp(),
        )
        docRef.update(updates).addOnCompleteListener {

        }
    }

}
