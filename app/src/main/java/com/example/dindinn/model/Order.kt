package com.example.dindinn.model

import android.content.Context
import android.media.RingtoneManager
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import java.text.SimpleDateFormat
import java.util.*


data class Order(
    val id: Int,
    val title: String,
    val quantity: Int,
    val created_at: Long = System.currentTimeMillis(), // for test only
    var alerted_at: Long = 0,
    var expired_at: Long = 0,
    val addon: List<Addon>
) {

    val expiredString = ObservableField<String>()
    val expired = ObservableBoolean(false)

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    fun startTimer(context: Context) {
        runnable = Runnable {
            expired_at -= 1000
            expiredString.set(
                "Expired after " + SimpleDateFormat("mm:ss", Locale.US).format(
                    expired_at - created_at
                )
            )
            // alerting
            if (expired_at == alerted_at) {
                RingtoneManager.getActualDefaultRingtoneUri(
                    context,
                    RingtoneManager.TYPE_ALL
                ).let {
                    RingtoneManager.getRingtone(context, it).play()
                }
                Toast.makeText(context, "Order #$id will be expired in 1 min", Toast.LENGTH_SHORT)
                    .show()
            }
            // expired
            if (expired_at == created_at) {
                stopTimer()
                expired.set(true)
                expiredString.set("EXPIRED!")
                return@Runnable
            }

            handler.postDelayed(runnable, 1000)

        }.also { runnable = it }

        handler.postDelayed(runnable, 1000)
    }

    fun stopTimer() {
        handler.removeCallbacks(runnable)
    }
}


data class Addon(
    val id: Int,
    val title: String,
    val quantity: Int,
)
