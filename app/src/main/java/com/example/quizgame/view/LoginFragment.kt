package com.example.quizgame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizgame.R
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.quizgame.viewmodel.AuthViewModel
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

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

    private var viewModel: AuthViewModel? = null
    private var navController: NavController? = null

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
        var startQuiz = view.findViewById<Button>(R.id.btn_start_quiz)
        var signOut = view.findViewById<Button>(R.id.btn_sign_out)

        startQuiz.setOnClickListener() {
            navController?.navigate(R.id.action_loginFragment_to_questionFragment)
        }
        signOut.setOnClickListener(View.OnClickListener {
            viewModel!!.signOut()
            Toast.makeText(context, "Logout Successfully", Toast.LENGTH_SHORT).show()
            viewModel!!.firebaseUserMutableLiveData.observe(this as LifecycleOwner) {
//                        fun onChanged(firebaseUser: FirebaseUser?) {
//                            if (it != null) {
                navController?.navigate(R.id.action_loginFragment_to_signInFragment)
//                            }
//                        }
            }

        })
    }
}