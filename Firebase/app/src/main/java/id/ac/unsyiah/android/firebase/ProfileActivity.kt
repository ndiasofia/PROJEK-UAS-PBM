package id.ac.unsyiah.android.firebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import id.ac.unsyiah.android.firebase.databinding.ActivityProfileBinding
import id.ac.unsyiah.android.firebase.databinding.ActivityRegistrationBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var _binding: ActivityProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // setup firebase
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
    }

    override fun onStart() {
        super.onStart()
       // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            checkAlreadyUser(user)
        } else {
            // arahin ke mainAcitivity lewat intent
        }
    }

    private fun checkAlreadyUser(user: FirebaseUser) {
        database.child("USERS").child(user.uid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(data: DataSnapshot) {
                if (data.exists()) {
                    data.getValue(UserMC::class.java)?.apply { setupProfile(this) }
                } else {
                    progressDialog.dismiss()
                    startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                    finish()
                }
                database.child("USERS").child(user.uid).removeEventListener(this)
            }
        })
    }

    private fun setupProfile(userMC: UserMC) {
         binding.etUsername.setText(userMC.name)

         binding.btnUpdate.setOnClickListener {
            val userReff = database.child("USERS").child(mAuth.uid!!)
            userReff.child("name").setValue(binding.etUsername.text.toString())

             userReff.child(userMC.name).setValue(userMC).addOnCompleteListener {
                 if(it.isSuccessful) {
                     Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT)
                         .show()
                     finish()
                     startActivity(Intent(this, DashboardActivity::class.java))
                 }
                 else {
                     Toast.makeText(
                         this,
                         "Saving data Failed due to $(it.exception)",
                         Toast.LENGTH_LONG
                     ).show()
                 }
             }
         }
    }


}

