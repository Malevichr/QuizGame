package com.ru.malevich.quizgame

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ru.malevich.quizgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel: GameViewModel = GameViewModel()

        binding.firstChoiceButton.setOnClickListener {
            val uiState: GameUiState = viewModel.chooseFirst()
            uiState.update(binding = binding)
        }
        binding.secondChoiceButton.setOnClickListener {
            val uiState: GameUiState = viewModel.chooseSecond()
            uiState.update(binding = binding)
        }
        binding.thirdChoiceButton.setOnClickListener {
            val uiState: GameUiState = viewModel.chooseThird()
            uiState.update(binding = binding)
        }
        binding.forthChoiceButton.setOnClickListener {
            val uiState: GameUiState = viewModel.chooseForth()
            uiState.update(binding = binding)
        }
        binding.checkButton.setOnClickListener {
            val uiState: GameUiState = viewModel.check()
            uiState.update(binding = binding)
        }
        binding.nextButton.setOnClickListener {
            val uiState: GameUiState = viewModel.next()
            uiState.update(binding = binding)
        }
        val uiState: GameUiState = viewModel.init()
        uiState.update(binding = binding)
    }
}