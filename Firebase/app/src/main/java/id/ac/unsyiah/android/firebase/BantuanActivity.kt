package id.ac.unsyiah.android.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class BantuanActivity : AppCompatActivity() {
    private lateinit var toogle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bantuan)

        val drawerLayout: DrawerLayout = findViewById(R.id.container2)
        val navView: NavigationView = findViewById(R.id.nav_view3)

        toogle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {

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
                R.id.nav_logout -> Toast.makeText(
                    applicationContext,
                    "Clicked Home",
                    Toast.LENGTH_SHORT
                ).show()


            }

            true
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}