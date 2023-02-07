package ru.uspehovmax.coroutinestart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.uspehovmax.coroutinestart.databinding.ActivityMainBinding

/**
 * Handler() - класс принимающий на вход объекты Runnable
 * Looper - класс в котором очередь из объектов Runnable
 * из Looper передаются объекты в Handler(), кот. запускает ф-ю run
 *
 */
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // 1 вариант работы с главным потоком из порожденного через Handler()
//    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            lifecycleScope.launch {
                downloadData()
            }
        }
    }

    // ключевое слово suspend - прерывание
    private suspend fun downloadData() {
        with(binding) {
            progress.isVisible = true
            buttonLoad.isEnabled = false

            val city = downloadCity()
            tvLocation.text = city

            val temp = downloadTemperature(city)
            tvTemperature.text = temp.toString()

            progress.isVisible = false
            buttonLoad.isEnabled = true
        }

    }

    private suspend fun downloadCity(): String {
/*        // создание отдельного потока для асинхронного выполнения
 thread {
    Thread.sleep(2_000)
     1 вариант раб через Handler - Looper
        handler.post{...} // или
        Handler(Looper.getMainLooper()).post
    // 2 вариант работы через runOnUiThread - под капотом тот же Handler - Looper
    runOnUiThread {...}
//            {
//                callback.invoke("Moscow")
//            }*/
        // 3 вариант корутины
        delay(2_000)
        return "Moscow"
    }


    private suspend fun downloadTemperature(city: String): Int {
        /*
// создание отдельного потока для асинхронного выполнения
//        thread {
            /* 1 вариант работы через Handler - Looper
                handler.post{...} // или
                Handler(Looper.getMainLooper()).post

            // 2 вариант работы через runOnUiThread - под капотом тот же Handler - Looper
            runOnUiThread {...} */
            // runOnUiThread - под капотом тот же Handler - Looper
            {
                Toast.makeText(
                    this,
                    getString(R.string.loading_temperature_toast, city),
                    Toast.LENGTH_SHORT
                ).show()
            }
            Thread.sleep(2_000)
            // runOnUiThread - под капотом тот же Handler - Looper
            runOnUiThread {
                callback.invoke(17)
            }
        }*/
        Toast.makeText(
            this,
            getString(R.string.loading_temperature_toast, city),
            Toast.LENGTH_SHORT
        ).show()
        delay(2_000)
        return 17
    }


}