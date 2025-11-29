package com.example.pressbeauty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pressbeauty.model.BaseCompra
import com.example.pressbeauty.model.Baseusuario
import com.example.pressbeauty.repository.CompraRepository
import com.example.pressbeauty.repository.SesionDataStore
import com.example.pressbeauty.repository.UsuarioRepositorio
import com.example.pressbeauty.view.*
import com.example.pressbeauty.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val navController = rememberNavController()
            val context = LocalContext.current

            val sesionDataStore = remember { SesionDataStore(context) }

            val db = remember { Baseusuario.getDatabase(context) }
            val dbc = remember { BaseCompra.getDatabase(context) }

            val usuarioRepositorio = remember { UsuarioRepositorio(db.usuarioDao()) }
            val usuarioViewModel = remember { UsuarioViewModel(usuarioRepositorio, sesionDataStore) }

            val productoViewModel: ProductoViewModel = viewModel()
            val carritoViewModel: CarritoViewModel = viewModel()
            val imagenPerfilViewModel: ImagenPerfilViewModel = viewModel()

            val compraRepositorio = remember { CompraRepository(dbc.compraDao()) }
            val compraViewModel = remember { CompraViewModel(compraRepositorio) }

            val estaLogueado = remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                estaLogueado.value = usuarioViewModel.estaLogueado()
            }

            NavHost(
                navController = navController,
                startDestination = if (estaLogueado.value) "PerfilUsuarioScreen" else "LoginScreen"
            ) {

                composable("LoginScreen") {
                    LoginScreen(
                        navController = navController,
                        usuarioViewModel = usuarioViewModel
                    )
                }

                composable("UsuarioFormScreen") {
                    UsuarioFormScreen(
                        navController = navController,
                        viewModel = usuarioViewModel
                    )
                }

                composable("PerfilUsuarioScreen") {
                    PerfilUsuarioScreen(
                        usuarioViewModel = usuarioViewModel,
                        imagenPerfilViewModel = imagenPerfilViewModel,
                        navController = navController
                    )
                }

                composable("InicioCatalogoScreen") {
                    InicioCatalogoScreen(
                        productoViewModel = productoViewModel,
                        usuarioViewModel = usuarioViewModel,
                        navController = navController,
                        carritoViewModel = carritoViewModel
                    )
                }

                composable("productoScreen/{idProducto}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("idProducto")
                    ProductoScreen(
                        navController = navController,
                        idProducto = id,
                        productoViewModel = productoViewModel,
                        carritoViewModel = carritoViewModel
                    )
                }

                composable("CarritoScreen") {
                    CarritoScreen(
                        navController = navController,
                        carritoViewModel = carritoViewModel
                    )
                }
            }
        }
    }
}
