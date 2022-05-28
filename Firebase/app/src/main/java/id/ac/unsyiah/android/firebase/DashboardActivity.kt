package id.ac.unsyiah.android.firebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import id.ac.unsyiah.android.firebase.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var _binding: ActivityDashboardBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener {
            mAuth.signOut()
            updateUI(mAuth.currentUser)
        }

        binding.btnProductive.setOnClickListener {
            startActivity(Intent(this, SosmedActivity::class.java))
        }

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.btnStory.setOnClickListener {
            startActivity(Intent(this, StoryActivity::class.java))
        }

        binding.btnTes.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser == null) {
            finish()
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        else {
            binding.userNameTV.text = "Welcome ${mAuth.currentUser!!.email}"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}