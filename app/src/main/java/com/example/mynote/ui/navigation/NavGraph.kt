package com.example.mynote.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mynote.viewmodel.MainViewModel
import com.example.mynote.ui.screens.DetailScreen
import com.example.mynote.ui.screens.HomeScreen

/**
 * Navigation routes used throughout the app.
 */
private sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail/{noteId}") {
        // Helper to build a route with an optional noteId
        fun createRoute(noteId: Int?) = if (noteId == null) "detail" else "detail/$noteId"
    }
}

@Composable
fun NavGraph(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = mainViewModel,
                onNoteClick = { noteId ->
                    navController.navigate(Screen.Detail.createRoute(noteId))
                },
                onAddNote = {
                    navController.navigate(Screen.Detail.createRoute(null))
                }
            )
        }
        composable(Screen.Detail.route) { backStackEntry ->
            // noteId is optional – when null we are creating a new note
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            DetailScreen(
                viewModel = mainViewModel,
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
