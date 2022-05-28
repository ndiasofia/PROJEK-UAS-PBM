package id.ac.unsyiah.android.firebase

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.app.ProgressDialog
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseUser
import id.ac.unsyiah.android.firebase.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding?=null
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setCancelable(false)
        mAuth = FirebaseAuth.getInstance()
        createRequest()

        binding.btnMasuk.setOnClickListener {
            var email = binding.etEmail.text.toString()
            var pass = binding.etPassword.text.toString()

            if(email.isEmpty()) {
                binding.etEmail.setError("Username required!!")
                binding.etPassword.requestFocus()
            }
            else if(pass.isEmpty()) {
                binding.etPassword.error = "Password required!!!"
                binding.etPassword.requestFocus()
            }
            else {
                progressDialog.show()
                //login user
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful) {
                        Toast.makeText(this, "User Login Successfully", Toast.LENGTH_SHORT)
                            .show()
                        finish()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this,
                            "User Login Failed due to ${it.exception}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
//        binding.gotoRegScreen.setOnClickListener {
//            startActivity(Intent(this, RegistrationActivity::class.java))
//        }
    }

    private fun createRequest() {
        var signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.webclientid))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null) {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}