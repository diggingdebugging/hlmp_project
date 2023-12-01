package com.example.hlmp_project

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.hlmp_project.databinding.ActivityHomeBinding
import com.google.firebase.messaging.FirebaseMessaging

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding // viewBinding사용

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment()) //첫화면은 홈화면으로 설정


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)
        }*/

        /*FirebaseMessaging.getInstance().token.addOnCompleteListener { // it: Task<String!>
            textFCMToken.text = if (it.isSuccessful) it.result else "Token Error!"
        // copy FCM token to clipboard
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "FCM Token",
                textFCMToken.text
            ) clipboard.setPrimaryClip (clip)
// write to logcat
            Log.d(MyFirebaseMessagingService.TAG, "FCM token: ${textFCMToken.text}")
        }*/

        //바텀네비게이션바 리스너
        binding.bottomNavgationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.message -> replaceFragment(MessageFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) { //fragment를 동적으로 넣는 방식
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.frame_layout, fragment, null) // replace 교체방식 이용
            addToBackStack(null) //백버튼 이용안함
        }
        when (fragment) { // 바텀네비게이션바의 focus 맞춰주기
            is HomeFragment -> binding.bottomNavgationView.menu.findItem(R.id.home)?.isChecked =
                true

            is ProfileFragment -> binding.bottomNavgationView.menu.findItem(R.id.profile)?.isChecked =
                true

            is MessageFragment -> binding.bottomNavgationView.menu.findItem(R.id.message)?.isChecked =
                true
        }
    }

   /* private fun requestSinglePermission(permission: String) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return

        val requestPermLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it == false) { // permission is not granted!
                    AlertDialog.Builder(this).apply {
                        setTitle("Warning")
                        setMessage("notification permission required!")
                    }.show()
                }
            }

        if (shouldShowRequestPermissionRationale(permission)) {
            // you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage("notification permission required!")
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // should be called in onCreate()
            requestPermLauncher.launch(permission)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "firebase-messaging", "firebase-messaging channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "This is firebase-messaging channel."
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }*/
}