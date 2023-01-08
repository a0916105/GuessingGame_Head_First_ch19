package tw.idv.jew.guessinggame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val words = listOf("Android", "Activity", "Fragment")   //可讓用戶猜的單字清單
    private val secretWord = words.random().uppercase() //讓用戶猜的單字

    private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay: LiveData<String>  //顯示出來的單字
        get() = _secretWordDisplay  //讓外界能讀_的backing屬性

    private var correctGuesses = "" //猜對的答案

    private val _incorrectGuesses = MutableLiveData<String>("")
    val incorrectGuesses: LiveData<String> //猜錯的答案
        get() = _incorrectGuesses   //讓外界能讀_的backing屬性

    private val _livesLeft = MutableLiveData<Int>(8)
    val livesLeft: LiveData<Int>   //剩下幾條命
        get() = _livesLeft  //讓外界能讀_的backing屬性

    init {
        //LiveData只能讀，所以更新使用_
        _secretWordDisplay.value = deriveSecretWordDisplay()   //推導秘密文字
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
                _secretWordDisplay.value = deriveSecretWordDisplay()
            } else {
                _incorrectGuesses.value += "$guess "
                _livesLeft.value = livesLeft.value?.minus(1)
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