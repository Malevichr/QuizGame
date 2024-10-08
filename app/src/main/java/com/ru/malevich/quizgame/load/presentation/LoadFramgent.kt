package com.ru.malevich.quizgame.load.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ru.malevich.quizgame.core.di.ProvideViewModel
import com.ru.malevich.quizgame.core.presentation.AbstractFragment
import com.ru.malevich.quizgame.databinding.FragmentLoadBinding
import com.ru.malevich.quizgame.game.presentation.NavigateToGame

class LoadFragment : AbstractFragment<LoadUiState, LoadViewModel>(), UiObserver {
    private var _binding: FragmentLoadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override val update: (LoadUiState) -> Unit = { uiState: LoadUiState ->
        requireActivity().runOnUiThread {
            uiState.show(
                binding.errorTextView,
                binding.retryButton,
                binding.progressBar
            )
            uiState.navigate((requireActivity() as NavigateToGame))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).makeViewModel(LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }


        viewModel.load(isFirstRun = savedInstanceState == null)
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

    override fun invoke(p1: LoadUiState) {
        update.invoke(p1)
    }
}