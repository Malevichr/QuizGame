package com.ru.malevich.quizgame.gameover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ru.malevich.quizgame.QuizApp
import com.ru.malevich.quizgame.databinding.FragmentGameOverBinding
import com.ru.malevich.quizgame.game.NavigateToGame

class GameOverFragment : Fragment() {
    private var _binding: FragmentGameOverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: GameOverViewModel =
            (requireActivity().application as QuizApp).gameOverViewModel
        binding.statsTextView.update(viewModel.statsUiState())
        binding.newGameButton.setOnClickListener {
            (requireActivity() as NavigateToGame).navigateToGame()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}