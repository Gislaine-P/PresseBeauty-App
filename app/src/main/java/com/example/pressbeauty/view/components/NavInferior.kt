package com.example.pressbeauty.view.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class NavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val route: String
)

@Composable
fun NavInferior(navController: NavController) {
    val items = listOf(
        NavItem("Inicio", Icons.Filled.Home, "InicioCatalogoScreen"),
        NavItem("Carrito", Icons.Filled.ShoppingCart, "CarritoScreen"),
        NavItem("Perfil", Icons.Filled.Person, "PerfilUsuarioScreen")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var canNavigate by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    NavigationBar(
        containerColor = Color(0xFFf2ebf1),
        contentColor = Color(0xFFc8bfc7)
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (canNavigate && currentRoute != item.route) {
                        canNavigate = false
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }

                        scope.launch {
                            delay(400)
                            canNavigate = true
                        }
                    }
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = if (selected)
                            Color(0xFF474347)
                        else
                            Color(0xFF958d94)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    }
}
