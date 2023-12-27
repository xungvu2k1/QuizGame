package com.example.quizgame.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgame.R
import com.example.quizgame.viewmodel.AuthViewModel
import com.google.android.material.textfield.TextInputEditText


class SignInFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var navController: NavController
    private lateinit var editEmail: TextInputEditText
    private lateinit var editPass:TextInputEditText
    private lateinit var signUpText: TextView
    private lateinit var signInBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        editEmail = view.findViewById<TextInputEditText>(R.id.txt_emailin)
        editPass = view.findViewById<TextInputEditText>(R.id.txt_passwordin)
        signUpText = view.findViewById(R.id.btn_sign_upin)
        signInBtn = view.findViewById<Button>(R.id.btn_sign_in)

        signUpText.setOnClickListener{
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        signInBtn.setOnClickListener {
            val email = editEmail.getText().toString()
            val pass: String = editPass.getText().toString()
            if (!email.isEmpty() && !pass.isEmpty()) {
                viewModel.signIn(email, pass)
                Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
                viewModel.apply {
                    firebaseUserMutableLiveData.apply {
                        observe(viewLifecycleOwner) {
                            if (it != null) {
                                navController.navigate(R.id.action_signInFragment_to_loginFragment)
                            }
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Please Enter Email and Pass", Toast.LENGTH_SHORT).show()
            }
        }
    }

}