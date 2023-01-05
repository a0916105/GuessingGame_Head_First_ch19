package tw.idv.jew.guessinggame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModelFactory(private val finalResult: String)   //使用和ResultViewMode的建構式一樣的參數
    : ViewModelProvider.Factory {   //實作ViewModelProvider.Factory

    override fun <T : ViewModel> create(modelClass: Class<T>): T {  //覆寫create方法，ViewModelProvider會用此來建立view model物件
        if (modelClass.isAssignableFrom(ResultViewModel::class.java))   //檢查ViewModelProvider想要建立的view model是不是正確的型態
            return ResultViewModel(finalResult) as T    //如果是，回傳一個
        throw IllegalArgumentException("Unknown ViewModel") //型態不正確，丟出例外
    }
}