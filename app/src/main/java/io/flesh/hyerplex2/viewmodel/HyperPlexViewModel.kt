package io.flesh.hyerplex2.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.flesh.hyerplex2.utils.HyperPlexer
import javax.inject.Inject

@HiltViewModel
class HyperPlexViewModel @Inject constructor(private val hyperPlexer: HyperPlexer) : ViewModel() {

    val message = hyperPlexer.message

    init {
        setHyperPlex("This is HyperPlex")
    }

    /**
     * Sets up hyper plex initially and jumbles the text at the beginning.
     */
    fun setHyperPlex(initialText: String) {
        hyperPlexer.setInitial(initialText)
        hyperPlexer.jumble()
    }

    fun interactWithHyperPlex() = hyperPlexer.interactWithHyperPlex()
}
