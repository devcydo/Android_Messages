package com.example.mensajeria

import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.example.mensajeria.Clases.Chat
import com.example.mensajeria.Clases.Estado
import com.example.mensajeria.Clases.Usuario
import com.example.mensajeria.Utils.*
import kotlinx.android.synthetic.main.activity_home.*
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import android.R.string.cancel
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.EditText



class HomeActivity : ActivityUtils() {

    var chats = true
    var contactos = false
    var estados = false

    private var m_Text = ""

    private var usuario= Usuario(-1,"","","",-1)
    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                ShowChats().execute()
                chats = true
                contactos = false
                estados = false
                btnNuevoChat.setImageResource(R.drawable.newchatgroupicon)
                btnNuevoChat.isEnabled = false
                btnNuevoChat.alpha = 0.4f
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                ShowContacts().execute()
                chats = false
                contactos = true
                estados = false
                btnNuevoChat.setImageResource(R.drawable.newcontacticon)
                btnNuevoChat.isEnabled = true
                btnNuevoChat.alpha = 1f
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                ShowStatus().execute()
                chats = false
                contactos = false
                estados = true
                btnNuevoChat.setImageResource(R.drawable.newstatusicon)
                btnNuevoChat.isEnabled = true
                btnNuevoChat.alpha = 1f
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        usuario = intent.getSerializableExtra("usuario") as Usuario
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        ShowChats().execute()

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        btnNuevoChat.isEnabled = false
        btnNuevoChat.alpha = 0.4f
        btnNuevoChat.setOnClickListener {
            if(chats){
                val intent = Intent(this, GrupoActivity::class.java)
                intent.putExtra("id_usuario", usuario.id)
                startActivity(intent)
            }
            else if(estados){
                val intent = Intent(this, EstadoActivity::class.java)
                intent.putExtra("id_usuario", usuario.id)
                intent.putExtra("nombre", usuario.nombre)
                intent.putExtra("foto", usuario.foto)
                startActivity(intent)
            }
            else {
                val intent = Intent(this, ContactActivity::class.java)
                intent.putExtra("id_usuario", usuario.id)
                startActivity(intent)
            }
        }
    }

    public override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }

    inner class ShowChats : AsyncTask<Unit, Unit, ArrayList<Chat>>() {
        override fun doInBackground(vararg params: Unit?): ArrayList<Chat> {
            val webs = Uri.Builder()
            webs.scheme(getString(R.string.ip))
            webs.appendPath("mensajeria")
            webs.appendPath("showChats.php")
            webs.appendQueryParameter("usuario_id", usuario.id.toString())
            webs.build()

            return Conexion.resultadoChats(webs.toString(), getString(R.string.ip), usuario.id.toString())
        }

        override fun onPostExecute(result: ArrayList<Chat>?) {
            super.onPostExecute(result)
            println(result)
            result?.let {
                try {
                    val man = LinearLayoutManager(this@HomeActivity)
                    man.orientation = LinearLayoutManager.VERTICAL
                    reciclerChats.layoutManager = man

                    val adaptador = ChatAdapter(this@HomeActivity,R.layout.chat_banner, it, usuario.id)
                    reciclerChats.adapter = adaptador

                } catch (e: Exception) {
                    e.printStackTrace()
                    "Error al conectar".show(this@HomeActivity)
                }
            }
        }
    }

    inner class ShowContacts : AsyncTask<Unit, Unit, ArrayList<Usuario>>() {
        override fun doInBackground(vararg params: Unit?): ArrayList<Usuario> {
            return Conexion.getContactos(getString(R.string.ip), ""+usuario.id)
        }

        override fun onPostExecute(result: ArrayList<Usuario>?) {
            super.onPostExecute(result)
            println(result)
            result?.let {
                try {
                    val man = LinearLayoutManager(this@HomeActivity)
                    man.orientation = LinearLayoutManager.VERTICAL
                    reciclerChats.layoutManager = man

                    val adaptador = ContactAdapter(this@HomeActivity,R.layout.usuario_banner, it, usuario.id)
                    reciclerChats.adapter = adaptador

                } catch (e: Exception) {
                    e.printStackTrace()
                    "Error al conectar".show(this@HomeActivity)
                }
            }
        }
    }

    inner class ShowStatus : AsyncTask<Unit, Unit, ArrayList<Estado>>() {
        override fun doInBackground(vararg params: Unit?): ArrayList<Estado> {
            return Conexion.getStatus(getString(R.string.ip), ""+usuario.id)
        }

        override fun onPostExecute(result: ArrayList<Estado>?) {
            super.onPostExecute(result)
            println(result)
            result?.let {
                try {
                    val man = LinearLayoutManager(this@HomeActivity)
                    man.orientation = LinearLayoutManager.VERTICAL
                    reciclerChats.layoutManager = man

                    val adaptador = StatusAdapter(this@HomeActivity,R.layout.estado_banner, it)
                    reciclerChats.adapter = adaptador

                } catch (e: Exception) {
                    e.printStackTrace()
                    "Error al conectar".show(this@HomeActivity)
                }
            }
        }
    }
}
