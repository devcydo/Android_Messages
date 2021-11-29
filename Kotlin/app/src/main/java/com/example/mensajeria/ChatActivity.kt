package com.example.mensajeria

import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.mensajeria.Clases.Chat
import com.example.mensajeria.Clases.Mensaje
import com.example.mensajeria.Utils.ActivityUtils
import com.example.mensajeria.Utils.ChatAdapter
import com.example.mensajeria.Utils.Conexion
import com.example.mensajeria.Utils.MessageAdapter
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_home.*
import java.lang.Exception

class ChatActivity : ActivityUtils() {

    var chat_nuevo = false
    var id_amigo = 0
    var id_chat = 0
    var id_usuario = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        id_chat = intent.getIntExtra("id_chat", 0)
        id_amigo = intent.getIntExtra("id_amigo",0)
        id_usuario = intent.getIntExtra("id_usuario", 0)
        chat_nuevo = intent.getBooleanExtra("chat_nuevo", false)

        if (chat_nuevo){
            AddChat().execute()
            chat_nuevo = false
        }

        SetChatRead().execute()
        ShowMessages().execute()
        btnEnviar.setOnClickListener {
            recyclerMensajes.setAdapter(null)
            recyclerMensajes.setLayoutManager(null)
            AddMessage().execute()
            UpdateChatDate().execute()
            ShowMessages().execute()
        }
    }


    inner class ShowMessages : AsyncTask<Unit, Unit, ArrayList<Mensaje>>() {
        override fun doInBackground(vararg params: Unit?): ArrayList<Mensaje> {
            return Conexion.getMessages(getString(R.string.ip), ""+id_chat)
        }

        override fun onPostExecute(result: ArrayList<Mensaje>?) {
            super.onPostExecute(result)
            println(result)
            result?.let {
                try {
                    val man = LinearLayoutManager(this@ChatActivity)
                    man.orientation = LinearLayoutManager.VERTICAL
                    recyclerMensajes.layoutManager = man

                    val adaptador = MessageAdapter(this@ChatActivity,R.layout.mensaje_banner, it, id_usuario)
                    recyclerMensajes.adapter = adaptador

                } catch (e: Exception) {
                    e.printStackTrace()
                    "Error al conectar".show(this@ChatActivity)
                }
            }
        }
    }

    inner class AddMessage : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("addMessage.php")
            webs.appendQueryParameter("usuario_id",""+id_usuario)
            webs.appendQueryParameter("chat_id", ""+id_chat)
            webs.appendQueryParameter("mensaje", editMensaje.text.toString())
            webs.build()
            return Conexion.resultado(webs.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            editMensaje.text.clear()
            println(result)
        }
    }

    inner class UpdateChatDate : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("updateChatDate.php")
            webs.appendQueryParameter("chat_id", ""+id_chat)
            webs.build()
            return Conexion.resultado(webs.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            println(result)
        }
    }

    inner class SetChatRead : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("setChatRead.php")
            webs.appendQueryParameter("chat_id", ""+id_chat)
            webs.appendQueryParameter("usuario_id", ""+id_usuario)
            webs.build()
            return Conexion.resultado(webs.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            println(result)
        }
    }

    inner class AddChat : AsyncTask<Unit, Unit, Int>() {
        override fun doInBackground(vararg params: Unit?): Int {
            return Conexion.addChat(getString(R.string.ws) + "addChat.php", getString(R.string.ip), ""+id_usuario, ""+id_amigo)
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            result?.let {
                id_chat = it
            }
        }
    }
}
