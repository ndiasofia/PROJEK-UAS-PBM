package id.ac.unsyiah.android.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import id.ac.unsyiah.android.firebase.databinding.ActivityDashboardBinding

class MainActivity : AppCompatActivity() {
    private lateinit var toogle : ActionBarDrawerToggle
    private var _binding: ActivityDashboardBinding?=null
    private lateinit var tuliscerita : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var mAuth: FirebaseAuth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        tuliscerita = findViewById(R.id.tuliscerita)
        tuliscerita.setOnClickListener{
            startActivity(Intent(this, TulisceritaActivity::class.java))
        }

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

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser == null) {
            finish()
            startActivity(Intent(this, Awal3Activity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}