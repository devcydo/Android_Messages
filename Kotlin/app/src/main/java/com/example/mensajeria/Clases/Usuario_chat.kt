package com.example.mensajeria.Clases

import java.io.Serializable

data class Usuario_chat(var id: Int, var nombre: String, var numero: String, var frase: String, var foto: Int) : Serializable