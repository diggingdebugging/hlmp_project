package com.example.hlmp_project

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hlmp_project.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.model.Document
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding // viewBinding사용
    private lateinit var recyclerView: RecyclerView
    private var myAdapter: MyAdapter? = null
    private val db: FirebaseFirestore = Firebase.firestore // 데이터베이스 객체
    private val itemsCollectionRef = db.collection("items") //콜렉션 객체
    lateinit var products: ArrayList<Product>
    private var snapshotListener: ListenerRegistration? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        //floatingActionButton Listener ->  RegisterActivity 실행
        binding.fab.setOnClickListener {
            startActivity(Intent(requireActivity(), RegisterActivity::class.java))
        }

        //리사이클러뷰 설정
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext()) // 1차원 배열
        myAdapter = MyAdapter(requireContext(), emptyList()) //어댑터 초기화
        recyclerView.adapter = myAdapter
        recyclerView.setHasFixedSize(true)
        updateList()

        //툴바 메뉴의 필터
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.status -> querySell() //판매중인 목록보기
                R.id.priceOver -> queryOver() //만원초과
                R.id.priceUnder -> queryUnder() //만원이하
                else -> updateList() //전체리스트보기
            }
            true
        }

        snapshotListener = itemsCollectionRef.addSnapshotListener { snapshot, error -> //실시간 데이터변경알림
            updateList()
        }
        return binding.root
    }

    private fun updateList() {
        itemsCollectionRef.get()
            .addOnSuccessListener { // 콜렉션에서 document 리스트 리턴
                products = arrayListOf()
                for (doc in it) { //document를 하나하나 리턴받음
                    products.add(Product(doc)) //products에 추가
                }
                myAdapter?.updateList(products)
            }
    }

    private fun querySell() {
        val s = "판매중"
        itemsCollectionRef.whereEqualTo("status", true).get()
            .addOnSuccessListener {
                products = arrayListOf()
                for (doc in it) { //document를 하나하나 리턴받음
                    products.add(Product(doc)) //products에 추가
                }
                myAdapter?.updateList(products)
            }
    }

    private fun queryOver() { //만원초과
        val p = 10000
        itemsCollectionRef.whereGreaterThan("price", p).get()
            .addOnSuccessListener {
                products = arrayListOf()
                for (doc in it) { //document를 하나하나 리턴받음
                    products.add(Product(doc)) //products에 추가
                }
                myAdapter?.updateList(products)
            }
    }

    private fun queryUnder() { // 만원이하
        val p = 10000
        itemsCollectionRef.whereLessThanOrEqualTo("price", p).get()
            .addOnSuccessListener {
                products = arrayListOf()
                for (doc in it) { //document를 하나하나 리턴받음
                    products.add(Product(doc)) //products에 추가
                }
                myAdapter?.updateList(products)
            }
    }


}