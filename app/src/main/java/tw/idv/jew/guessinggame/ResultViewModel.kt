package tw.idv.jew.guessinggame

import android.util.Log
import androidx.lifecycle.ViewModel

class ResultViewModel(finalResult: String) : ViewModel() {
    val result = finalResult

    //當view model即將被清除時執行的程式
    override fun onCleared() {  //經測試此APP沒有呼叫
        Log.i("ResultViewModel", "ViewModel cleared")
    }
}