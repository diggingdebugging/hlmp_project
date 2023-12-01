package com.example.hlmp_project

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.Serializable

data class Product(val id: String , var imageUrl : String, var title : String, var seller : String,
    var detail : String, var price : Int, var status : Boolean, var uid : String) : Serializable{
    constructor(doc: QueryDocumentSnapshot) :
            this(doc.id, doc["imageUrl"].toString(), doc["title"].toString(), doc["seller"].toString(),
                doc["detail"].toString(), doc["price"].toString().toIntOrNull() ?: 0,
                doc["status"].toString().toBoolean(), doc["uid"].toString())
}

interface OnItemClickListeners {
    fun onItemClick(position: Int) {}
}

class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
    private val context = view.context
    val titleImage : ShapeableImageView = view.findViewById<ShapeableImageView>(R.id.title_image)
    val titleInfo : TextView = view.findViewById<TextView>(R.id.tvHeading)
    var currentProduct : Product? = null
    private lateinit var auth: FirebaseAuth

    init { //한 개의 뷰를 클릭했을때
        view.setOnClickListener {
            auth = Firebase.auth
            val currentUser = auth.currentUser?.uid
            println(currentUser)
            if(currentUser == currentProduct?.uid){
                currentProduct?.let { product ->
                    val intent = Intent(context, ModifyActivity::class.java)
                    intent.putExtra("product", product as Serializable)
                    context.startActivity(intent)
                }
            }else{
                currentProduct?.let { product ->
                    val intent = Intent(context, ProductActivity::class.java)
                    intent.putExtra("product", product as Serializable)
                    context.startActivity(intent)
                }
            }

        }
    }

}

class MyAdapter(context: Context, private var productList : List<Product>) : RecyclerView.Adapter<MyViewHolder>() {
    private var storage : FirebaseStorage = Firebase.storage//스토리지
    private val storageRef = storage.reference // 루트에 대한 래퍼런스
    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { //뷰홀더 생성
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productList[position]
        val imageRef = storage.getReferenceFromUrl(currentItem.imageUrl)
        holder.titleInfo.text = currentItem.title
        holder.currentProduct = currentItem

        imageRef.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            holder.titleImage.setImageBitmap(bmp)
        }?.addOnFailureListener {

        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}