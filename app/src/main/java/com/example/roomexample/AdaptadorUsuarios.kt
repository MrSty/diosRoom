package com.example.roomexample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomexample.databinding.ActivityMainBinding
import com.example.roomexample.databinding.ItemRvUsuarioBinding

class AdaptadorUsuarios (val listaUsuarios: MutableList<Usuario>, val listener:AdaptadorListener) : RecyclerView.Adapter<AdaptadorUsuarios.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_usuario, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val usuarios = listaUsuarios[position]

        holder.tvUsuario.text = usuarios.usuario
        holder.tvNombre.text = usuarios.nombre

        holder.cvUsuario.setOnClickListener {
            listener.onEditItemClick(usuarios)
        }
        holder.btnBorrar.setOnClickListener {
            listener.onDeleteItemClick(usuarios)
        }
    }
    inner class ViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView){
        var binding = ItemRvUsuarioBinding.bind(itemView)

        val cvUsuario = binding.cvUsuario
        val tvNombre = binding.tvNombre
        val tvUsuario = binding.tvUsuario
        val btnBorrar = binding.btnBorrar
    }
}
