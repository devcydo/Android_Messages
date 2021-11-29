package com.example.mensajeria.Utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.mensajeria.Clases.Chat
import com.example.mensajeria.Clases.Estado
import com.example.mensajeria.Clases.Mensaje
import com.example.mensajeria.Clases.Usuario
import com.example.mensajeria.R
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class Conexion {


    // Equivalente a tener la clase Conexion como estatica
    companion object {

        var mensajes = ArrayList<Mensaje>()
        var id_amigo = 0
        var nombre = ""
        var foto = 0
        var numero = ""
        var frase = ""

        fun resultado(dir:String) : String {
            var info = ""
            try{
                val url = URL(dir)
                val con = url.openConnection()
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                info = br.readLine()
            }catch (e : Exception){
                e.printStackTrace()
            }
            return info
        }

        fun resultadoChats(dir:String, ip:String, usuario_id: String) : ArrayList<Chat> {
            var chats = ArrayList<Chat>()
            var res = ""
            try{
                //Encontrar los chats del usuario
                val url = URL(dir)
                val con = url.openConnection()
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                res = br.readLine()

                val json = JSONObject(res)
                val jsonArrayChats = json.getJSONArray("output")
                for (i in 0.. jsonArrayChats.length()-1) {
                    val jsonChat = jsonArrayChats.getJSONObject(i)
                    if(jsonChat.getString("nombre").equals("direct_chat")){
                        getFriendChat(ip, jsonChat.getInt("id").toString(), usuario_id)
                    }
                    else{
                        nombre = jsonChat.getString("nombre")
                        numero = ""
                        frase = "Chat grupal"
                        foto = jsonChat.getInt("imagen")
                    }

                    var chat = Chat(
                        jsonChat.getInt("id"),
                        id_amigo,
                        nombre,
                        foto,
                        numero,
                        frase,
                        isContact(ip, usuario_id)
                    )
                    chats.add(chat)
                }

            }catch (e : Exception){
                e.printStackTrace()

            }

            return chats
        }

        fun addChat(dir:String, ip:String, usuario_id: String, amigo_id: String) : Int {
            var res = ""
            var ress = 0
            try{
                //Encontrar los chats del usuario
                val url = URL(dir)
                val con = url.openConnection()
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                res = br.readLine()

                val json = JSONObject(res)
                ress = json.getInt("chat_id")

                addUserToChat(ip, ""+ress, usuario_id)
                addUserToChat(ip, ""+ress, amigo_id)

            }catch (e : Exception){
                e.printStackTrace()

            }

            return ress
        }

        fun addUserToChat(ip:String, chat_id: String, usuario_id: String) {
            var res = ""
            try{
                val webs = Uri.Builder()
                webs.scheme(ip)
                webs.appendPath("mensajeria")
                webs.appendPath("addUserToChat.php")
                webs.appendQueryParameter("chat_id", chat_id)
                webs.appendQueryParameter("usuario_id", usuario_id)
                webs.build()

                val url = URL(webs.toString())
                val con = url.openConnection()
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                res = br.readLine()

            }catch (e : Exception){
                e.printStackTrace()
            }
        }


        fun getMessages(ip:String, chat_id: String): ArrayList<Mensaje>{
            var res = ""
            var messages= ArrayList<Mensaje>()
            try{
                val webs = Uri.Builder()
                webs.scheme(ip)
                webs.appendPath("mensajeria")
                webs.appendPath("showMessages.php")
                webs.appendQueryParameter("chat_id", chat_id)
                webs.build()

                val url = URL(webs.toString())
                val con = url.openConnection()
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                res = br.readLine()

                val json = JSONObject(res)
                val jsonArrayMessages = json.getJSONArray("output")
                println(jsonArrayMessages.toString())
                for (i in 0.. jsonArrayMessages.length()-1) {
                    val jsonMessage = jsonArrayMessages.getJSONObject(i)
                    var mensaje = Mensaje(
                        jsonMessage.getInt("id"),
                        jsonMessage.getInt("id_usuario"),
                        jsonMessage.getString("nombre"),
                        jsonMessage.getString("mensaje"),
                        jsonMessage.getInt("leido"),
                        jsonMessage.getString("fecha")
                    )
                    messages.add(mensaje)
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
            return messages
        }

        fun getContactos(ip:String, id_usuario: String): ArrayList<Usuario>{
            var res = ""
            var contactos = ArrayList<Usuario>()
            try{
                val webs = Uri.Builder()
                webs.scheme(ip)
                webs.appendPath("mensajeria")
                webs.appendPath("showContacts.php")
                webs.appendQueryParameter("id_usuario", id_usuario)
                webs.build()

                val url = URL(webs.toString())
                val con = url.openConnection()
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                res = br.readLine()

                val json = JSONObject(res)
                val jsonArray = json.getJSONArray("output")
                println(jsonArray.toString())
                for (i in 0.. jsonArray.length()-1) {
                    val jsonM = jsonArray.getJSONObject(i)
                    var usuario = Usuario(
                        jsonM.getInt("id"),
                        jsonM.getString("nombre"),
                        jsonM.getString("numero"),
                        jsonM.getString("frase"),
                        jsonM.getInt("foto")
                    )
                    contactos.add(usuario)
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
            return contactos
        }

        fun getStatus(ip:String, id_usuario: String): ArrayList<Estado>{
            var res = ""
            var estados = ArrayList<Estado>()
            try{
                val webs = Uri.Builder()
                webs.scheme(ip)
                webs.appendPath("mensajeria")
                webs.appendPath("showStatus.php")
                webs.appendQueryParameter("id_usuario", id_usuario)
                webs.build()

                val url = URL(webs.toString())
                val con = url.openConnection()
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                res = br.readLine()

                val json = JSONObject(res)
                val jsonArray = json.getJSONArray("output")
                println(jsonArray.toString())
                for (i in 0.. jsonArray.length()-1) {
                    val jsonM = jsonArray.getJSONObject(i)
                    var estado = Estado(
                        jsonM.getInt("estado_id"),
                        jsonM.getInt("usuario_id"),
                        jsonM.getString("nombre"),
                        jsonM.getInt("foto"),
                        jsonM.getString("texto"),
                        jsonM.getString("fecha")
                    )
                    estados.add(estado)
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
            return estados
        }

        fun isContact(ip:String, usuario_id: String) : Int {
            var res = ""
            var resu = 1;
            try{
                val webs = Uri.Builder()
                webs.scheme(ip)
                webs.appendPath("mensajeria")
                webs.appendPath("isContact.php")
                webs.appendQueryParameter("id_usuario", usuario_id)
                webs.appendQueryParameter("id_amigo", ""+id_amigo)
                webs.build()

                val url = URL(webs.toString())
                val con = url.openConnection()
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                res = br.readLine()

                val json = JSONObject(res)
                val jsonArray = json.getJSONArray("output")
                if (jsonArray.length() == 0){
                    resu = 0
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
            return resu;
        }

        fun getFriendChat(ip:String, chat_id: String, usuario_id: String) {
            var res = ""
            try{
                val webs = Uri.Builder()
                webs.scheme(ip)
                webs.appendPath("mensajeria")
                webs.appendPath("showGroupUsers.php")
                webs.appendQueryParameter("chat_id", chat_id)
                webs.appendQueryParameter("usuario_id", usuario_id)
                webs.build()

                val url = URL(webs.toString())
                val con = url.openConnection()
                val br = BufferedReader(
                    InputStreamReader(con.getInputStream())
                )
                res = br.readLine()

                val json = JSONObject(res)
                val jsonArray = json.getJSONArray("output")
                for (i in 0.. jsonArray.length()-1) {
                    val jsonM = jsonArray.getJSONObject(i)
                    id_amigo = jsonM.getInt("id")
                    nombre = jsonM.getString("nombre")
                    foto = jsonM.getInt("foto")
                    numero = jsonM.getString("numero")
                    frase = jsonM.getString("frase")
                }
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
}