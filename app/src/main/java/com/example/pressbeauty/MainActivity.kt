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
import com.example.pressbeauty.model.Baseusuario
import com.example.pressbeauty.repository.UsuarioRepositorio
import com.example.pressbeauty.view.CarritoScreen
import com.example.pressbeauty.view.InicioCatalogoScreen
import com.example.pressbeauty.view.PerfilUsuarioScreen
import com.example.pressbeauty.view.ProductoScreen
import com.example.pressbeauty.view.UsuarioFormScreen
import com.example.pressbeauty.viewmodel.CarritoViewModel
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
            val db = remember { Baseusuario.getDatabase(context) }
            val repositorio = remember { UsuarioRepositorio(db.usuarioDao()) }
            val usuarioViewModel =remember { UsuarioViewModel(repositorio) }

            NavHost(
                navController = navController,
                startDestination = "InicioCatalogoScreen"
            ) {

                composable("UsuarioFormScreen") {
                    UsuarioFormScreen(
                        navController = navController,
                        viewModel = usuarioViewModel
                    )
                }
                composable("PerfilUsuarioScreen") {
                    PerfilUsuarioScreen(
                        viewModel = usuarioViewModel,
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


