package com.example.protege_dds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.segunda_tela,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usuario = view.findViewById<EditText>(R.id.editUsuario)
        val senha = view.findViewById<EditText>(R.id.editSenha)
        val btnEntrar = view.findViewById<Button>(R.id.btnEntrar)

        btnEntrar.setOnClickListener {
            val nomeUsuario = usuario.text.toString().trim()
            val senhaDigitada = senha.text.toString()

            if (nomeUsuario.isEmpty() || senhaDigitada.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Preencha o usuário e a senha.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Campos preenchidos! O login ainda será configurado.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}