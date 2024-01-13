package com.example.quizgame.view

import android.os.Bundle
import android.util.Log
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
import com.example.quizgame.databinding.FragmentLoginBinding
import com.example.quizgame.viewmodel.AuthViewModel

class LoginFragment : Fragment() {
    private lateinit var mFragmentLoginBinding : FragmentLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mFragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false )
        return mFragmentLoginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        mFragmentLoginBinding.btnStartQuiz.setOnClickListener {
            Log.e("Check", "startQuiz")
            navController.navigate(R.id.action_loginFragment_to_questionFragment)
        }

        mFragmentLoginBinding.btnSignOut.setOnClickListener{
            authViewModel.signOut()
            Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_loginFragment_to_signInFragment)
        }
    }
}