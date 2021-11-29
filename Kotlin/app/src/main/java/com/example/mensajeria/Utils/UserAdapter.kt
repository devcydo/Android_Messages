package com.example.mensajeria.Utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mensajeria.Clases.Usuario
import com.example.mensajeria.HomeActivity
import com.example.mensajeria.MainActivity
import com.example.mensajeria.R
import kotlinx.android.synthetic.main.usuario_banner.view.*

class UserAdapter(val context: Context, val res: Int, val usuarios:ArrayList<Usuario>) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = View.inflate(context, res, null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val usr = usuarios.get(p1)

        p0.enlaza(usr)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun enlaza(usr: Usuario) {
            val nombre = itemView.findViewById<TextView>(R.id.banner_nombre)
            val numero = itemView.findViewById<TextView>(R.id.banner_numero)
            val frase = itemView.findViewById<TextView>(R.id.banner_frase)
            val foto = itemView.findViewById<ImageView>(R.id.banner_imageUsr)

            nombre.text = usr.nombre
            numero.text = usr.numero
            frase.text = usr.frase

            when(usr.foto){
                1 -> foto.setImageResource(R.drawable.avataricon1)
                2 -> foto.setImageResource(R.drawable.avataricon2)
                3 -> foto.setImageResource(R.drawable.avataricon3)
                4 -> foto.setImageResource(R.drawable.avataricon4)
                else -> foto.setImageResource(R.drawable.avataricon5)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, HomeActivity::class.java)
                intent.putExtra("usuario", usr)
                context.startActivity(intent)
            }
        }
    }
}