package com.example.mensajeria.Clases

import java.io.Serializable

data class Mensaje(var id: Int, var id_usuario: Int, var nombre: String, var mensaje: String, var leido: Int, var fecha : String) : Serializable