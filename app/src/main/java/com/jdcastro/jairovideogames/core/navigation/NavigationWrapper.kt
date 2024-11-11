package com.jdcastro.jairovideogames.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jdcastro.jairovideogames.ui.dashboard.view.DashboardScreen
import com.jdcastro.jairovideogames.ui.dashboard.viewModel.DashboardViewModel
import com.jdcastro.jairovideogames.ui.videogameDetail.view.VideogameDetailScreen

@Composable
fun NavigationWrapper(viewModel: DashboardViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Dashboard) {
        composable<Dashboard> {
            DashboardScreen(viewModel) {id -> navController.navigate(VideogameDetail(id = id))}
        }

        composable<VideogameDetail> { backStackEntry ->
            val detail = backStackEntry.toRoute<VideogameDetail>()
            VideogameDetailScreen(viewModel, detail.id)
        }
    }
}