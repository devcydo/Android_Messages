package com.example.mensajeria

import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.mensajeria.Clases.Estado
import com.example.mensajeria.Utils.ActivityUtils
import com.example.mensajeria.Utils.Conexion
import com.example.mensajeria.Utils.StatusAdapter
import com.example.mensajeria.Utils.UserAdapter
import kotlinx.android.synthetic.main.activity_estado.*
import org.json.JSONObject
import java.lang.Exception

class EstadoActivity : ActivityUtils() {

    var id_usuario = 0
    var nombre = ""
    var foto = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estado)

        id_usuario = intent.getIntExtra("id_usuario", 0)
        nombre = intent.getStringExtra("nombre")
        foto = intent.getIntExtra("foto", 0)

        ShowOwnStatus().execute()

        btnNuevoEstado.setOnClickListener {
            recyclerOwnEstados.setAdapter(null)
            recyclerOwnEstados.setLayoutManager(null)
            AddStatus().execute()
            ShowOwnStatus().execute()
        }
    }

    public override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }

    inner class ShowOwnStatus : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("showOwnStatus.php")
            webs.appendQueryParameter("id_usuario", ""+id_usuario)
            webs.build()

            return Conexion.resultado(webs.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            println(result)
            result?.let {
                try {
                    val json = JSONObject(it)

                    val jsonArrayStatus = json.getJSONArray("output")

                    val estados = ArrayList<Estado>()
                    for (i in 0.. jsonArrayStatus.length()-1) {
                        val jsonE = jsonArrayStatus.getJSONObject(i)

                        val estado = Estado(
                            jsonE.getInt("id"),
                            jsonE.getInt("id_usuario"),
                            nombre,
                            foto,
                            jsonE.getString("texto"),
                            jsonE.getString("fecha")
                        )
                        estados.add(estado)
                    }
                    val man = LinearLayoutManager(this@EstadoActivity)
                    man.orientation = LinearLayoutManager.VERTICAL
                    recyclerOwnEstados.layoutManager = man

                    val adaptador = StatusAdapter(this@EstadoActivity,R.layout.estado_banner, estados)
                    recyclerOwnEstados.adapter = adaptador

                } catch (e: Exception) {
                    e.printStackTrace()
                    "Error al conectar".show(this@EstadoActivity)
                }
            }
        }
    }

    inner class AddStatus : AsyncTask<Unit, Unit, String>() {
        override fun doInBackground(vararg params: Unit?): String {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("addStatus.php")
            webs.appendQueryParameter("id_usuario", ""+id_usuario)
            webs.appendQueryParameter("texto", editTextnuevoEstado.text.toString())
            webs.build()
            return Conexion.resultado(webs.toString())
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            editTextnuevoEstado.text.clear()
            println(result)
        }
    }
}
