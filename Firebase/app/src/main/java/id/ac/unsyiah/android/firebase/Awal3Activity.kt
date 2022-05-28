package id.ac.unsyiah.android.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Awal3Activity : AppCompatActivity() {
    private lateinit var button3: Button
    private lateinit var button4: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.awal3)
        button3 = findViewById(R.id.button3)
        button4 = findViewById(R.id.button4)

        button3.setOnClickListener{
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        button4.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}