package com.example.pressbeauty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pressbeauty.model.BaseCompra
import com.example.pressbeauty.model.Baseusuario
import com.example.pressbeauty.repository.CompraRepository
import com.example.pressbeauty.repository.UsuarioRepositorio
import com.example.pressbeauty.view.CarritoScreen
import com.example.pressbeauty.view.InicioCatalogoScreen
import com.example.pressbeauty.view.LoginScreen
import com.example.pressbeauty.view.PerfilUsuarioScreen
import com.example.pressbeauty.view.ProductoScreen
import com.example.pressbeauty.view.UsuarioFormScreen
import com.example.pressbeauty.viewmodel.CarritoViewModel
import com.example.pressbeauty.viewmodel.CompraViewModel
import com.example.pressbeauty.viewmodel.ImagenPerfilViewModel
import com.example.pressbeauty.viewmodel.LoginViewModel
import com.example.pressbeauty.viewmodel.ProductoViewModel
import com.example.pressbeauty.viewmodel.UsuarioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            val productoViewModel: ProductoViewModel = viewModel()
            val carritoViewModel : CarritoViewModel = viewModel()
            val imagenPerfilViewModel : ImagenPerfilViewModel = viewModel()
            val db = remember { Baseusuario.getDatabase(context) }
            val dbc = remember { BaseCompra.getDatabase(context) }
            val repositorio = remember { UsuarioRepositorio(db.usuarioDao()) }
            val usuarioViewModel =remember { UsuarioViewModel(repositorio) }
            val compraRepositorio = remember { CompraRepository(dbc.compraDao()) }
            val compraViewModel = remember { CompraViewModel(compraRepositorio) }
            val loginViewModel : LoginViewModel = viewModel()

            NavHost(
                navController = navController,
                startDestination = "LoginScreen"
            ) {

                composable("LoginScreen"){
                    LoginScreen(
                        navController = navController,
                        loginViewModel = loginViewModel
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

                composable("InicioCatalogoScreen"){
                    InicioCatalogoScreen(productoViewModel = productoViewModel,
                                            usuarioViewModel = usuarioViewModel,
                                            navController = navController,
                                            carritoViewModel = carritoViewModel)
                }
                composable("productoScreen/{idProducto}"){ backStackEntry ->
                    val id = backStackEntry.arguments?.getString("idProducto")
                    ProductoScreen(navController,
                                    id,
                                    productoViewModel = productoViewModel,
                                    carritoViewModel = carritoViewModel)
                }

                composable("CarritoScreen"){
                    CarritoScreen(navController = navController,
                        carritoViewModel = carritoViewModel)
                }
            }
        }
    }
}


