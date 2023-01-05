package tw.idv.jew.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import tw.idv.jew.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    val words = listOf("Android", "Activity", "Fragment")   //可讓用戶猜的單字清單
    val secretWord = words.random().uppercase() //讓用戶猜的單字
    var secretWordDisplay = ""  //顯示出來的單字
    var correctGuesses = "" //猜對的答案
    var incorrectGuesses = "" //猜錯的答案
    var livesLeft = 8   //剩下幾條命

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        secretWordDisplay = deriveSecretWordDisplay()   //推導秘密文字
        updateScreen()  //更新畫面

        binding.guessButton.setOnClickListener {
            makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null   //重設edit text
            updateScreen()  //更新畫面
            if (isWon() || isLost()) {  //在用戶贏或輸時，前往ResultFragment，並將wonLostMessage()的回傳值傳給它
                val action = GameFragmentDirections
                    .actionGameFragmentToResultFragment(wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //設定layout的text view
    private fun updateScreen() {
        binding.word.text = secretWordDisplay
        binding.lives.text = "You have $livesLeft lives left."
        binding.incorrectGuesses.text = "Incorrect guesses: $incorrectGuesses"
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

    private fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuesses += guess
                secretWordDisplay = deriveSecretWordDisplay()
            } else {
                incorrectGuesses += "$guess "
                livesLeft--
            }
        }
    }

    private fun isWon() = secretWord.equals(secretWordDisplay, true)

    private fun isLost() = livesLeft <= 0   //當用戶沒命時，輸掉遊戲

    private fun wonLostMessage() : String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord."
        return message
    }

}