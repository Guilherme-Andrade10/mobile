package com.example.protege_dds

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())

    private val abrirLogin = Runnable {
        if (isAdded) {
            findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.primeira_tela,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logo = view.findViewById<ImageView>(R.id.imgLogo)
        val nome = view.findViewById<TextView>(R.id.txtNomeApp)
        val frase = view.findViewById<TextView>(R.id.txtFrase)

        logo.alpha = 0f
        nome.alpha = 0f
        frase.alpha = 0f

        logo.animate()
            .alpha(1f)
            .setDuration(800)
            .start()

        nome.animate()
            .alpha(1f)
            .setStartDelay(300)
            .setDuration(800)
            .start()

        frase.animate()
            .alpha(1f)
            .setStartDelay(600)
            .setDuration(800)
            .start()

        handler.postDelayed(abrirLogin, 3000)
    }

    override fun onDestroyView() {
        handler.removeCallbacks(abrirLogin)
        super.onDestroyView()
    }
}
