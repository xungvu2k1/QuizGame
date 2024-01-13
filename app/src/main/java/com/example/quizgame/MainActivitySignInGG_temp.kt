//package com.example.quizgame
//
//import android.app.ProgressDialog
//import android.content.ContentValues.TAG
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.MutableLiveData
//import com.google.android.gms.auth.api.identity.BeginSignInRequest
//import com.google.android.gms.auth.api.identity.SignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.tasks.Task
//import com.google.firebase.auth.AuthCredential
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.GoogleAuthProvider
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.ktx.Firebase
//
//class MainActivity : AppCompatActivity() {
//    private var auth: FirebaseAuth = Firebase.auth
//    private val _signInSuccessLiveData : MutableLiveData<Boolean> = MutableLiveData()
//    private lateinit var database: FirebaseDatabase
//    private lateinit var mGoogleSignInClient: GoogleSignInClient
//    private lateinit var progressDialog: ProgressDialog
//    val RC_SIGN_IN = 40
//    lateinit var gso: GoogleSignInOptions
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val btnSignIn = findViewById<Button>(R.id.btn_ggcheck)
//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()
//
//        progressDialog = ProgressDialog(this)
//        progressDialog.setTitle("Creating account")
//        progressDialog.setMessage("we are creating your account")
//        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(R.string.your_web_client_id.toString()).requestEmail().build()
//        Log.e("checktagx", "$gso")
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        btnSignIn.setOnClickListener {
//            startSignInWithGoogle()
//        }
//    }
//    private fun startSignInWithGoogle() {
//        val signInIntent = getGoogleSignInIntent()
//        startActivityForResult(signInIntent , RC_SIGN_IN)
//    }
//
//    private fun getGoogleSignInIntent() : Intent {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.your_web_client_id)).requestEmail().build()
//
//        val googleSignInClient = GoogleSignIn.getClient(
//            this , gso
//        )
//        return googleSignInClient.signInIntent
//    }
//
//    override fun onActivityResult(requestCode : Int , resultCode : Int , data : Intent?) {
//        super.onActivityResult(requestCode , resultCode , data)
//
//        if (requestCode == RC_SIGN_IN) {
//            if (data != null) {
//                val idToken = handleGoogleSignInResult(data)
//
//                idToken?.let {
//                    signInWithGoogle(it)
//                }
//            } else {
//                Log.d("GoogleTest" , "Data is null")
//            }
//        }
//    }
//
//    private fun handleGoogleSignInResult(data : Intent?) : String? {
//        try {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            Log.e("checktagx", "task: $task")
//            val account = task.getResult(ApiException::class.java)
//            Log.e("checktagx", "account: $account")
//            return account?.idToken
//        } catch (e : ApiException) {
//            // Xử lý lỗi nếu cần thiết
//            Log.e("GoogleTest" , "ApiException: ${e.message}")
//        }
//        return null
//    }
//
//    fun signInWithGoogle(idToken : String) {
//        val firebaseCredential = GoogleAuthProvider.getCredential(idToken , null)
//        auth.signInWithCredential(firebaseCredential)
//            .addOnCompleteListener { task ->
//                _signInSuccessLiveData.postValue(task.isSuccessful)
//                Toast.makeText(this, "firebaseCredential", Toast.LENGTH_SHORT).show()
//            }
//    }
//}
//data class User(var userId: String, var name: String, var profile: String)