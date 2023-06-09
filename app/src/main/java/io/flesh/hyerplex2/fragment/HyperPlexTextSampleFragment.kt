package io.flesh.hyerplex2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.flesh.hyerplex2.databinding.FragmentHyperplexTextSampleBinding
import io.flesh.hyerplex2.viewmodel.HyperPlexViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HyperPlexTextSampleFragment : Fragment() {

    private val viewModel: HyperPlexViewModel by viewModels()
    private var _binding: FragmentHyperplexTextSampleBinding? = null
    private val binding: FragmentHyperplexTextSampleBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHyperplexTextSampleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.message.flowWithLifecycle(lifecycle)
                .collect {
                    binding.tvHyerplex.text = it
                }
        }
        binding.tvHyerplex.setOnClickListener {
            viewModel.interactWithHyperPlex()
        }

        binding.tvHyerplex2.setHyperPlexText("As a custom view")
        binding.tvHyerplex2.setOnClickListener {
            Toast.makeText(it.context, "Decoded", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // null the binding
    }
}
