package com.example.timerquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.timerquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 各ラジオボタンにクリックリスナーを設定
        // クリックされたら、それぞれの時間にtime変数を設定
        binding.radio1.setOnClickListener{
            time = 60000 * 1  // １分
        }
        binding.radio2.setOnClickListener{
            time = 60000 * 3  // 3分
        }
        binding.radio3.setOnClickListener{
            time = 60000 * 5  // 5分
        }
        binding.radio4.setOnClickListener{
            time = 60000 * 10 // 10分
        }

        // スタートボタンにクリックリスナーを設定
        binding.startBtn.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("TIME_KEY",time)
            startActivity(intent)
        }
    }
}