package id.ac.unsyiah.android.firebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import id.ac.unsyiah.android.firebase.databinding.ActivityRegistrationBinding



class RegistrationActivity : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth

    private var _binding: ActivityRegistrationBinding?=null
    private val binding get() = _binding!!
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        mAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading Please Wait")
        progressDialog.setCancelable(false)

        binding.btnDaftar.setOnClickListener {
            var name = binding.etUsername.text.toString()
            var email = binding.etEmail.text.toString()
            var pass = binding.etPassword.text.toString()

            if (name.isEmpty()) {
                binding.etUsername.setError("Username required!!")
                binding.etUsername.requestFocus()
            }
            else if(email.isEmpty()) {
                binding.etEmail.setError("Email required!!")
                binding.etEmail.requestFocus()
            }
            else if(pass.isEmpty()) {
                binding.etPassword.error = "Password required!!!"
                binding.etPassword.requestFocus()
            }
            else {
                progressDialog.show()
                //Register user
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful) {
                        val user = UserMC()
                        user.name = name
                        user.email = email
                        user.password = pass
                        user.authID = mAuth.currentUser!!.uid
                        val dbRef = FirebaseDatabase.getInstance().reference.child("USERS")

                        dbRef.child(user.authID).setValue(user).addOnCompleteListener {
                            if(it.isSuccessful) {
                                Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT)
                                    .show()
                                finish()
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                            else {
                                Toast.makeText(
                                    this,
                                    "Saving data Failed due to $(it.exception)",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "User Registered Failed due to ${it.exception}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
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
