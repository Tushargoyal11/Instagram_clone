package com.example.insta

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.insta.databinding.ActivitySignUpScreenBinding
import com.example.insta.models.User
import com.example.insta.utils.USER_NODE
import com.example.insta.utils.USER_PROFILE_FOLDER
import com.example.insta.utils.uploadImage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class SignUpScreen : AppCompatActivity() {

    val binding by lazy{
        ActivitySignUpScreenBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher=registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let{
            uploadImage(uri, USER_PROFILE_FOLDER){
                if(it==null){

                }
                else{
                    user.image=it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val text="<font color=#FF000000>Already have an account?</font> <font color=#099BE9> Login</font>"
        binding.Login.setText(Html.fromHtml(text))
        user=User()
        if(intent.hasExtra("MODE")){
            if(intent.getIntExtra("MODE",-1)==1){

                binding.signupbtn.text="Update Profile"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        it->
                        user=it.toObject<User>()!!
                        if(!user.image.isNullOrEmpty()){
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }
                        binding.signupname.editText?.setText(user.name)
                        binding.signupemail.editText?.setText(user.email)
                        binding.signuppassword.editText?.setText(user.password)

                    }
            }
        }



        binding.signupbtn.setOnClickListener{
            if(intent.hasExtra("MODE")){
                if(intent.getIntExtra("MODE",-1)==1){
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            startActivity(Intent(this@SignUpScreen, HomeActivity::class.java))
                            finish()
                        }
                }
            }
            else
            {
            if(binding.signupname.editText?.text.toString().equals("") or
                binding.signupemail.editText?.text.toString().equals("") or
                binding.signuppassword.editText?.text.toString().equals("")
                ) {
                    Toast.makeText(this@SignUpScreen,"Please fill all required fields", Toast.LENGTH_SHORT).show()
            }
            else
            {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.signupemail.editText?.text.toString(),
                    binding.signuppassword.editText?.text.toString()
                ).addOnCompleteListener{
                    result->
                    if(result.isSuccessful){
                        user.name=binding.signupname.editText?.text.toString()
                        user.email=binding.signupemail.editText?.text.toString()
                        user.password=binding.signuppassword.editText?.text.toString()
                        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {
                        startActivity(Intent(this@SignUpScreen,HomeActivity::class.java))
                        finish()
                        }
                    }
                    else{
                        Toast.makeText(this@SignUpScreen, result.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }}
        binding.plus.setOnClickListener{
            launcher.launch("image/*")
        }
        binding.Login.setOnClickListener{
            startActivity(Intent(this@SignUpScreen,LoginActivity::class.java))
            finish()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}