package com.example.babyblues

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babyblues.databinding.ActivityOcdquizBinding
import com.example.babyblues.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OCDquiz : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var questionlist: ArrayList<Questions> = arrayListOf()
    private lateinit var binding: ActivityOcdquizBinding
    private var value = 1
    var useranswers : ArrayList<Int> = arrayListOf()
    private val currentUser = FirebaseAuth.getInstance().currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_ocdquiz)
        val binding = ActivityOcdquizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var finalquestions: ArrayList<Questions> = arrayListOf()
        binding.button2.isGone = true
        var que1 = Questions(
            "Do you prefer things organised over messy?", "No", "Yes",)
        var que2 = Questions(
            "Are you an overthinker?", "No", "Yes", )
        var que3 = Questions(
            "When you are anxious , do you oragnise things a lot?", "No", "Yes",)
        var que4 = Questions(
            "Do you feel insecure ?", "No", "Yes", )
        var que5 = Questions(
            "Are you worried about losing somethings valuable?", "No", "Yes",)
        var que6 = Questions(
            "Are you worried about contamination?", "No", "Yes")


        finalquestions.add(que1)
        finalquestions.add(que2)
        finalquestions.add(que3)
        finalquestions.add(que4)
        finalquestions.add(que5)
        finalquestions.add(que6)
        binding.question.text = que1.questions
        val optionadapter = OptionsAdapter(que1,2)
        binding.options.layoutManager = LinearLayoutManager(this)
        binding.options.adapter = optionadapter
        binding.options.hasFixedSize()
        var userid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        var path = buildString {
            append("Users/")
            append(userid)
            append("/Answers")
        }

        binding.button.setOnClickListener {
            database = FirebaseDatabase.getInstance().reference
            useranswers.add(selectedCardViews[0])
            database.child(path).setValue(useranswers)
            var que = finalquestions[value]
            binding.question.text = que.questions
            val optionadapter = OptionsAdapter(que,2)
            binding.options.layoutManager = LinearLayoutManager(this)
            binding.options.adapter = optionadapter
            binding.options.hasFixedSize()
            selectedCardViews = mutableListOf()
            value += 1
            if (value == 6) {
                binding.button.isGone = true
                binding.button2.isGone = false
            }
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this,Finish::class.java)
            useranswers.add(selectedCardViews[0])
            database.child(path).setValue(useranswers)
            startActivity(intent)
            finish()
        }


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