package com.example.mensajeria

import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import com.example.mensajeria.Clases.Usuario
import com.example.mensajeria.Utils.ActivityUtils
import com.example.mensajeria.Utils.Conexion
import kotlinx.android.synthetic.main.activity_contact.*
import org.json.JSONObject
import java.lang.Exception

class ContactActivity : ActivityUtils() {

    var id_usuario = 0
    var amigo = Usuario(0,"","","",0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        id_usuario = intent.getIntExtra("id_usuario",0)

        btnBuscarContacto.setOnClickListener {
            LookByNumber().execute()
        }

        btnAgregarContacto.setOnClickListener {
            AddContacto().execute()
            Toast.makeText(this,"Contacto guardado",Toast.LENGTH_LONG).show()
            finish()
        }
    }

    inner class LookByNumber : AsyncTask<Unit, Unit, String>() {
            override fun doInBackground(vararg params: Unit?): String {
                val webs = Uri.Builder()
                webs.scheme(getString(R.string.ip))
                webs.appendPath("mensajeria")
                webs.appendPath("lookByNumber.php")
                webs.appendQueryParameter("numero", editNumeroContacto.text.toString())
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
                    if  ( jsonArrayUsers.length() > 0) {
                        val jsonUsr = jsonArrayUsers.getJSONObject(0)
                        val usr = Usuario(
                            jsonUsr.getInt("id"),
                            jsonUsr.getString("nombre"),
                            jsonUsr.getString("numero"),
                            jsonUsr.getString("frase"),
                            jsonUsr.getInt("foto")
                        )
                        amigo = usr
                        textNombre.text = usr.nombre
                        textNumero.text = usr.numero
                        textFrase.text = usr.frase
                        when(usr.foto){
                            1 -> imgContacto.setImageResource(R.drawable.avataricon1)
                            2 -> imgContacto.setImageResource(R.drawable.avataricon2)
                            3 -> imgContacto.setImageResource(R.drawable.avataricon3)
                            4 -> imgContacto.setImageResource(R.drawable.avataricon4)
                            else -> imgContacto.setImageResource(R.drawable.avataricon5)
                        }
                        btnAgregarContacto.isEnabled = true
                    }
                    else{
                        "No existe el usuario".show(this@ContactActivity)
                        textNombre.text = "Nombre"
                        textNumero.text = "Numero"
                        textFrase.text = "Frase"
                        imgContacto.setImageResource(R.mipmap.usuario)
                        btnAgregarContacto.isEnabled = false
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    "Error al conectar".show(this@ContactActivity)
                }
            }
        }
    }

    inner class AddContacto : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("addContact.php")
            webs.appendQueryParameter("usuario_id", ""+id_usuario)
            webs.appendQueryParameter("amigo_id", ""+amigo.id)
            webs.build()
            return Conexion.resultado(webs.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            println(result)
        }
    }
}
