package com.example.presentation.views

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.domain.util.Status
import com.example.presentation.R
import com.example.presentation.config.BaseActivity
import com.example.presentation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        with(viewModel) {
            centerList.observe(this@MainActivity) {
                Log.d(TAG, "Center List: $it")
            }

            problem.observe(this@MainActivity) {
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

    companion object {
        private const val TAG = "MainActivity_Corona"
    }
}