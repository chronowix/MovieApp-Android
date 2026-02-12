package fr.sdv.alan.movieapp.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.sdv.alan.movieapp.ui.navigation.AppNavGraph

@Composable
fun App(
    innerPadding: PaddingValues
) {
    AppNavGraph(
        modifier = Modifier.padding(innerPadding)
    )
}
