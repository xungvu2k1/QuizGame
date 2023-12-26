package com.example.quizgame.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.quizgame.R
import com.example.quizgame.viewmodel.AuthViewModel

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private var viewModel: AuthViewModel? = null
    private var navController: NavController? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {  // dễ lỗi ở đoạn này
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get<AuthViewModel>(
            AuthViewModel::class.java
        )
        navController = findNavController(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            if (viewModel?.currentUser != null) {
                navController?.navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                navController?.navigate(R.id.action_splashFragment_to_signInFragment)
            }
        }, 1000)
    }

}