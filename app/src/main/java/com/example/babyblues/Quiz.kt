package com.example.babyblues

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.babyblues.databinding.ActivityQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Quiz : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private var questionlist: ArrayList<Questions> = arrayListOf()
    private lateinit var binding: ActivityQuizBinding
    private var value = 1
    var useranswers : ArrayList<Int> = arrayListOf()
    private val currentUser = FirebaseAuth.getInstance().currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_quiz)
        val binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var finalquestions: ArrayList<Questions> = arrayListOf()
        binding.button2.isGone = true
        var que1 = Questions(
            "Attempted a suicide", "No", "Yes", "Not Interested To Tell"
        )
        var que2 = Questions(
            "Do You Have Feeling Of Guilt??", "No", "Yes", "Maybe"
        )
        var que3 = Questions(
            "Feeling irritable towards baby and partner", "No", "Yes", "Sometimes"
        )
        var que4 = Questions(
            "Feeling sad or tearful ?", "No", "Yes", "Sometimes"
        )
        var que5 = Questions(
            "Overeating or loss of apetite ?", "No", "Yes", "Not at all"
        )
        var que6 = Questions(
            "Problems concentrating or in decision making", "No", "Yes", "Often"
        )
        var que7 = Questions(
            "Problems of bonding with the baby", "No", "Yes", "Sometimes"
        )
        var que8 = Questions(
            "Trouble sleeping at night", "No", "Yes", "Two or more days a week"
        )

        finalquestions.add(que1)
        finalquestions.add(que2)
        finalquestions.add(que3)
        finalquestions.add(que4)
        finalquestions.add(que5)
        finalquestions.add(que6)
        finalquestions.add(que7)
        finalquestions.add(que8)

        binding.question.text = que1.questions
        val optionadapter = OptionsAdapter(que1,3)
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
                val optionadapter = OptionsAdapter(que,3)
                binding.options.layoutManager = LinearLayoutManager(this)
                binding.options.adapter = optionadapter
                binding.options.hasFixedSize()
                selectedCardViews = mutableListOf()
                value += 1
                if (value == 8) {
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