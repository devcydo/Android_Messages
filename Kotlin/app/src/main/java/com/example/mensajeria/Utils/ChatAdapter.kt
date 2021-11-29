package com.example.mensajeria.Utils

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mensajeria.ChatActivity
import com.example.mensajeria.Clases.Chat
import com.example.mensajeria.Clases.Mensaje
import com.example.mensajeria.Clases.Usuario_chat
import com.example.mensajeria.R

class ChatAdapter(val context: Context, val res: Int, val chats:ArrayList<Chat>, val id_usuario: Int) :
    RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

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

        fun enlaza(c: Chat) {
            val nombre_chat = itemView.findViewById<TextView>(R.id.banner_nombreChat)
            val frase_chat = itemView.findViewById<TextView>(R.id.banner_fraseChat)
            val imgChat = itemView.findViewById<ImageView>(R.id.banner_imageChat)

            if(c.frase.equals("Chat grupal")){
                nombre_chat.text = c.nombre
            }else {
                if (c.es_contacto == 1) {
                    nombre_chat.text = c.nombre
                } else {
                    nombre_chat.text = c.numero
                }
            }

            frase_chat.text = c.frase

            when(c.foto){
                1 -> imgChat.setImageResource(R.drawable.avataricon1)
                2 -> imgChat.setImageResource(R.drawable.avataricon2)
                3 -> imgChat.setImageResource(R.drawable.avataricon3)
                4 -> imgChat.setImageResource(R.drawable.avataricon4)
                5 -> imgChat.setImageResource(R.drawable.avataricon5)
                6 -> imgChat.setImageResource(R.drawable.groupicon1)
                7 -> imgChat.setImageResource(R.drawable.groupicon2)
                8 -> imgChat.setImageResource(R.drawable.groupicon3)
                9 -> imgChat.setImageResource(R.drawable.chicasdramaticas)
                else -> imgChat.setImageResource(R.drawable.groupicon4)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("id_chat", c.id_chat)
                intent.putExtra("id_usuario", id_usuario)
                intent.putExtra("chat_nuevo", false)
                context.startActivity(intent)
            }
        }
    }
}