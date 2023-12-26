package com.example.quizgame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizgame.R
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.quizgame.viewmodel.AuthViewModel
import com.google.android.material.textfield.TextInputEditText

class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    private lateinit var viewModel: AuthViewModel
    private var navController: NavController? = null
    private var editEmail: EditText? = null
    private  var editPass:EditText? = null
    private var signInText: TextView? = null
    private var signUpBtn: Button? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController(view)
        var editEmail = view.findViewById<TextInputEditText>(R.id.txt_email)
        var editPass = view.findViewById<TextInputEditText>(R.id.txt_password)
        var signUpText = view.findViewById<Button>(R.id.btn_sign_up)
        var signInBtn = view.findViewById<Button>(R.id.btn_sign_in)

        signInBtn.setOnClickListener() {
            navController?.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        signUpText.setOnClickListener(View.OnClickListener {
            val email = editEmail.text.toString()
            val pass: String = editPass.text.toString()
            if (!email.isEmpty() && !pass.isEmpty()) {
                viewModel.signUp(email, pass)
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                viewModel.firebaseUserMutableLiveData.observe(viewLifecycleOwner) {
//                        fun onChanged(firebaseUser: FirebaseUser?) {
//                            if (it != null) {
                    navController?.navigate(R.id.action_signUpFragment_to_signInFragment)
//                            }
//                        }
                }
            } else {
                Toast.makeText(context, "Please Enter Email and Pass", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get<AuthViewModel>(
            AuthViewModel::class.java
        )
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

}