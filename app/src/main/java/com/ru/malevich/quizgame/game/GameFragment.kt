package com.ru.malevich.quizgame.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ru.malevich.quizgame.QuizApp
import com.ru.malevich.quizgame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = (requireActivity().application as QuizApp).gameViewModel
        lateinit var uiState: GameUiState
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
//            uiState.navigate(requireActivity() as NavigateToGameOver)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}