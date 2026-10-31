
package com.example.protege_dds

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class FirstFragment : Fragment() {

    private val handler = Handler(Looper.getMainLooper())

    private val abrirLogin = Runnable {
        if (isAdded) {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.primeira_tela, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgLogo = view.findViewById<View>(R.id.imgLogo)
        val txtNomeApp = view.findViewById<View>(R.id.txtNomeApp)
        val txtFrase = view.findViewById<View>(R.id.txtFrase)

        imgLogo.alpha = 0f
        txtNomeApp.alpha = 0f
        txtFrase.alpha = 0f

        imgLogo.animate().alpha(1f).setDuration(1000).start()
        txtNomeApp.animate().alpha(1f).setDuration(1000).start()
        txtFrase.animate().alpha(1f).setDuration(1000).start()

        handler.postDelayed(abrirLogin, 3000)
    }

    override fun onDestroyView() {
        handler.removeCallbacks(abrirLogin)
        super.onDestroyView()
    }
}
