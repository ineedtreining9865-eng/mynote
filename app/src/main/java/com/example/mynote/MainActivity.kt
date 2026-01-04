package com.example.mynote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.mynote.ui.theme.MynoteTheme
import com.example.mynote.ui.navigation.NavGraph
import com.example.mynote.viewmodel.MainViewModel

/**
 * Entry point of the application. It sets the Compose content and provides the shared
 * [MainViewModel] to the navigation graph.
 */
class MainActivity : ComponentActivity() {
    // ViewModel scoped to the Activity lifecycle
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MynoteTheme {
                // The NavGraph receives the ViewModel instance so that all composables share state
                NavGraph(mainViewModel = mainViewModel)
            }
        }
    }
}
