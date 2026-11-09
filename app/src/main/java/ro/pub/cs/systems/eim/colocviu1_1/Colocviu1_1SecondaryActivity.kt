package ro.pub.cs.systems.eim.colocviu1_1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import android.widget.TextView

class Colocviu1_1SecondaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_secondary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val totalText: TextView = findViewById(R.id.total_text)
        val registerButton: Button = findViewById(R.id.register_button)
        val cancelButton: Button = findViewById(R.id.cancel_button)

        if (intent != null) {
            val text2 = intent.getStringExtra("text2")
            totalText.text = text2
        }

        registerButton.setOnClickListener {
            setResult(RESULT_OK, Intent())
            finish()
        }

        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED, Intent())
            finish()
        }
    }
}