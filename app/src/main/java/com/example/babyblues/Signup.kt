package com.example.babyblues

import android.app.Activity
import android.content.Intent
import android.icu.text.Transliterator
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.babyblues.databinding.FragmentSignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class Signup : Fragment() {
    private lateinit var binding : FragmentSignupBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_signup, container, false)
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth= Firebase.auth

        binding.signup.setOnClickListener {
            var name = binding.name.text.toString()
            var email = binding.email.text.toString()
            var passcode = binding.passcode.text.toString()
            var phone = binding.phonenumber.text.toString()
            if (name.isNotBlank()&&email.isNotBlank()&&passcode.isNotBlank()&&phone.isNotBlank()){
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.toString(),passcode).addOnSuccessListener {
                        database = FirebaseDatabase.getInstance().getReference("Users")
                        val User = userdetails(name, email, passcode , phone )
                        val userid = FirebaseAuth.getInstance().currentUser?.uid.toString()
                        database.child(userid).setValue(User).addOnSuccessListener {
                            val intent = Intent(activity,StartQuiz::class.java)
                            startActivity(intent)
                            activity?.finish()
                        }.addOnFailureListener {
                            Toast.makeText(activity,"ERROR",Toast.LENGTH_SHORT).show()
                        }
                    }
                        .addOnFailureListener {
                            Toast.makeText(activity,"ERROR",Toast.LENGTH_SHORT).show()
                        }
                }
                else{
                    Toast.makeText(activity,"Enter Correct Mail", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(activity,"Fill All Out", Toast.LENGTH_LONG).show()
            }
        }


    }

}