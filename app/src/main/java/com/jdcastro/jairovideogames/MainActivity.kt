package com.jdcastro.jairovideogames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.jdcastro.jairovideogames.core.navigation.NavigationWrapper
import com.jdcastro.jairovideogames.ui.dashboard.viewModel.DashboardViewModel
import com.jdcastro.jairovideogames.ui.theme.JairoVideoGamesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JairoVideoGamesTheme {
                NavigationWrapper(viewModel)
                viewModel.getVideogameLocal()
            }
        }
    }
}
