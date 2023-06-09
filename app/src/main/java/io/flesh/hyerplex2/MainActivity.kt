package io.flesh.hyerplex2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import io.flesh.hyerplex2.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: HyperPlexViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
            Toast.makeText(this, "Decode", Toast.LENGTH_SHORT).show()
        }
    }
}
