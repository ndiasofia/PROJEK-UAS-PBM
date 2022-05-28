package id.ac.unsyiah.android.firebase

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import id.ac.unsyiah.android.firebase.databinding.SosmedBinding

class SosmedActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var _binding: SosmedBinding? = null
    private val binding get() = _binding!!
    private lateinit var toogle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = SosmedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()

        binding.btn1.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://elearning.unsyiah.ac.id/login/index.php")
            startActivity(openURL)
        }

        binding.btn2.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://elearning.unsyiah.ac.id/login/index.php")
            startActivity(openURL)
        }

        binding.btn3.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://simkuliah.unsyiah.ac.id/")
            startActivity(openURL)
        }

        binding.btn4.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://twitter.com/codingfess")
            startActivity(openURL)
        }

        binding.btn5.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.youtube.com/watch?v=pUTz5IOkBtE")
            startActivity(openURL)
        }

        binding.btn6.setOnClickListener {
            val openURL = Intent(android.content.Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.youtube.com/watch?v=c_fRtpQf4Oc")
            startActivity(openURL)
        }

        val drawerLayout : DrawerLayout = findViewById(R.id.container)
        val navView : NavigationView = findViewById(R.id.nav_view2)

        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
                R.id.nav_logout -> Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()


            }

            true
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}