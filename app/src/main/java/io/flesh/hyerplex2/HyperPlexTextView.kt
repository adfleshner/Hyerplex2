package io.flesh.hyerplex2

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class HyperPlexTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs),
    LifecycleOwner {

    private val viewScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val lifecycleRegistry: LifecycleRegistry by lazy { LifecycleRegistry(this) }
    private val hyperPlexer = HyperPlexer()

    init {
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        observeStateFlow()
        setOnClickListener {
            hyperPlexer.interactWithHyperPlex()
        }
    }

    private var hyperplexClickListner: OnClickListener = OnClickListener {
        hyperPlexer.interactWithHyperPlex()
    }

    override fun performClick(): Boolean {
        hyperplexClickListner.onClick(this)
        return super.performClick()
    }

    private fun observeStateFlow() {
        viewScope.launch {
            hyperPlexer.message.flowWithLifecycle(lifecycle).collect { value ->
                text = value
            }
        }
    }

    fun setHyperPlexText(initialText: String) {
        hyperPlexer.initial = initialText
        hyperPlexer.jumble()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewScope.cancel()
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry

}

