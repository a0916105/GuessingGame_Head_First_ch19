package tw.idv.jew.guessinggame

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    val words = listOf("Android", "Activity", "Fragment")   //可讓用戶猜的單字清單
    val secretWord = words.random().uppercase() //讓用戶猜的單字

    val secretWordDisplay = MutableLiveData<String>()  //顯示出來的單字
    var correctGuesses = "" //猜對的答案
    val incorrectGuesses = MutableLiveData<String>("") //猜錯的答案
    val livesLeft = MutableLiveData<Int>(8)   //剩下幾條命

    init {
        secretWordDisplay.value = deriveSecretWordDisplay()   //推導秘密文字
    }

    //建立要顯示在畫面上的秘密單字String
    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }   //用secretWord裡面的每一個字母來呼叫checkLetter，並將它的回傳值加到display變數結尾
        return display
    }

    //檢查秘密單字有沒有猜對字母
    private fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }

    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuesses += guess
                secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                incorrectGuesses.value += "$guess "
                livesLeft.value = livesLeft.value?.minus(1)
            }
        }
    }

    fun isWon() = secretWord.equals(secretWordDisplay.value, true)

    fun isLost() = (livesLeft.value ?: 0) <= 0   //(如果是null回傳0)當用戶沒命時，輸掉遊戲

    fun wonLostMessage() : String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord."
        return message
    }

    //當view model即將被清除時執行的程式
    override fun onCleared() {  //經測試此APP沒有呼叫
        Log.i("GameViewModel", "ViewModel cleared")
    }
}