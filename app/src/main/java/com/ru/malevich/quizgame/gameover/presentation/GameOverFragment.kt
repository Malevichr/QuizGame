package com.ru.malevich.quizgame.gameover.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ru.malevich.quizgame.core.di.ProvideViewModel
import com.ru.malevich.quizgame.databinding.FragmentGameOverBinding
import com.ru.malevich.quizgame.load.presentation.NavigateToLoad

class GameOverFragment : Fragment() {
    private var _binding: FragmentGameOverBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GameOverViewModel
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
        viewModel =
            (requireActivity() as ProvideViewModel).makeViewModel(GameOverViewModel::class.java)
        binding.newGameButton.setOnClickListener {
            viewModel.clear()
            (requireActivity() as NavigateToLoad).navigateToLoad()
        }
        val uiState = viewModel.init(savedInstanceState == null)
        binding.statsTextView.update(uiState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

abstract class Logged : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Mlvc", "Fragment: onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Mlvc", "Fragment: onCreated")
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Mlvc", "Fragment: onViewCreated")
    }
}