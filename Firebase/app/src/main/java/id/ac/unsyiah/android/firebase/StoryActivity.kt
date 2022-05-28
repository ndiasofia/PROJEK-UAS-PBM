package id.ac.unsyiah.android.firebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import id.ac.unsyiah.android.firebase.databinding.ActivityProfileBinding
import id.ac.unsyiah.android.firebase.databinding.ActivityRegistrationBinding
import id.ac.unsyiah.android.firebase.databinding.StoryBinding
import id.ac.unsyiah.android.firebase.Postingan as Postingan1

class StoryActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var _binding: StoryBinding?=null
    private val binding get() = _binding!!
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = StoryBinding.inflate(layoutInflater)
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
        database.child(user.uid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(data: DataSnapshot) {
                if (data.exists()) {
                    data.getValue(UserMC::class.java)?.apply { setupProfile(this) }
                } else {
                    progressDialog.dismiss()
                    startActivity(Intent(this@StoryActivity, LoginActivity::class.java))
                    finish()
                }
                database.child("USERS").child(user.uid).removeEventListener(this)
            }
        })
    }

    private fun setupProfile(userMC: UserMC) {
        binding.etTambahStory.setText(userMC.name)

        binding.btnKirim.setOnClickListener {
            val userReff = database.child("USERS").child(mAuth.uid!!)
            userReff.child("post").setValue(binding.etTambahStory.text.toString())

            userReff.child(userMC.authID).setValue(userMC).addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(this, "User Registered Successfully", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                    startActivity(Intent(this, StoryActivity::class.java))
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