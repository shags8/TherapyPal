package com.example.babyblues

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        val quote = findViewById<AppCompatButton>(R.id.quote)
        quote.setOnClickListener {
            val intent = Intent(this,QUOTE::class.java)
            startActivity(intent)
        }

        val viewPager: ViewPager2 = findViewById(R.id.frame)
        val tabLayout: TabLayout = findViewById(R.id.tabs)
        viewPager.adapter = tabAdpater(this)
        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                val tabNames = listOf("Log in", "Sign up")
                tab.text = tabNames[position]
            }).attach()


    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).setTitle("Exit")
            .setMessage("Are you sure?")
            .setPositiveButton("yes", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }).setNegativeButton("no", null).show()
    }


}