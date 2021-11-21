package ru.guzeyst.mathgame.screens.options

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.guzeyst.mathgame.R
import ru.guzeyst.mathgame.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPref()
        initOptions()
    }

    private fun initOptions() {
        binding.etMaxNumber.setText(ObjectOptions.maxNumber.toString())
        binding.switchNegative.isChecked = ObjectOptions.useNegative
        val rbChecked = when (ObjectOptions.difficulty) {
            10000 -> binding.rbNormal
            5000 -> binding.rbHard
            else -> binding.rbEasy
        }
        rbChecked.isChecked = true
    }

    private fun initPref() {
        preferences = getSharedPreferences(getString(R.string.key_main_options), MODE_PRIVATE)
    }

    fun onClickSave(view: android.view.View) {
        if (binding.etMaxNumber.text.isEmpty()) {
            Toast.makeText(this, getString(R.string.send_error_options), Toast.LENGTH_SHORT).show()
            return
        }

        val maxNumber = binding.etMaxNumber.text.toString().toInt()

        if (maxNumber < 2) {
            Toast.makeText(this, "Нельзя устанавливать значение меньше 4", Toast.LENGTH_SHORT)
                .show()
            return
        }

        val editor = preferences?.edit()
        editor?.let {
            val useNegative = binding.switchNegative.isChecked
            val difficulty = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.rbNormal -> 10000
                R.id.rbHard -> 5000
                else -> 15000
            }

            it.putBoolean(getString(R.string.key_use_negative), useNegative)
            it.putInt(getString(R.string.key_max_number), maxNumber)
            it.putInt(getString(R.string.key_difficulty), difficulty)

            ObjectOptions.maxNumber = maxNumber
            ObjectOptions.useNegative = useNegative
            ObjectOptions.difficulty = difficulty

            it.apply()
        }
        finish()
    }
}