package com.example.quizgame.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgame.R
import com.example.quizgame.viewmodel.AuthViewModel

class LoginFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
//        ).get<AuthViewModel>(
//            AuthViewModel::class.java
//        )
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val startQuiz = view.findViewById<Button>(R.id.btn_start_quiz)
        val signOut = view.findViewById<Button>(R.id.btn_sign_out)

        startQuiz.setOnClickListener {
                    navController.navigate(R.id.action_loginFragment_to_questionFragment)
        }

        signOut.setOnClickListener{
            authViewModel.signOut()
            Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
            authViewModel.firebaseUserMutableLiveData.observe(this as LifecycleOwner) {
                navController.navigate(R.id.action_loginFragment_to_signInFragment)
            }
        }
    }
}