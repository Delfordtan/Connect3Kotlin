package com.productions.del.connect3kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val game = Connect3()

    var counterArray = arrayOfNulls<ImageView>(9)

    class Connect3 {

        // 0 = win state; 1 = player 1; 2 = player 2
        var gameState: Int = 1

        var board = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)


        fun checkSlotPlacement(firstSlotOfColumn: Int): Int {
            when {
                board[firstSlotOfColumn + 2] > 0 -> {
                    return firstSlotOfColumn + 2
                }
                board[firstSlotOfColumn + 1] > 0 -> {
                    return firstSlotOfColumn + 2
                }
                board[firstSlotOfColumn] > 0 -> {
                    return firstSlotOfColumn + 1
                }
                board[firstSlotOfColumn] == 0 -> {
                    return firstSlotOfColumn
                }
                else -> {
                    return firstSlotOfColumn + 2
                }
            }
        }

        fun updateBoard(slot: Int) {
            if (board[slot] == 0) {
                board[slot] = gameState
            }

        }

        fun checkGameOver() {
            when {
                board[0] == board[1] && board[1] == board[2] && board[0] > 0 -> gameState = 0
                board[3] == board[4] && board[4] == board[5] && board[3] > 0 -> gameState = 0
                board[6] == board[7] && board[7] == board[8] && board[6] > 0 -> gameState = 0
                board[0] == board[4] && board[4] == board[8] && board[0] > 0 -> gameState = 0
                board[6] == board[4] && board[4] == board[2] && board[6] > 0 -> gameState = 0
                board[0] == board[3] && board[3] == board[6] && board[0] > 0 -> gameState = 0
                board[1] == board[4] && board[4] == board[7] && board[1] > 0 -> gameState = 0
                board[2] == board[5] && board[5] == board[8] && board[2] > 0 -> gameState = 0
            }
        }

    }

    fun dropCounterLeft(view: View) {
        dropCounter(0)
        changeGameState()


    }

    fun dropCounterMiddle(view: View) {
        dropCounter(3)
        changeGameState()
    }

    fun dropCounterRight(view: View) {
        dropCounter(6)
        changeGameState()
    }

    fun dropCounter(firstSlotOfColumn: Int) {
        if (game.gameState != 0) {

            val slot = game.checkSlotPlacement(firstSlotOfColumn)
            if (game.board[slot] == 0) {
                game.updateBoard(slot)
                animateCounter(slot)
            }
        }

    }

    fun animateCounter(slot: Int) {
        val counter = counterArray[slot]!!
        counter.translationY = -1000f
        if (game.gameState == 1) {
            counter.setImageResource(R.drawable.red)
        } else {
            counter.setImageResource(R.drawable.yellow)
        }
        counter.animate().translationYBy(1000f)?.duration = 200L
    }

    fun changeGameState() {
        val previousPlayer = game.gameState
        game.checkGameOver()
        when (game.gameState) {
            1 -> game.gameState = 2
            2 -> game.gameState = 1
            else -> Toast.makeText(this@MainActivity, "Player $previousPlayer won!", Toast.LENGTH_SHORT).show()
        }
    }

    fun reset(view: View) {
        game.board = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        for (counter in counterArray) {
            counter?.setImageResource(0)
        }
        game.gameState = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageArray = arrayOf(counter0, counter1, counter2, counter3, counter4, counter5, counter6, counter7, counter8)
        counterArray = imageArray
    }
}
