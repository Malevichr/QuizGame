package com.ru.malevich.quizgame.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ru.malevich.quizgame.QuizApp
import com.ru.malevich.quizgame.R
import com.ru.malevich.quizgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var uiState: GameUiState
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

        val viewModel = (application as QuizApp).viewModel

        val update: () -> Unit = {
            uiState.update(
                binding.questionTextView,
                binding.firstChoiceButton,
                binding.secondChoiceButton,
                binding.thirdChoiceButton,
                binding.forthChoiceButton,
                binding.nextButton,
                binding.checkButton
            )
        }

        binding.firstChoiceButton.setOnClickListener {
            uiState = viewModel.chooseFirst()
            update.invoke()
        }
        binding.secondChoiceButton.setOnClickListener {
            uiState = viewModel.chooseSecond()
            update.invoke()
        }
        binding.thirdChoiceButton.setOnClickListener {
            uiState = viewModel.chooseThird()
            update.invoke()
        }
        binding.forthChoiceButton.setOnClickListener {
            uiState = viewModel.chooseForth()
            update.invoke()
        }
        binding.checkButton.setOnClickListener {
            uiState = viewModel.check()
            update.invoke()
        }
        binding.nextButton.setOnClickListener {
            uiState = viewModel.next()
            update.invoke()
        }

        uiState = viewModel.init(savedInstanceState == null)
        update.invoke()
    }

}