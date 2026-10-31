package com.example.protege_dds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

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

                return@setOnClickListener
            }

            btnEntrar.isEnabled = false

            Thread {
                var mensagem = "Erro ao conectar com o servidor."
                var sucesso = false

                try {
                    val url = URL(
                        "${BuildConfig.SUPABASE_URL}/functions/v1/login-por-nome"
                    )

                    val conexao = url.openConnection() as HttpURLConnection

                    conexao.requestMethod = "POST"
                    conexao.connectTimeout = 15000
                    conexao.readTimeout = 15000
                    conexao.doOutput = true

                    conexao.setRequestProperty(
                        "Content-Type",
                        "application/json"
                    )

                    conexao.setRequestProperty(
                        "apikey",
                        BuildConfig.SUPABASE_PUBLISHABLE_KEY
                    )

                    conexao.setRequestProperty(
                        "Authorization",
                        "Bearer ${BuildConfig.SUPABASE_PUBLISHABLE_KEY}"
                    )

                    val dados = JSONObject().apply {
                        put("nome", nomeUsuario)
                        put("senha", senhaDigitada)
                    }

                    conexao.outputStream.use { saida ->
                        saida.write(dados.toString().toByteArray(Charsets.UTF_8))
                    }

                    val codigo = conexao.responseCode

                    val resposta = if (codigo in 200..299) {
                        conexao.inputStream.bufferedReader().use { it.readText() }
                    } else {
                        conexao.errorStream?.bufferedReader()?.use {
                            it.readText()
                        } ?: ""
                    }

                    val resultado = JSONObject(resposta)

                    if (codigo in 200..299 &&
                        resultado.optBoolean("sucesso")
                    ) {
                        sucesso = true
                        mensagem = "Login realizado com sucesso!"
                    } else {
                        mensagem = resultado.optString(
                            "erro",
                            "Não foi possível realizar o login."
                        )
                    }

                    conexao.disconnect()

                } catch (e: Exception) {
                    mensagem = "Erro de conexão: ${e.message}"
                }

                activity?.runOnUiThread {
                    btnEntrar.isEnabled = true

                    Toast.makeText(
                        requireContext(),
                        mensagem,
                        Toast.LENGTH_LONG
                    ).show()
                }

            }.start()
        }
    }
}
