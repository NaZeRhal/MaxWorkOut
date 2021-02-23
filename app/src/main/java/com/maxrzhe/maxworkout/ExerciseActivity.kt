package com.maxrzhe.maxworkout

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maxrzhe.maxworkout.adapter.ExerciseStatusAdapter
import com.maxrzhe.maxworkout.databinding.ActivityExerciseBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityExerciseBinding

    private var restTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var adapter: ExerciseStatusAdapter? = null
    private var rvExerciseStatus: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.tbExercise)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tbExercise.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = Constants.defaultExerciseList()
        tts = TextToSpeech(this, this)

        setupRestView()
        setupExerciseStatusRecyclerView()

    }

    override fun onDestroy() {
        stopTimer(restTimer)
        stopTimer(exerciseTimer)

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player != null) {
            player!!.stop()
        }

        super.onDestroy()
    }

    private fun setRestProgressBar() {
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                binding.pbRest.progress = (millisUntilFinished / 1000).toInt()
                binding.tvTimerRest.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                adapter!!.notifyItemChanged(currentExercisePosition)
                setupExerciseView()
            }
        }
            .start()
    }

    private fun setupRestView() {
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }


        binding.llRestView.visibility = View.VISIBLE
        binding.llExerciseView.visibility = View.GONE
        stopTimer(restTimer)

        binding.tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()

    }

    private fun setExerciseProgressBar() {
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.pbExercise.progress = (millisUntilFinished / 1000).toInt()
                binding.tvTimerExercise.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    adapter!!.notifyItemChanged(currentExercisePosition)
                    setupRestView()
                } else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Congratulations!!!!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
            .start()
    }

    private fun setupExerciseView() {
        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE
        stopTimer(exerciseTimer)
        setExerciseProgressBar()

        val exerciseModel = exerciseList!![currentExercisePosition]
        speakOut(exerciseModel.getName())
        binding.ivExerciseImage.setImageResource(exerciseModel.getImage())
        binding.tvExerciseName.text = exerciseModel.getName()


    }

    private fun stopTimer(timer: CountDownTimer?) {
        timer?.cancel()
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRecyclerView() {
        adapter = ExerciseStatusAdapter(exerciseList!!)
        rvExerciseStatus = binding.rvExerciseStatus
        rvExerciseStatus!!.adapter = adapter
        rvExerciseStatus!!.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "onInit: The language is not supported")
            }
        } else {
            Log.e("TTS", "onInit: Initialisation failed!")
        }
    }
}