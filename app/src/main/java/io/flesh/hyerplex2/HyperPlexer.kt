package io.flesh.hyerplex2

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.roundToInt
import kotlin.random.Random

class HyperPlexer {
    private val letters = ('!'..'@') + ('{'..'~') + ('a'..'z') + ('A'..'Z').toList()

    // initial text
    var initial = ""

    private val internalMessage = MutableStateFlow(initial)
    val message = internalMessage.asStateFlow()
    private var running = false
    private var decoded = false
    private val interval = 30L
    private var speed = .5

    init {
        jumble()
    }

    private val handler = Handler(Looper.getMainLooper())



    // Decode Letter by letter
    private var decodeRunnable: Runnable = object : Runnable {
        override fun run() {
            internalMessage.update {
                decode(iterations.roundToInt())
            }
            handler.postDelayed(this, interval) // Run again after 30ms
            if (iterations.roundToInt() == message.value.length) {
                decoded = true
                resetHyerplex()
            }
            iterations += speed
        }
    }

    // TODO Randomize it back letter by letter
    private lateinit var encodeRunnable: Runnable
    private var iterations = 0.0


    // Updates the
    fun jumble() {
        decoded = false
        internalMessage.update {
            randomize()
        }
    }

    // Randomize it all
    private fun randomize() = List(initial.toList().size) { index ->
        when {
            initial[index] == ' ' -> {
                initial[index]
            }

            else -> {
                getRandomLetter()
            }
        }
    }.joinToString("")

    // Decode the string
    private fun decode(iterations: Int): String =
        List(initial.toList().size) { index ->
            when {
                index <= iterations || initial[index] == ' ' -> {
                    initial[index]
                }

                else -> {
                    getRandomLetter()
                }
            }
        }.joinToString("")

    private fun getRandomLetter() = letters[Random.nextInt(letters.size)]


    // When the user clickes it.
    fun interactWithHyperPlex() {
        if (decoded) {
            jumble()
            return
        }
        if (running) {
            resetHyerplex()
            jumble()
        } else {
            handler.post(decodeRunnable)
            running = true
        }
    }

    // Reset that Hyperplex
    private fun resetHyerplex() {
        iterations = 0.0
        running = false
        handler.removeCallbacks(decodeRunnable)
    }
}