package id.ac.unsyiah.android.firebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import id.ac.unsyiah.android.firebase.databinding.ActivityProfileBinding
import id.ac.unsyiah.android.firebase.databinding.ActivityRegistrationBinding
import id.ac.unsyiah.android.firebase.databinding.EditakunBinding

class EditakunActivity : AppCompatActivity() {
    private lateinit var toogle : ActionBarDrawerToggle
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private var _binding: EditakunBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = EditakunBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // setup firebase
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val drawerLayout : DrawerLayout = findViewById(R.id.editakun)
        val navView : NavigationView = findViewById(R.id.nav_viewedit)

        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mAuth = FirebaseAuth.getInstance()

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {

                //nama menu di drawernyaa -> liat main_menu
                R.id.nav_home -> {
                    val intent = Intent(this, AkunActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_beranda -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_bantuan -> {
                    val intent = Intent(this, BantuanActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_home4 -> {
                    val intent = Intent(this, SosmedActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_editakun -> {
                    val intent = Intent(this, EditakunActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    mAuth.signOut()
                    updateUI(mAuth.currentUser)
                }

            }

            true
        }

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
                    startActivity(Intent(this@EditakunActivity, LoginActivity::class.java))
                    finish()
                }
                database.child("USERS").child(user.uid).removeEventListener(this)
            }
        })
    }

    private fun setupProfile(userMC: UserMC) {
        binding.tampilusername.setText(userMC.name)

        binding.btnUpdate.setOnClickListener {
            val userReff = database.child("USERS").child(mAuth.uid!!)
            userReff.child("name").setValue(binding.updateUsername.text.toString())
            userReff.child("password").setValue(binding.updatePassword.text.toString())

            userReff.child(userMC.name).setValue(userMC).addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(this, "User update Successfully", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else {
                    Toast.makeText(
                        this,
                        "Update data Failed due to $(it.exception)",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            userReff.child(userMC.password).setValue(userMC).addOnCompleteListener {
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
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}

