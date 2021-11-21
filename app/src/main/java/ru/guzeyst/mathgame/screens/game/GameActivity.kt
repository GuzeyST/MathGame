package ru.guzeyst.mathgame.screens.game

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ru.guzeyst.mathgame.R
import ru.guzeyst.mathgame.databinding.ActivityGameBinding
import ru.guzeyst.mathgame.screens.options.ObjectOptions

class GameActivity : AppCompatActivity() {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var binding: ActivityGameBinding
    private lateinit var pref: SharedPreferences
    private var listAnswers: Array<TextView?> = arrayOfNulls(4)
    private var rightAnswer = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fillListTV()
        pref = getSharedPreferences(getString(R.string.key_main_options), MODE_PRIVATE)
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
    }


    override fun onPause() {
        saveBestScore()
        super.onPause()
    }

    private fun saveBestScore(){
        if (score > ObjectOptions.bestScore) {
            pref.edit().putInt(getString(R.string.key_best_score), score).apply()
        }
    }

    private fun fillListTV() {
        listAnswers[0] = binding.tvAnswer1
        listAnswers[1] = binding.tvAnswer2
        listAnswers[2] = binding.tvAnswer3
        listAnswers[3] = binding.tvAnswer4
    }

    override fun onStart() {
        super.onStart()
        gameViewModel.timeValue.observe(this, {
            binding.tvTimer.apply {
                if (it.toInt() <= 3) this.setTextColor(resources.getColor(R.color.red))
                else this.setTextColor(resources.getColor(R.color.black))
                this.text = it
                if (it.toInt() == 0) endGame()
            }
        })
        gameViewModel.listData.observe(this, {
            binding.tvTack.text = "${it.quest} " + getString(R.string.tv)
            rightAnswer = it.rightAnswerIndex

            for ((i, n) in listAnswers.withIndex()) {
                listAnswers[i]?.text = it.listAnswers[i]

            }
        })
    }

    private fun startGame(){
        gameViewModel.startGame()
    }

    fun onClickAnswer(view: View) {
        if(listAnswers.indexOf(view as TextView) == rightAnswer){
            score++
            Toast.makeText(this, getString(R.string.right), Toast.LENGTH_SHORT).show()
            gameViewModel.startGame()
        }else{
            endGame()
        }
        binding.tvScore.text = getString(R.string.current_score) + "$score"
    }

    private fun endGame(){
        saveBestScore()
        binding.tvTack.text = getString(R.string.lose_text) + "$score"
        for(tv in listAnswers){
            tv?.visibility = View.INVISIBLE
        }
        binding.tvResumeGame.visibility = View.VISIBLE
    }

    fun onClickResumeGame(view: View) {
        for(tv in listAnswers){
            tv?.visibility = View.VISIBLE
        }
        binding.tvResumeGame.visibility = View.INVISIBLE
        score = 0
        startGame()
    }
}