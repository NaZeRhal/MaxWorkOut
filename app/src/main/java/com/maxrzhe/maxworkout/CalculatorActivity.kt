package com.maxrzhe.maxworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.maxrzhe.maxworkout.databinding.ActivityCalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorActivity : AppCompatActivity() {

    private enum class UnitSystem {
        METRIC, US
    }

    private lateinit var binding: ActivityCalculatorBinding

    private var currentUnitSystem: UnitSystem = UnitSystem.METRIC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.tbBmiCalc)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "CALCULATE BMI"

        binding.tbBmiCalc.setNavigationOnClickListener {
            onBackPressed()
        }
        binding.btnCalculateBmi.setOnClickListener {
            when (currentUnitSystem) {
                UnitSystem.METRIC -> {
                    when {
                        validateMetricUnits() -> displayBmiResult(calculateMetricBmi())
                        else -> showShortToast("Please, enter valid values.")
                    }
                }
                UnitSystem.US -> {
                    when {
                        validateUsUnits() -> displayBmiResult(calculateUsBmi())
                        else -> showShortToast("Please, enter valid values.")
                    }
                }
            }
        }

        makeVisibleMetricUnitsView()

        binding.rgUnits.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbMetricUnits.id -> makeVisibleMetricUnitsView()
                else -> makeVisibleUsUnitsView()
            }
        }


    }

    private fun calculateMetricBmi(): Float {
        var bmi: Float
        with(binding) {
            val height: Float = etMetricUnitHeight.text.toString().toFloat() / 100
            val weight: Float = etMetricUnitWeight.text.toString().toFloat()
            bmi = weight / (height * height)
        }
        return bmi
    }

    private fun calculateUsBmi(): Float {
        var bmi: Float
        with(binding) {
            val heightFeet: Float = etUsUnitHeightFeet.text.toString().toFloat()
            val heightInch: Float = etUsUnitHeightInch.text.toString().toFloat()
            val weight: Float = etUsUnitWeight.text.toString().toFloat()

            val height = heightFeet * 12 + heightInch

            bmi = 703 * (weight / (height * height))
        }
        return bmi
    }

    private fun displayBmiResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiRounded = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        with(binding) {

            llDisplayBmiResult.visibility = View.VISIBLE

            tvBmiValue.text = bmiRounded
            tvBmiDescription.text = bmiDescription
            tvBmiType.text = bmiLabel
        }
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true
        with(binding) {
            when {
                etMetricUnitWeight.text.toString().isEmpty() -> isValid = false
                etMetricUnitHeight.text.toString().isEmpty() -> isValid = false
            }
        }
        return isValid

    }

    private fun validateUsUnits(): Boolean {
        var isValid = true
        with(binding) {
            when {
                etUsUnitWeight.text.toString().isEmpty() -> isValid = false
                etUsUnitHeightFeet.text.toString().isEmpty() -> isValid = false
                etUsUnitHeightInch.text.toString().isEmpty() -> isValid = false
            }
        }
        return isValid

    }

    private fun makeVisibleMetricUnitsView() {
        currentUnitSystem = UnitSystem.METRIC
        with(binding) {
            tilMetricUnitHeight.visibility = View.VISIBLE
            tilMetricUnitWeight.visibility = View.VISIBLE

            etMetricUnitHeight.text!!.clear()
            etMetricUnitWeight.text!!.clear()

            tilUsUnitWeight.visibility = View.GONE
            llUsUnitsHeight.visibility = View.GONE

            llDisplayBmiResult.visibility = View.INVISIBLE
        }
    }

    private fun makeVisibleUsUnitsView() {
        currentUnitSystem = UnitSystem.US
        with(binding) {
            tilMetricUnitHeight.visibility = View.GONE
            tilMetricUnitWeight.visibility = View.GONE

            etUsUnitHeightInch.text!!.clear()
            etUsUnitHeightFeet.text!!.clear()
            etUsUnitWeight.text!!.clear()

            tilUsUnitWeight.visibility = View.VISIBLE
            llUsUnitsHeight.visibility = View.VISIBLE

            llDisplayBmiResult.visibility = View.INVISIBLE
        }
    }

    private fun showShortToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}