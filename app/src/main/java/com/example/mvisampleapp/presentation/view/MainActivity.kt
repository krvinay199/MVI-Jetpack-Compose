package com.example.mvisampleapp.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.example.mvisampleapp.presentation.viewModel.MainIntent
import com.example.mvisampleapp.presentation.viewModel.AnimalViewModel
import com.example.mvisampleapp.presentation.viewModel.AnimalsState
import com.example.mvisampleapp.ui.theme.MVISampleAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    private val viewModel: AnimalViewModel by viewModels { AnimalViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVISampleAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(vm = viewModel) {
                        lifecycleScope.launch {
                            viewModel.userIntentChannel.send(MainIntent.FetchAnimals)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainScreen(vm: AnimalViewModel, onButtonClick: () -> Unit) {
    val state = vm.state.value

    when (state) {
        is AnimalsState.Idle -> IdleScreen(onButtonClick)
        is AnimalsState.Loading -> LoadingScreen()
        is AnimalsState.Success -> AnimalsList(animals = state.animals)
        is AnimalsState.Error -> {
            IdleScreen(onButtonClick)
            Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
        }
    }
}