package com.ru.malevich.quizgame.game.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ru.malevich.quizgame.core.di.ProvideViewModel
import com.ru.malevich.quizgame.core.presentation.AbstractFragment
import com.ru.malevich.quizgame.databinding.FragmentGameBinding
import com.ru.malevich.quizgame.gameover.presentation.NavigateToGameOver

class GameFragment : AbstractFragment<GameUiState, GameViewModel>() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override val update = { uiState: GameUiState ->
        requireActivity().runOnUiThread {
            uiState.update(
                binding.questionTextView,
                binding.firstChoiceButton,
                binding.secondChoiceButton,
                binding.thirdChoiceButton,
                binding.forthChoiceButton,
                binding.nextButton,
                binding.checkButton
            )
            uiState.navigate(requireActivity() as NavigateToGameOver)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (requireActivity() as ProvideViewModel)
            .makeViewModel(GameViewModel::class.java)

        binding.firstChoiceButton.setOnClickListener {
            val uiState = viewModel.chooseFirst()
            update(uiState)
        }
        binding.secondChoiceButton.setOnClickListener {
            val uiState = viewModel.chooseSecond()
            update(uiState)
        }
        binding.thirdChoiceButton.setOnClickListener {
            val uiState = viewModel.chooseThird()
            update(uiState)
        }
        binding.forthChoiceButton.setOnClickListener {
            val uiState = viewModel.chooseForth()
            update(uiState)
        }
        binding.checkButton.setOnClickListener {
            viewModel.check()
        }
        binding.nextButton.setOnClickListener {
            viewModel.next()
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates(observer = update)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}