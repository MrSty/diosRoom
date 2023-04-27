package com.example.roomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.roomexample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AdaptadorListener {

    lateinit var binding: ActivityMainBinding

    var listaUsuario: MutableList<Usuario> = mutableListOf()

    lateinit var adaptador:AdaptadorUsuarios
    lateinit var room:DBroom
    lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsuarios.layoutManager = LinearLayoutManager(this)

        room = Room.databaseBuilder(this, DBroom::class.java, "dbRoom").build()

        obtenerUsuarios(room)

        binding.btnSubmit.setOnClickListener {
            if (binding.tfUsuario?.editText?.text.isNullOrEmpty() || binding.tfUsuario?.editText?.text.isNullOrEmpty()){
                Toast.makeText(this, "Debes llenar los campos", Toast.LENGTH_SHORT ).show()

                return@setOnClickListener
            }
            if (binding.btnSubmit.text.equals("Submit")){
                usuario = Usuario(
                    binding.tfUsuario.editText?.text.toString().trim(),
                    binding.tfNombre.editText?.text.toString().trim()
                )

                agregarUsuarios(room, usuario)

            } else if (binding.btnSubmit.text.equals("Actualizar")){
                usuario.nombre = binding.tfNombre.editText?.text.toString().trim()

                actualizarUsuario(room, usuario)
            }
        }
    }

    private fun actualizarUsuario(room: DBroom, usuario: Usuario) {
        lifecycleScope.launch {
            room.daoUsuario().modificarUsuario(usuario.usuario, usuario.nombre)
            obtenerUsuarios(room)
            limpiarCampos()
        }
    }

    private fun obtenerUsuarios(room: DBroom) {
        lifecycleScope.launch {
            listaUsuario = room.daoUsuario().obtenerUsuarios()
            adaptador = AdaptadorUsuarios(listaUsuario, this@MainActivity)
            binding.rvUsuarios.adapter = adaptador
        }


    }

    fun agregarUsuarios(room: DBroom, usuario: Usuario){
        lifecycleScope.launch {
            room.daoUsuario().agregarUsuario(usuario)
            obtenerUsuarios(room)
            limpiarCampos()
        }
    }

    private fun limpiarCampos() {
        usuario.usuario = ""
        usuario.nombre = ""

        binding.tfUsuario.editText?.setText("")
        binding.tfNombre.editText?.setText("")

        if (binding.btnSubmit.text.equals("Actualizar")){
            binding.btnSubmit.setText("Agregar")
            binding.tfUsuario.editText?.isEnabled = true
        }
    }

    override fun onEditItemClick(usuario: Usuario) {
        binding.btnSubmit.setText("Actualizar")
        binding.tfUsuario.editText?.isEnabled = false

        this.usuario = usuario

        binding.tfUsuario.editText?.setText(this.usuario.usuario)
        binding.tfNombre.editText?.setText(this.usuario.nombre)
    }

    override fun onDeleteItemClick(usuario: Usuario) {
        lifecycleScope.launch {
            room.daoUsuario().eliminarUsuario(usuario.usuario)
            adaptador.notifyDataSetChanged()
            obtenerUsuarios(room)
        }
    }
}