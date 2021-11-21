package ru.guzeyst.mathgame.screens.game

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.guzeyst.mathgame.screens.options.ObjectOptions
import ru.guzeyst.mathgame.utils.MathAnswers

class GameViewModel(application: Application): AndroidViewModel(application){

    val timeValue = MutableLiveData<String>()
    val listData =  MutableLiveData<MathAnswers>()
    private var timer: CountDownTimer? = null

    init{
       startGame()
    }

    private fun setAnswers(){
        listData.value = MathAnswers()
    }

    fun startGame(){
        if(timer!= null) timer?.cancel()
        setAnswers()
        timer = object : CountDownTimer(ObjectOptions.difficulty.toLong(), 1000){
            override fun onTick(p0: Long) {
                timeValue.value = Math.round(p0.toDouble()/1000.0).toString()
            }

            override fun onFinish() {
                //setAnswers()
                //startGame()
            }
        }.start()
    }

}