package com.example.quizgame.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.quizgame.R
import com.example.quizgame.viewmodel.AuthViewModel

class   SplashFragment : Fragment() {
    private lateinit var viewModel: AuthViewModel
    private var navController: NavController? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {  // dễ lỗi ở đoạn này
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        Handler().postDelayed({
            if (viewModel.currentUserLiveData != null) {
                navController?.navigate(R.id.action_splashFragment_to_loginFragment)
            } else {
                navController?.navigate(R.id.action_splashFragment_to_signInFragment)
            }
        }, 1000)
    }

}