package com.example.mensajeria

import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.mensajeria.Clases.Usuario
import com.example.mensajeria.Utils.ActivityUtils
import com.example.mensajeria.Utils.Conexion
import com.example.mensajeria.Utils.UserAdapter
import com.example.mensajeria.Utils.UserGroupAdapter
import kotlinx.android.synthetic.main.activity_grupo.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.Exception

class GrupoActivity : ActivityUtils() {

    var id_usuario = 0
    var cont = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grupo)
        ShowContacts().execute()

        imageViewGroup.setOnClickListener {
            cont++
            when(cont){
                1-> imageViewGroup.setImageResource(R.drawable.groupicon1)
                2-> imageViewGroup.setImageResource(R.drawable.groupicon2)
                3-> imageViewGroup.setImageResource(R.drawable.groupicon3)
                4-> {
                    imageViewGroup.setImageResource(R.drawable.groupicon4)
                    cont = 0
                }
            }
        }

        btnCrearGrupo.setOnClickListener {

            println("G R U P O: "+editGroupName.text.toString())
            println(" F O T O : "+cont)
            //println(" U S E R : "+id_usuario)

            finish()
        }


    }

    inner class ShowContacts : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("showContacts.php")
            webs.appendQueryParameter("id_usuario", id_usuario.toString())
            webs.build()

            return Conexion.resultado(webs.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            println(result)
            result?.let {
                try {
                    val json = JSONObject(it)

                    val jsonArrayUsers = json.getJSONArray("output")

                    val usuarios = ArrayList<Usuario>()
                    for (i in 0.. jsonArrayUsers.length()-1) {
                        val jsonUsr = jsonArrayUsers.getJSONObject(i)

                        val usr = Usuario(
                            jsonUsr.getInt("id"),
                            jsonUsr.getString("nombre"),
                            jsonUsr.getString("numero"),
                            jsonUsr.getString("frase"),
                            jsonUsr.getInt("foto")
                        )
                        usuarios.add(usr)
                    }
                    val man = LinearLayoutManager(this@GrupoActivity)
                    man.orientation = LinearLayoutManager.VERTICAL
                    recyclerGroupUsrs.layoutManager = man

                    val adaptador = UserAdapter(this@GrupoActivity,R.layout.usuario_banner, usuarios)
                    recyclerGroupUsrs.adapter = adaptador

                } catch (e: Exception) {
                    e.printStackTrace()
                    "Error al conectar".show(this@GrupoActivity)
                }
            }
        }
    }

    inner class AddChatGroup : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("addChatGroup.php")
            webs.appendQueryParameter("chat_nombre", editGroupName.text.toString())
            webs.appendQueryParameter("imagen", ""+cont)
            webs.build()
            return Conexion.resultado(webs.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            println(result)
        }
    }
}
