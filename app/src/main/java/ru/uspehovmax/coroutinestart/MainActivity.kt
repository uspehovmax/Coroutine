package ru.uspehovmax.coroutinestart

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.uspehovmax.coroutinestart.databinding.ActivityMainBinding

/**
 * Handler() - класс принимающий на вход объекты Runnable, взаимодействие м/у потоками, передача Message
 * Looper - класс в котором очередь из объектов Runnable,
 * из Looper передаются объекты в Handler(), кот. запускает ф-ю run
 *
 */
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding) {
            buttonLoad.setOnClickListener {

                progress.isVisible = true
                buttonLoad.isEnabled = false
/*
val jobCity = lifecycleScope.launch..
launch - метод возвращает Job - интерфейс, методы join, cancel, start ..
Job - интерфейс - ничего не возвращает

val deferredCity: Deferred<String> = lifecycleScope.async
Deferred - интерфейс, расширяет Job, возвращет <тип>
async - метод возвращает Deferred - интерфейс, методы await
 */
                /* Реализация Job
                // 1 jobCity
                val jobCity = lifecycleScope.launch {
                    val city = downloadCity()
                    tvLocation.text = city
                }
                //2 jobTemp
                val jobTemp = lifecycleScope.launch {
                    val temp = downloadTemperature()
                    tvTemperature.text = temp.toString()
                }
                //3
                lifecycleScope.launch {
                    jobCity.join()
                    jobTemp.join()
                    progress.isVisible = false
                    buttonLoad.isEnabled = true
                    Log.d("MSG", "jobCity + jobTemp = join()")
                }*/

                // Реализация Deferred
                // 1 deferredCity
                val deferredCity: Deferred<String> = lifecycleScope.async {
                    val city = downloadCity()
                    tvLocation.text = city
                    city
                }
                //2 deferredTemp
                val deferredTemp: Deferred<Int> = lifecycleScope.async {
                    val temp = downloadTemperature()
                    tvTemperature.text = temp.toString()
                    temp
                }
                //3
                lifecycleScope.launch {
                    val city = deferredCity.await()
                    val temp = deferredTemp.await()
                    progress.isVisible = false
                    buttonLoad.isEnabled = true
                    Toast.makeText(
                        this@MainActivity,
                        "City $city, Temp: $temp",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("MSG", "jobCity + jobTemp = join()")
                }
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

            val temp = downloadTemperature()
            tvTemperature.text = temp.toString()

            progress.isVisible = false
            buttonLoad.isEnabled = true
            Log.d("MSG", "downloadData()")
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
        delay(3_000)
        return "Moscow"
    }


    private suspend fun downloadTemperature(): Int {
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
//        Toast.makeText(
//            this,
//            getString(R.string.loading_temperature_toast, city),
//            Toast.LENGTH_SHORT
//        ).show()
        delay(5_000)
        return 17
    }


}