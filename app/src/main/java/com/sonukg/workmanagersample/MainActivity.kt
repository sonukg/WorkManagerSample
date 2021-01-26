package com.sonukg.workmanagersample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {

    var tvStatus: TextView? = null
    var btnSend: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStatus = findViewById(R.id.textView)
        btnSend = findViewById(R.id.button)

        val mWorkManager = WorkManager.getInstance()
        val mRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java).build()
        btnSend!!.setOnClickListener(View.OnClickListener { mWorkManager.enqueue(mRequest) })

        mWorkManager.getWorkInfoByIdLiveData(mRequest.id).observe(this, { workInfo ->
            if (workInfo != null) {
                val state = workInfo.state
                tvStatus!!.append("""$state""".trimIndent())
            }
        })

    }
    companion object{
        const val MESSAGE_STATUS = "message_status"
    }
}