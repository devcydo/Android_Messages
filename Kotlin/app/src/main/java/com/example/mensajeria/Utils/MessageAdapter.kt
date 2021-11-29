package com.example.mensajeria.Utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mensajeria.Clases.Mensaje
import com.example.mensajeria.R

class MessageAdapter(val context: Context, val res: Int, val chats:ArrayList<Mensaje>, val id_usr: Int) :
    RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = View.inflate(context, res, null)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val chat = chats.get(p1)

        p0.enlaza(chat)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun enlaza(m: Mensaje) {
            val nombre_msg = itemView.findViewById<TextView>(R.id.banner_nombreMsg)
            val mensaje_msg = itemView.findViewById<TextView>(R.id.banner_mensajeMsg)
            val img_leido = itemView.findViewById<ImageView>(R.id.imgLeido)

            nombre_msg.text = m.nombre
            mensaje_msg.text = m.mensaje

            if(m.id_usuario == id_usr){
                if(m.leido == 1){
                    img_leido.setImageResource(R.drawable.doublecheckicon)
                }
                nombre_msg.text = "Tu"
                nombre_msg.setTextColor(Color.BLUE)
            }
            else{

                nombre_msg.setTextColor(Color.RED)
                img_leido.imageAlpha = 0
            }

        }
    }
}