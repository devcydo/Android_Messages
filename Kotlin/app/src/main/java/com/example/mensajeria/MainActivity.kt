package com.example.mensajeria

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.mensajeria.Clases.Usuario
import com.example.mensajeria.Utils.ActivityUtils
import com.example.mensajeria.Utils.Conexion
import com.example.mensajeria.Utils.UserAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registro.*
import org.json.JSONObject
import java.lang.Exception

class MainActivity : ActivityUtils() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAgregar.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
        ShowUsers().execute()
    }

    public override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }

    inner class ShowUsers : AsyncTask<Unit, Unit, String>() {

        override fun doInBackground(vararg params: Unit?): String {
            return Conexion.resultado(getString(R.string.ws) + "showUsers.php")
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
                    val man = LinearLayoutManager(this@MainActivity)
                    man.orientation = LinearLayoutManager.VERTICAL
                    recyclerViewUsrs.layoutManager = man

                    val adaptador = UserAdapter(this@MainActivity,R.layout.usuario_banner, usuarios)
                    recyclerViewUsrs.adapter = adaptador

                } catch (e: Exception) {
                    e.printStackTrace()
                    "Error al conectar".show(this@MainActivity)
                }
            }
        }
    }
}
