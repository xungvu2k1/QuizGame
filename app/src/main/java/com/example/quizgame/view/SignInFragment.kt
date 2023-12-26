package com.example.quizgame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizgame.R
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.quizgame.viewmodel.AuthViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser

class SignInFragment : Fragment() {

    lateinit var viewModel: AuthViewModel
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

    private var navController: NavController? = null
    private var editEmail: EditText? = null
    private var editPass:EditText? = null
    private var signUpText: TextView? = null
    private var signInBtn: Button? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController(view)

        var editEmail = view.findViewById<TextInputEditText>(R.id.txt_email)
        var editPass = view.findViewById<TextInputEditText>(R.id.txt_password)
        var signUpText = view.findViewById<Button>(R.id.btn_sign_up)
        var signInBtn = view.findViewById<Button>(R.id.btn_sign_in)
        signUpText.setOnClickListener() {
            navController?.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        signInBtn.setOnClickListener() {
            val email = editEmail.getText().toString()
            val pass: String = editPass.getText().toString()
            if (!email.isEmpty() && !pass.isEmpty()) {
                viewModel.signIn(email, pass)
                Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
                viewModel.apply {
                    Log.e("mycodeisblocking","aaaaaaaaaaaaaaaaaa")
                    firebaseUserMutableLiveData.apply {
                        Log.e("mycodeisblocking","bbbbbbbbbbbbbbbbbbbbbbbbb")
                        observe(viewLifecycleOwner) {
                            //                            fun onChanged(firebaseUser: FirebaseUser?) {
                            if (it != null) {
                                Log.e("mycodeisblocking", "eeeeeeeeeeeeeeeeeeeeeee")
                                navController?.navigate(R.id.action_signInFragment_to_loginFragment)
                            }
//                            }
                        }
                    }
                }
            } else {
                Toast.makeText(context, "Please Enter Email and Pass", Toast.LENGTH_SHORT).show()
            }
        }
    }

}