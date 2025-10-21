package com.example.pressbeauty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pressbeauty.view.InicioCatalogoScreen
import com.example.pressbeauty.view.PerfilUsuarioScreen
import com.example.pressbeauty.view.UsuarioFormScreen
import com.example.pressbeauty.viewmodel.ProductoViewModel
import com.example.pressbeauty.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        /*setContent {
            val navController = rememberNavController()
            val usuarioViewModel: UsuarioViewModel = viewModel()

            NavHost(
                navController = navController,
                startDestination = "UsuarioFormScreen"
            ) {
                composable("UsuarioFormScreen") {
                    UsuarioFormScreen(
                        navController = navController,
                        viewModel = usuarioViewModel
                    )
                }
                composable("PerfilUsuarioScreen") {
                    PerfilUsuarioScreen(
                        viewModel = usuarioViewModel
                    )
                }
            }
        }
    }*/
        setContent {
            val productoViewModel: ProductoViewModel = viewModel()

            // Llamas directamente a la pantalla del catálogo
            InicioCatalogoScreen(viewModel = productoViewModel)
        }
    }
}


