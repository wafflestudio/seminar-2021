package com.veldic.tictactoeassignment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _dataList: MutableLiveData<Array<Array<String>>> = MutableLiveData(Array(3) { Array(3) { MARK_BLANK } })
    val dataList: LiveData<Array<Array<String>>> = _dataList

    private val _gameStatus: MutableLiveData<String> = MutableLiveData(GAME_STATUS_PLAYING)
    val gameStatus: LiveData<String> = _gameStatus

    private val _turnData: MutableLiveData<Boolean> = MutableLiveData(TURN_O)
    val turnData: LiveData<Boolean> = _turnData

    fun clickButton(row: Int, column: Int) {
        if (_gameStatus.value != GAME_STATUS_PLAYING) return

        val currData = _dataList.value
        currData?.let {
            if (it[row][column] == MARK_BLANK) {
                it[row][column] = if (_turnData.value == TURN_O) MARK_O else MARK_X
                _turnData.value = !_turnData.value!!
                _dataList.value = currData
                checkStatus(currData)
            }
        }
    }

    private fun checkStatus(data: Array<Array<String>>) {
        // check row
        for (i in 0 until 3) {
            if (data[i][0] == data[i][1] && data[i][1] == data[i][2]) {
                if (data[i][0] != MARK_BLANK) {
                    if (data[i][0] == MARK_X) endGame(GAME_STATUS_X_WIN) else endGame(GAME_STATUS_O_WIN)
                    return
                }
            }
        }

        // check column
        for (i in 0 until 3) {
            if (data[0][i] == data[1][i] && data[1][i] == data[2][i]) {
                if (data[0][i] != MARK_BLANK) {
                    if (data[0][i] == MARK_X) endGame(GAME_STATUS_X_WIN) else endGame(GAME_STATUS_O_WIN)
                    return
                }
            }
        }

        // check diagnol
        if (data[0][0] == data[1][1] && data[1][1] == data[2][2]) {
            if (data[0][0] != MARK_BLANK) {
                if (data[0][0] == MARK_X) endGame(GAME_STATUS_X_WIN) else endGame(GAME_STATUS_O_WIN)
                return
            }
        }
        if (data[0][2] == data[1][1] && data[1][1] == data[2][0]) {
            if (data[0][2] != MARK_BLANK) {
                if (data[0][2] == MARK_X) endGame(GAME_STATUS_X_WIN) else endGame(GAME_STATUS_O_WIN)
                return
            }
        }

        var isDraw = true
        drawLoop@ for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (data[i][j] == MARK_BLANK) {
                    isDraw = false
                    break@drawLoop
                }
            }
        }

        if (isDraw) {
            endGame(GAME_STATUS_DRAW)
            return
        }
    }

    private fun endGame(status: String) {
        _gameStatus.value = status
    }

    fun resetGame() {
        _dataList.value = Array(3) { Array(3) { MARK_BLANK } }
        _turnData.value = TURN_O
        _gameStatus.value = GAME_STATUS_PLAYING
    }

    companion object {
        const val GAME_STATUS_PLAYING = "PLAYING..."
        const val GAME_STATUS_X_WIN = "PLAYER X WIN!"
        const val GAME_STATUS_O_WIN = "PLAYER O WIN!"
        const val GAME_STATUS_DRAW = "DRAW!"

        const val TURN_O = true
        const val TURN_X = false

        const val MARK_O = "O"
        const val MARK_X = "X"
        const val MARK_BLANK = ""
    }
}
