package ru.guzeyst.mathgame.screens.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.guzeyst.mathgame.R
import ru.guzeyst.mathgame.databinding.ActivityMainBinding
import ru.guzeyst.mathgame.screens.game.GameActivity
import ru.guzeyst.mathgame.screens.options.ObjectOptions
import ru.guzeyst.mathgame.screens.options.OptionsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onStartInit()
    }

    private fun onStartInit() {
        val pref = getSharedPreferences(getString(R.string.key_main_options), MODE_PRIVATE)
        val maxNumber = pref.getInt(getString(R.string.key_max_number), 5)
        val useNegative = pref.getBoolean(getString(R.string.key_use_negative), false)
        val difficulty = pref.getInt(getString(R.string.key_difficulty), 30000)
        val bestScore = pref.getInt(getString(R.string.key_best_score), 0)
        ObjectOptions.maxNumber = maxNumber
        ObjectOptions.useNegative = useNegative
        ObjectOptions.difficulty = difficulty
        ObjectOptions.bestScore = bestScore

        binding.tvBestScore.text = getString(R.string.text_best_score)+" $bestScore"
    }

    fun onClickOptions(view: android.view.View) {
        intent = Intent(this, OptionsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        onStartInit()
    }

    fun onClickStartGame(view: android.view.View) {
        intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}