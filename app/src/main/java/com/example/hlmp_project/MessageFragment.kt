package com.example.hlmp_project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.hlmp_project.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 예시: 버튼 클릭 시 Toast 메시지 전송
       /* binding.buttonSend.setOnClickListener {
            sendMessage()
        }*/
    }

    /*private fun sendMessage() {
        val message = binding.editTextMessage.text.toString().trim()

        if (message.isNotEmpty()) {
            binding.textViewMessage.text = message
        } else {
            Toast.makeText(requireContext(), "메시지를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }

    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

