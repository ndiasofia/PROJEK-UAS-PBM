package id.ac.unsyiah.android.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Awal2Activity : AppCompatActivity() {
    private lateinit var button2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.awal2)
        button2 = findViewById(R.id.button2)
        button2.setOnClickListener{
            startActivity(Intent(this, Awal3Activity::class.java))
        }
    }
}