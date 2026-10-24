container.alpha = 0f
container.translationY = 40f

container.animate()
.alpha(1f)
.translationY(0f)
.setDuration(1200)
.setInterpolator(AccelerateDecelerateInterpolator())
.start()

handler.postDelayed(
abrirProximaTela,
tempoAbertura
)
}

override fun onDestroy() {
    super.onDestroy()
    handler.removeCallbacks(abrirProximaTela)
}
}
