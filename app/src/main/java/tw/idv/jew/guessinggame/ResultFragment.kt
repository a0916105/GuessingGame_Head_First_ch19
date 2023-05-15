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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import tw.idv.jew.guessinggame.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: ResultViewModel
    lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentResultBinding.inflate(inflater, container, false).apply {    //使用apply將composable加入UI
            //告訴ComposeView要加入哪些composable
            composeView.setContent {
                //將ResultFragmentContent加入layout的composeView，並套用預設的Material佈景主題
                MaterialTheme {
                    Surface {
                        view?.let { ResultFragmentContent(it) }
                    }
                }
            }
        }
        val view = binding.root

        val result = ResultFragmentArgs.fromBundle(requireArguments()).result

        //建立view model factory物件
        viewModelFactory = ResultViewModelFactory(result)
        //將view model factory傳給ViewModelProvider
        viewModel = ViewModelProvider(this, viewModelFactory)
            //如果還沒view model物件，ViewModelProvider會使用view model factory來建立view model
            .get(ResultViewModel::class.java)

        binding.resultViewModel = viewModel //改用data binding（設定layout的data binding變數）

        binding.newGameButton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_resultFragment_to_gameFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@Composable
fun NewGameButton(clicked: () -> Unit) {
    Button(onClick = clicked) {
        Text("Start New Game")
    }
}

@Composable
fun ResultFragmentContent(view: View) {
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        NewGameButton {
            view.findNavController()
                .navigate(R.id.action_resultFragment_to_gameFragment)
        }
    }
}