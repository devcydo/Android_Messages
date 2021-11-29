package com.example.mensajeria.Utils

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mensajeria.Clases.Estado
import com.example.mensajeria.R

class StatusAdapter(val context: Context, val res: Int, val chats:ArrayList<Estado>) :
    RecyclerView.Adapter<StatusAdapter.MyViewHolder>() {

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

        fun enlaza(e: Estado) {
            val nombre_estado = itemView.findViewById<TextView>(R.id.banner_nombreStat)
            val estado = itemView.findViewById<TextView>(R.id.banner_estadoStat)
            val imgEstado = itemView.findViewById<ImageView>(R.id.banner_imageStat)

            nombre_estado.text = e.nombre
            estado.text = e.texto


            when(e.foto){
                1 -> imgEstado.setImageResource(R.drawable.avataricon1)
                2 -> imgEstado.setImageResource(R.drawable.avataricon2)
                3 -> imgEstado.setImageResource(R.drawable.avataricon3)
                4 -> imgEstado.setImageResource(R.drawable.avataricon4)
                else -> imgEstado.setImageResource(R.drawable.avataricon5)
            }

            itemView.setOnClickListener {
                Toast.makeText(context, e.texto, Toast.LENGTH_LONG).show()
            }
        }
    }
}