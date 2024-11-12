package com.example.insta

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.insta.databinding.ActivitySignUpScreenBinding
import com.example.insta.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class SignUpScreen : AppCompatActivity() {

    val binding by lazy{
        ActivitySignUpScreenBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let{

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        user=User()
//      setContentView(R.layout.activity_sign_up_screen)
        binding.signUpBtn.setOnClickListener{
            if(binding.signupname.editText?.text.toString().equals("") or
                binding.signupemail.editText?.text.toString().equals("") or
                binding.signuppassword.editText?.text.toString().equals("")
                ) {
                    Toast.makeText(this@SignUpScreen,"Please fill all required fields", Toast.LENGTH_SHORT).show()
            }
            else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.signupemail.editText?.text.toString(),
                    binding.signuppassword.editText?.text.toString()
                ).addOnCompleteListener{
                    result->
                    if(result.isSuccessful){
                        Toast.makeText(this@SignUpScreen,"Successfully Registered",Toast.LENGTH_SHORT).show()
                        user.name=binding.signupname.editText?.text.toString()
                        user.email=binding.signupemail.editText?.text.toString()
                        user.password=binding.signuppassword.editText?.text.toString()
                        Firebase.firestore.collection("Users").document(Firebase.auth.currentUser!!.uid).set(user)
                    }
                    else{
                        Toast.makeText(this@SignUpScreen, result.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}