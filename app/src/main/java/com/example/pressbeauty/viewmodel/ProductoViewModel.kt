package com.example.pressbeauty.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pressbeauty.model.ProductoUI

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductoViewModel : ViewModel() {

    /*Crea una variable priv que guarda el estado interno de VM
    * MutableStateFlow se puede modificar su valor dentro del VM
    * List es el tipo de dato que guarda una lista
    * emptyList mantiene valor inicial una lista vacia*/
    private val _productos = MutableStateFlow(listOf(
        ProductoUI(
            "1",
            "https://i.pinimg.com/1200x/4e/7f/c8/4e7fc89f546e21ef6231f13b5f491f6e.jpg",
            "Celestial Butterfly",
            "Uñas celestes brillantes con un diseño de alas de mariposas y detalles con un tono metalico",
            10,
            17000),
        ProductoUI(
            "2",
            "https://i.pinimg.com/1200x/58/2c/fe/582cfef6ba64da56775eced8189ba2e1.jpg",
            "Celestial Brightness",
            "Uñas celestes brillantes con un diseño detallado y colores calidos.",
            10,
            10000),
        ProductoUI(
            "3",
            "https://img.kwcdn.com/product/fancy/407abcef-b7ff-4ddb-b981-506c00ee6716.jpg?imageView2/2/w/800/q/70",
            "Metallic Sky",
            "Uñas blancas y celestes con lazos, corazones y brillos metalicos junto a pequeñas perlas que le dan un toque.",
            10,
            10000),
        ProductoUI(
            "4",
            "https://i.pinimg.com/736x/21/9e/a9/219ea9589d25eed07e6fde99bf0ad195.jpg",
            "CherryLove",
            "Uñas de un color rojo intenso, diseño con lazos, corazones y cerezas, junto con perlas.",
            10,
            10000),
        ProductoUI(
            "5",
            "https://i.pinimg.com/1200x/7c/8e/3a/7c8e3a6eebfe6588cd22feba82d86d43.jpg",
            "Starry Night",
            "Uñas basadas en la noche, diseños de estrellas, arañas y lunas, colores azules oscuros, negros y blancos.",
            10,
            10000),
        ProductoUI(
            "6",
            "https://i.pinimg.com/736x/30/36/fa/3036fa653b5f90655bae071a6915d5f1.jpg",
            "Metallic White",
            "Uñas de color blanco con un diseño metalico.",
            10,
            10000),
        ProductoUI(
            "7",
            "https://i.pinimg.com/736x/f2/98/3d/f2983d13d546f7d24384c18ca4325263.jpg",
            "Intense Pink Strawberries",
            "Uñas fresas rosado intenso con diseños marcados y llamativos",
            10,
            10000),
        ProductoUI(
            "8",
            "https://i.pinimg.com/736x/c8/ef/6b/c8ef6b56c2d9ab93e54bd6bff9328a4e.jpg",
            "Pink Strawberries",
            "Uñas con diferentes diseños bonitos, fresas y brillos.",
            10,
            10000)

        )
    )

    /*StateFlow forma moderna de guardar un estado y notificar cambios,
    * mantiene valor actual y avisa cuando el valor cambia para que UI se actualice sola
    *
    * Version publica de _productos, la vista compose puede leerlo y ver cambios pero no modificarlo
    * Stateflow sin mutable, solo lectura
    * pantalla puede mirar la lista de productos pero no cambiarla*/
    val productos: StateFlow<List<ProductoUI>> = _productos

}


