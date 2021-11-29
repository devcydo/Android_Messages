package com.example.mensajeria

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.mensajeria.Clases.Usuario
import com.example.mensajeria.Utils.ActivityUtils
import com.example.mensajeria.Utils.Conexion
import com.example.mensajeria.Utils.UserAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONObject
import java.lang.Exception
import java.net.URI

class RegistroActivity : ActivityUtils() {

    var cont = 1
    var usuario = Usuario(-1, "", "", "", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        btnGuardar.setOnClickListener {
            val usr = Usuario(-1, editNombre.text.toString(), editNumero.text.toString(), editFrase.text.toString(), cont)

            usuario = usr
            AddUser().execute()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        imageViewUsr.setOnClickListener {
            cont++
            when(cont){
                1-> imageViewUsr.setImageResource(R.drawable.avataricon1)
                2-> imageViewUsr.setImageResource(R.drawable.avataricon2)
                3-> imageViewUsr.setImageResource(R.drawable.avataricon3)
                4-> imageViewUsr.setImageResource(R.drawable.avataricon4)
                5-> {
                    imageViewUsr.setImageResource(R.drawable.avataricon5)
                    cont = 0
                }
            }
        }
    }

    inner class AddUser : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("addUser.php")
            webs.appendQueryParameter("nombre", usuario.nombre)
            webs.appendQueryParameter("numero", usuario.numero)
            webs.appendQueryParameter("frase", usuario.frase)
            webs.appendQueryParameter("foto", usuario.foto.toString())
            webs.build()
            return Conexion.resultado(webs.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            println(result)
        }
    }


}
