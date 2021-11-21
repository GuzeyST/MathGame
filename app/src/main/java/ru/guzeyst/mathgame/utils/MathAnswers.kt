package ru.guzeyst.mathgame.utils

import android.util.Log
import ru.guzeyst.mathgame.screens.options.ObjectOptions

class MathAnswers {
    val listAnswers = arrayOfNulls<String>(4)
    var rightAnswerIndex = 0
    var quest: String = ""

    private val useNegative = ObjectOptions.useNegative
    private var rightAnswer = 0

    init {
        rightAnswerIndex = getNumberRightAnswer()
        quest = getQuestion()
        fillIncorrectAnswers()
    }

    private fun fillIncorrectAnswers() {
        for((i, n) in listAnswers.withIndex()){
            if(i != rightAnswerIndex){
                var rAnsw:Int
                do rAnsw = getRandomeAnswer()
                while (rAnsw == rightAnswer || listAnswers.map { it?.toInt()}.any{it == rAnsw})
                listAnswers[i] = rAnsw.toString()
            }
        }
    }

    private fun getRandomeAnswer(): Int {
        val start = if (!useNegative) 0
        else ObjectOptions.maxNumber*2*(-1)
        return (start..ObjectOptions.maxNumber*2).random()
    }

    private fun getQuestion(): String {
        var firstNumber = getRandomeNumber()
        var secondNumber = getRandomeNumber()

        if (!useNegative && firstNumber < secondNumber){
            val temp = firstNumber
            firstNumber = secondNumber
            secondNumber = temp
        }

        val question = getAnswer(firstNumber, secondNumber)
        listAnswers[rightAnswerIndex] = rightAnswer.toString()
        return question
    }

    private fun getAnswer(firstNumber: Int, secondNumber: Int):String{
        return if((1..2).random() == 1){
            rightAnswer = firstNumber + secondNumber
            "${firstNumber.toStringRepresentation()} + ${secondNumber.toStringRepresentation()}"
        }else{
            rightAnswer = firstNumber - secondNumber
            "${firstNumber.toStringRepresentation()} - ${secondNumber.toStringRepresentation()}"
        }
    }

    private fun Int.toStringRepresentation(): String{
        return if(this < 0) "($this)"
        else this.toString()
    }

    private fun getNumberRightAnswer(): Int {
        return (0..3).random()
    }

    private fun getRandomeNumber(): Int{
        val start = if (!useNegative) 0
        else ObjectOptions.maxNumber*(-1)
        return (start..ObjectOptions.maxNumber).random()
    }
}