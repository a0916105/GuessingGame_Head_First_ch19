package tw.idv.jew.guessinggame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

class ResultFragment : Fragment() {
    lateinit var viewModel: ResultViewModel
    lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val result = ResultFragmentArgs.fromBundle(requireArguments()).result
        //建立view model factory物件
        viewModelFactory = ResultViewModelFactory(result)
        //將view model factory傳給ViewModelProvider
        viewModel = ViewModelProvider(this@ResultFragment, viewModelFactory)
            //如果還沒view model物件，ViewModelProvider會使用view model factory來建立view model
            .get(ResultViewModel::class.java)
        //先建立view model再回傳ComposeView
        return ComposeView(requireContext()).apply {
            //告訴ComposeView要加入哪些composable
            setContent {    //移除composeView參考，因為它引用layout檔裡面的ComposeView
                //將ResultFragmentContent加入layout的composeView，並套用預設的Material佈景主題
                MaterialTheme {
                    Surface {
                        //view呼叫fragment的getview()方法，它回傳根view
                        view?.let { ResultFragmentContent(it, viewModel) }
                    }
                }
            }
        }
    }
}

@Composable
fun ResultText(result: String) {
    Text(text = result,
        fontSize = 28.sp,
        textAlign = TextAlign.Center)
}

@Composable
fun NewGameButton(clicked: () -> Unit) {
    Button(onClick = clicked) {
        Text("Start New Game")
    }
}

@Composable
fun ResultFragmentContent(view: View, viewModel: ResultViewModel) {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        ResultText(viewModel.result)    //將ResultText加入UI，並讓它使用viewModel的result屬性作為文字

        NewGameButton {
            view.findNavController()
                .navigate(R.id.action_resultFragment_to_gameFragment)
        }
    }
}