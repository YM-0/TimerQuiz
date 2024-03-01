package com.example.timerquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.timerquiz.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // レイアウトをセット
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // クイズの正解数と問題数を取得
        val score = intent.getIntExtra("RIGHT_ANSWER_COUNT", 0)
        val count = intent.getIntExtra("QUIZ_COUNT", 0)

        // 取得した正解数と問題数をTextViewに表示
        binding.total.text = getString(R.string.total, count)
        binding.score.text = getString(R.string.score, score)
        //binding.resultLabel.text = getString(R.string.result_score, count, score)

        // もう一度ボタンの処理
        binding.tryAgainBtn.setOnClickListener {
            startActivity(Intent(this@ResultActivity, MainActivity::class.java))
        }
    }
}