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
//
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
//            Log.e("checktagx", "btnSignIn")
//            signIn()
//        }
//    }
//
//    private fun signIn() {
//        Log.e("checktagx", "signIn fun")
//        val intent = mGoogleSignInClient.signInIntent
//        startActivityForResult(intent, RC_SIGN_IN)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) { //&& data != null){
//            Log.e("checktagx", "onActivityResult")
//            if (data != null) {
//                Log.e("checktagx", "data != null, $data")
//                val idToken  = handleGoogleSignInResult(data)
//                Log.e("checktagx", "idToken $idToken")
//
//                idToken?.let {
//                    signInWithGoogle(it){ isSuccess ->
//                        Log.e("checktagx", "signInWithGoogle")
//                    }
//                }
////                firebaseAuth(idToken)
//            } else {
//                Log.d("checktagx", "Data is null")
//            }
//        }
//    }
//
//    fun signInWithGoogle(idToken : String , onComplete : (Boolean) -> Unit) {
//        val firebaseCredential = GoogleAuthProvider.getCredential(idToken , null)
//        auth.signInWithCredential(firebaseCredential)
//            .addOnCompleteListener { task ->
//                Log.e("checktagx", "signInWithGoogle")
//            }
//    }
//    private fun handleGoogleSignInResult(data : Intent?) : String? {
//        Log.e("checktagx", "handleGoogleSignInResult")
//        try {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            Log.e("checktagx", "task: $task")
//            try {
//                val account = task.getResult(ApiException::class.java)
//                Log.e("checktagx", "account: $account")
//            } catch (e : ApiException){
//                Log.e("checktagx", "account error: ${e}")
//            }
//            val account = task.getResult(ApiException::class.java)
//            Log.e("checktagx", "task xx: $account")
//            return account?.idToken
//        } catch (e : ApiException) {
//            // Xử lý lỗi nếu cần thiết
//            Log.e("GoogleTest" , "ApiException: ${e.message}")
//        }
//        return null
//    }
//
//    private fun firebaseAuth(idToken: String) { //signInWithGoogle
//        Log.e("checktagx", "firebaseAuth")
//        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
//
//        auth.signInWithCredential(credential)
//            .addOnSuccessListener { task ->
//                if (task != null) {
//                    val user: FirebaseUser? = auth.currentUser
//
//                    val userID = user?.uid
//                    val name = user?.displayName
//                    val profile = user?.photoUrl.toString()
//
//                    val users = User(userID!!, name!!, profile)
//
//                    database.getReference().child("Users").child(user.uid).setValue(users)
//
//                    val intent = Intent(this, MainActivity2::class.java)
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//
//
//}
//
//data class User(var userId: String, var name: String, var profile: String)