package com.lucaszacarias.meli.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.lucaszacarias.meli.ui.detail.DetailScreen
import com.lucaszacarias.meli.ui.mainview.MainScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "search",
        modifier = modifier
    ) {
        composable("search") {
            MainScreen(
                onNavigateToDetail = { articleId ->
                    navController.navigate("detail/$articleId")
                }
            )
        }
        composable(
            route = "detail/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.IntType })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getInt("articleId") ?: 0
            DetailScreen(
                articleId = articleId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
