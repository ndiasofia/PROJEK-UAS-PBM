package id.ac.unsyiah.android.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class Awal1Activity : AppCompatActivity() {
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.awal1)
        button = findViewById(R.id.button)
        button.setOnClickListener{
            startActivity(Intent(this, Awal2Activity::class.java))
        }
    }
}

