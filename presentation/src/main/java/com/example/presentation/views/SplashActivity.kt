package com.example.presentation.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.domain.util.Status
import com.example.presentation.R
import com.example.presentation.config.BaseActivity
import com.example.presentation.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.concurrent.thread

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val TIME_FOR_1_PER = 20L
    private val STANDARD_FOR_WAIT = 80
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manageProgress()
        initEvent()
    }

    private fun manageProgress() {
        thread(start = true) {
            for (percent in 1..100) {
                // 80%가 될때까지 && 저장이 완료되지 않았다면
                if(percent == STANDARD_FOR_WAIT && viewModel.progressCount.value!!.compareTo(10) < -1) {
                    // 저장이 완료될 때 까지 대기 -> 이때 몇 초를 대기하라는 요구 사항이 없었으므로 3초로 설정
                    for(sec in 1..3) {
                        Thread.sleep(1000)
                        // 저장이 완료되면
                        if(viewModel.progressCount.value!!.compareTo(10) == 0) {
                            // 0.4초에 걸쳐 100%를 만들고 페이지 이동
                            // 1% = 0.02초, 20% = 0.4초
                            for(i in 1..20) {
                                Thread.sleep(TIME_FOR_1_PER)
                                setProgressInfo(percent + i)
                                startMapActivity()
                                return@thread
                            }
                        }
                    }
                }
                Thread.sleep(TIME_FOR_1_PER)
                setProgressInfo(percent)
            }
            startMapActivity()
        }
    }

    private fun setProgressInfo(step: Int) {
        runOnUiThread {
            binding.progressBar.progress = step
            binding.tvLoading.text = resources.getString(R.string.loading_info) + " ${step}%"
        }
    }

    private fun initEvent() {
        with(viewModel) {
            problem.observe(this@SplashActivity) {
                when(it.status) {
                    Status.FAIL -> {
                        Log.d(TAG, "FAIL : ${it.message}")
                    }
                    Status.ERROR -> {
                        Log.d(TAG, "ERROR : ${it.message}")
                    }
                    Status.SUCCESS -> {
                        Log.d(TAG, "SUCCESS")
                    }
                }
            }
        }
    }

    private fun startMapActivity() {
        startActivity(Intent(this, MapActivity::class.java))
    }

    companion object {
        private const val TAG = "SplashActivity_Corona"
    }
}