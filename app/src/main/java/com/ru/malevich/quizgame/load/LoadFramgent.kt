package com.ru.malevich.quizgame.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ru.malevich.quizgame.databinding.FragmentLoadBinding
import com.ru.malevich.quizgame.di.ProvideViewModel

class LoadFragment : Fragment() {
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

    private val update = { uiState: LoadUiState ->
        uiState.show(
            binding.errorTextView,
            binding.retryButton,
            binding.progressBar
        )
    }
    private lateinit var viewModel: LoadViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity() as ProvideViewModel).makeViewModel(LoadViewModel::class.java)
        update
        binding.retryButton.setOnClickListener {
            val uiState = viewModel.load()
            uiState.show(
                binding.errorTextView,
                binding.retryButton,
                binding.progressBar
            )
        }

        viewModel.load(firstRun = savedInstanceState == null)
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