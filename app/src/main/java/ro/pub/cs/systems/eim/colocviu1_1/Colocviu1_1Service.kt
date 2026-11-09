package ro.pub.cs.systems.eim.colocviu1_1

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

class PracticalTest01Service : Service() {
    private var processingThread: ProcessingThread? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val text = intent?.getStringExtra("text") ?: 0
        val count = intent?.getIntExtra("count", 0) ?: 0
        processingThread = ProcessingThread(this, text, count)
        processingThread?.start()
        return START_REDELIVER_INTENT
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        processingThread?.stopThread()
        super.onDestroy()
    }
}

class ProcessingThread(private val context: Context, private val num1: Int, private val num2: Int) : Thread() {
    private var isRunning = true
    private val actions = listOf(
        "ro.pub.cs.systems.eim.action1",
        "ro.pub.cs.systems.eim.action2",
        "ro.pub.cs.systems.eim.action3"
    )

    override fun run() {
        while (isRunning) {
            val date = java.util.Date().toString()

            val intent = Intent()
            intent.action = actions.random()
            intent.putExtra("message", "Time: $date, ArithMean: $arithmeticMean, GeomMean: $geometricMean")
            context.sendBroadcast(intent)

            try {
                sleep(10000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun stopThread() {
        isRunning = false
    }
}
