package com.example.insta.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.insta.R
import com.example.insta.SignUpScreen
import com.example.insta.databinding.FragmentProfileBinding
import com.example.insta.models.User
import com.example.insta.utils.USER_NODE
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class profileFragment : Fragment() {

private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater, container, false)

        binding.editProfile.setOnClickListener{
            val intent= Intent(activity,SignUpScreen::class.java)
            intent.putExtra("MODE",1)
            activity?.startActivity(intent)
            activity?.finish()
        }


        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    it->

                    val user:User=it.toObject<User>()!!
                    binding.name.text=user.name
                    binding.bio.text=user.email
                    if(!user.image.isNullOrEmpty()){
                        Picasso.get().load(user.image).into(binding.profileImage)
                    }


                }
    }
}