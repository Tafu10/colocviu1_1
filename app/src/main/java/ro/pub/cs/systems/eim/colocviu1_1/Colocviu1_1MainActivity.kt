package ro.pub.cs.systems.eim.colocviu1_1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class Colocviu1_1MainActivity : AppCompatActivity() {
    private lateinit var leftText: EditText
    private lateinit var rightText: EditText
    private var text = ""
    private var count = 0

    private val SERVICE_THRESHOLD = 4
    private var serviceStarted = false

    private val activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            Toast.makeText(this, "Result from secondary activity: OK", Toast.LENGTH_LONG).show()
        } else if (result.resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Result from secondary activity: CANCELED", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkAndStartService() {
        if (count > SERVICE_THRESHOLD && !serviceStarted) {
            val intent = Intent(this, Colocviu1_1Service::class.java)
            intent.putExtra("text", text)
            intent.putExtra("count", count)
            startService(intent)
            serviceStarted = true
        }
    }

    override fun onDestroy() {
        val intent = Intent(this, Colocviu1_1Service::class.java)
        stopService(intent)
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        leftText = findViewById(R.id.left_text)
        rightText = findViewById(R.id.right_text)
        val northButton: Button = findViewById(R.id.north_button)
        val westButton: Button = findViewById(R.id.west_button)
        val eastButton: Button = findViewById(R.id.east_button)
        val southButton: Button = findViewById(R.id.south_button)
        val navigateButton: Button = findViewById(R.id.navigate_button)

        northButton.setOnClickListener {
            count++
            text = text + "North"
            leftText.setText(text)
            rightText.setText(count.toString())
        }

        westButton.setOnClickListener {
            count++
            text = text + "West"
            leftText.setText(text)
            rightText.setText(count.toString())
        }

        eastButton.setOnClickListener {
            count++
            text = text + "East"
            leftText.setText(text)
            rightText.setText(count.toString())
        }

        southButton.setOnClickListener {
            count++
            text = text + "South"
            leftText.setText(text)
            rightText.setText(count.toString())
        }

        navigateButton.setOnClickListener {
            val intent = Intent(this, Colocviu1_1SecondaryActivity::class.java)
            val text2 = text
            text = ""
            count = 0
            leftText.setText(text)
            rightText.setText(count.toString())
            intent.putExtra("text2", text2)
            activityResultLauncher.launch(intent)
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("count")) {
                count = savedInstanceState.getInt("count")
                rightText.setText(count.toString())
            }
            if (savedInstanceState.containsKey("text")) {
                text = text.plus(savedInstanceState.getString("text"))
                leftText.setText(text)
            }
        }

    }
}