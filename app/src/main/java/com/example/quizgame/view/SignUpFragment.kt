package com.example.quizgame.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.quizgame.R
import com.example.quizgame.viewmodel.AuthViewModel
import com.google.android.material.textfield.TextInputEditText

class SignUpFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var navController: NavController
    private lateinit var editEmail: TextInputEditText
    private lateinit var editPass: TextInputEditText
    private lateinit var signUpBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
//        ).get<AuthViewModel>(
//            AuthViewModel::class.java
//        )
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController(view)
        editEmail = view.findViewById(R.id.txt_email)
        editPass = view.findViewById(R.id.txt_password)
        signUpBtn = view.findViewById(R.id.btn_sign_up)

        signUpBtn.setOnClickListener {
            val email = editEmail.text.toString().trim()
            val pass: String = editPass.text.toString().trim()
            if (!email.isEmpty() && !pass.isEmpty()) {
                viewModel.signUp(email, pass)
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                viewModel.firebaseUserMutableLiveData.observe(viewLifecycleOwner) {
                    navController?.navigate(R.id.action_signUpFragment_to_signInFragment)
                }
            } else {
                Toast.makeText(context, "Please Enter Email and Pass", Toast.LENGTH_SHORT).show()
            }
        }
    }
}