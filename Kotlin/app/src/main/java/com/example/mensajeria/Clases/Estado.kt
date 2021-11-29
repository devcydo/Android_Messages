package com.example.mensajeria.Clases

import java.io.Serializable

data class Estado(var id: Int, var id_usuario: Int, var nombre: String,var foto: Int, var texto: String, var fecha : String) :
    Serializable