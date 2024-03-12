package com.mamafarm.android.interview_jitta

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mamafarm.android.interview_jitta.databinding.JittaActivityMainBinding

class MainActivity : ComponentActivity() {
    private val binding: JittaActivityMainBinding by lazy {
        JittaActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}