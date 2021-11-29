package com.example.mensajeria.Clases

import java.io.Serializable

data class Usuario(var id: Int, var nombre: String ? = null, var numero: String, var frase: String, var foto: Int) : Serializable