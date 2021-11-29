package com.example.mensajeria.Clases

import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

data class Chat(var id_chat: Int, var id_amigo: Int, var nombre: String, var foto: Int, var numero: String,
                var frase: String, var es_contacto: Int) : Serializable