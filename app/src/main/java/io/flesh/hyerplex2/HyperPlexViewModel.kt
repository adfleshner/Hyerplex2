package io.flesh.hyerplex2

import androidx.lifecycle.ViewModel


class HyperPlexViewModel : ViewModel() {

    private val hyperPlexer = HyperPlexer()

    val message = hyperPlexer.message

    init {
        hyperPlexer.initial = "This is HyperPlex"
        hyperPlexer.jumble()
    }

    fun interactWithHyperPlex() = hyperPlexer.interactWithHyperPlex()

}