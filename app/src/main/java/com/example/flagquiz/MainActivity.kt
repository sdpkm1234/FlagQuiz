package com.example.flagquiz

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flagquiz.databinding.ActivityMainBinding
import com.example.flagquiz.model.Question
import com.example.flagquiz.model.flagmodel
import com.example.flagquiz.receiver.MyAlarm
import com.example.flagquiz.utility.SharedPreference
import com.example.flagquiz.utility.getJsonDataFromAsset
import com.example.nativetest.adapters.QuestionAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var alarmMgr: AlarmManager? = null
    private lateinit var alarmIntent: PendingIntent
    private lateinit var cTimer: CountDownTimer
    private var broadcastReceiver: BroadcastReceiver? = null
    private lateinit var rvQuestionsAdapter: QuestionAdapter
    private lateinit var QuestionsList: ArrayList<Question>
    private lateinit var FlagImageList: ArrayList<Int>
    private lateinit var sharedPreference: SharedPreference
    private var isTimerRunning = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initialize()

        registerReceiver()

        addTextWatchers()

        binding.hhmmss.btnSave.setOnClickListener {

            timeValidation()


        }


    }


    private fun registerReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                setCountdownTimer()


            }
        }
        ContextCompat.registerReceiver(
            this,
            broadcastReceiver,
            IntentFilter("com.example.flagquiz"),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    private fun timeValidation() {


        val h1 = binding.hhmmss.h1.text.toString();
        val h2 = binding.hhmmss.h2.text.toString();
        val m1 = binding.hhmmss.m1.text.toString()
        val m2 = binding.hhmmss.m2.text.toString();
        val s1 = binding.hhmmss.s1.text.toString();
        val s2 = binding.hhmmss.s2.text.toString()

        if (h1 == "" || h2 == "") {
            Toast.makeText(applicationContext, "Please enter hours..", Toast.LENGTH_SHORT).show()

        } else if (m1 == "" || m2 == "") {
            Toast.makeText(applicationContext, "Please enter minutes..", Toast.LENGTH_SHORT).show()
        } else if (s1 == "" || s2 == "") {
            Toast.makeText(applicationContext, "Please enter seconds..", Toast.LENGTH_SHORT).show()
        } else {

            val time = h1 + h2 + m1 + m2 + s1 + s2
            val timeFormat = SimpleDateFormat("HHmmss", Locale.US)
            try {
                val inputTime: Date = timeFormat.parse(time)!!
                timeFormat.format(inputTime)

                val currentTimeFormat = SimpleDateFormat("HHmmss", Locale.US)

                val nowTime: String = currentTimeFormat.format(Date())

                val currentTime: Date = currentTimeFormat.parse(nowTime)!!

                if (inputTime.before(currentTime)) {
                    Toast.makeText(applicationContext, "Entered time over..", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    val h = (h1 + h2).toInt()
                    val m = (m1 + m2).toInt()
                    val s = (s1 + s2).toInt()

                    val calendar: Calendar = Calendar.getInstance().apply {
                        timeInMillis = System.currentTimeMillis()
                        set(Calendar.HOUR_OF_DAY, h)
                        set(Calendar.MINUTE, m)
                        set(Calendar.SECOND, s - 20)
                    }


                    alarmMgr?.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)

                    Toast.makeText(applicationContext, "schedule success", Toast.LENGTH_SHORT)
                        .show()
                }


            } catch (e: Exception) {
                Log.e("error", e.toString())
            }


        }


    }

    private fun addTextWatchers() {
        binding.hhmmss.h1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub

                if (s.length == 1) {
                    binding.hhmmss.h2.requestFocus()
                }

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })


        binding.hhmmss.h2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub

                if (s.length == 1) {
                    binding.hhmmss.m1.requestFocus()
                }

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })

        binding.hhmmss.m1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub

                if (s.length == 1) {
                    binding.hhmmss.m2.requestFocus()
                }

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })

        binding.hhmmss.m2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub

                if (s.length == 1) {
                    binding.hhmmss.s1.requestFocus()
                }

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })

        binding.hhmmss.s1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // TODO Auto-generated method stub

                if (s.length == 1) {
                    binding.hhmmss.s2.requestFocus()
                }

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                // TODO Auto-generated method stub
            }

            override fun afterTextChanged(s: Editable) {
                // TODO Auto-generated method stub
            }
        })
    }

    fun scrolltoNext(i: Int) {

        binding.rvQuestions.visibility = View.GONE

        binding.challengeText.visibility = View.VISIBLE
        binding.challengeText.text = getString(R.string.next_question_in_00_10)


        cTimer = object : CountDownTimer(10001, 100) {
            override fun onTick(millisUntilFinished: Long) {

                try {


                    var seconds = millisUntilFinished / 1000
                    val minutes = seconds / 60
                    seconds %= 60
                    val txt =
                        (String.format("%02d", minutes) + ":" + String.format("%02d", seconds))
                    binding.timerText.text = txt
                } catch (e: Exception) {
                    Log.e("error", "Unhandled exception", e)
                }

            }

            override fun onFinish() {

                binding.rvQuestions.visibility = View.VISIBLE

                binding.challengeText.visibility = View.GONE

                binding.rvQuestions.layoutManager?.scrollToPosition(i)

            }
        }
        cTimer.start()


    }

    fun setTimerText(seconds: Long, minutes: Long, i: Int) {

        sharedPreference.putInt("lastQnNo", i)

        isTimerRunning = true
        val txt = (String.format("%02d", minutes) + ":" + String.format("%02d", seconds))
        binding.timerText.text = txt
        sharedPreference.putLong("lastSecond", seconds)

    }

    fun setScore(answers: Int) {
        val lastScore = sharedPreference.getInt("score")
        var correctCount = answers
        if (lastScore != 0) {
            correctCount += lastScore
        }

        sharedPreference.putInt("score", correctCount)

    }

    fun gameOver() {
        isTimerRunning = false
        binding.rvQuestions.visibility = View.GONE
        binding.challengeText.text = getString(R.string.game_over)
        binding.challengeText.visibility = View.VISIBLE

        cTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {


            }

            override fun onFinish() {

                val score = sharedPreference.getInt("score").toString()
                val txt = "SCORE: $score/15"
                binding.challengeText.text = txt

                binding.challengeText.textSize = 25f

                sharedPreference.putInt("score", 0)
                sharedPreference.putBoolean("isTimerRunning", isTimerRunning)

            }
        }
        cTimer.start()
    }


    fun setCountdownTimer() {

        binding.hhmmss.timeLayout.visibility = View.GONE

        binding.timerText.visibility = View.VISIBLE
        binding.challengeText.visibility = View.VISIBLE

        cTimer = object : CountDownTimer(21000, 100) {
            override fun onTick(millisUntilFinished: Long) {

                try {


                    var seconds = millisUntilFinished / 1000
                    val minutes = seconds / 60
                    seconds %= 60
                    val txt =
                        (String.format("%02d", minutes) + ":" + String.format("%02d", seconds))
                    binding.timerText.text = txt
                } catch (e: Exception) {
                    Log.e("error", "Unhandled exception", e)
                }

            }

            override fun onFinish() {


                binding.challengeText.visibility = View.GONE
                binding.rvQuestions.visibility = View.VISIBLE

                setupRecyclerView()

            }
        }
        cTimer.start()

    }

    private fun setupRecyclerView() {
        val lm: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvQuestions.layoutManager = lm
        rvQuestionsAdapter = QuestionAdapter(this, QuestionsList, FlagImageList)
        binding.rvQuestions.adapter = rvQuestionsAdapter


    }


    private fun initialize() {


        FlagImageList = ArrayList(
            mutableListOf(
                R.drawable.nz, R.drawable.aw, R.drawable.ec, R.drawable.py,
                R.drawable.kg,
                R.drawable.pm,
                R.drawable.jp,
                R.drawable.tm,
                R.drawable.ga,
                R.drawable.mq,
                R.drawable.bz,
                R.drawable.cz,
                R.drawable.ae,
                R.drawable.je,
                R.drawable.ls,

                )
        )
        alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(this, MyAlarm::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }



        try {

            val jsonFileString = getJsonDataFromAsset(applicationContext, "response.json")
            if (jsonFileString != null) {
                Log.i("data", jsonFileString)
            }

            val gson = Gson()
            val QuestionType = object : TypeToken<flagmodel>() {}.type

            val questionsModel: flagmodel = gson.fromJson(jsonFileString, QuestionType)
            Log.i("data", questionsModel.toString())

            val questionsList = questionsModel.questions
            QuestionsList = questionsList as ArrayList<Question>

        } catch (e: Exception) {
            Log.e("error", "Unhandled exception", e)
        }

        sharedPreference = SharedPreference(this)

        binding.hhmmss.h1.requestFocus()


    }


    override fun onStart() {
        super.onStart()
        val isTimer = sharedPreference.getBoolean("isTimerRunning")

        val tEnd = System.currentTimeMillis()
        val tStart = sharedPreference.getLong("tStart")

        try {
            val tDelta: Long = tEnd - tStart
            val elapsedSeconds = tDelta / 1000


            if (isTimer) {
                binding.challengeText.visibility = View.GONE
                binding.hhmmss.timeLayout.visibility = View.GONE
                setupRecyclerView()
                binding.rvQuestions.visibility = View.VISIBLE
                binding.timerText.visibility = View.VISIBLE

                val lastQnNo = sharedPreference.getInt("lastQnNo")

                val qnNo: Int = (elapsedSeconds / 30).toInt()

                binding.rvQuestions.layoutManager?.scrollToPosition(lastQnNo + qnNo)


            }
        } catch (e: Exception) {
            Log.e("error", "Unhandled exception", e)
        }
    }

    override fun onStop() {
        super.onStop()

        sharedPreference.putBoolean("isTimerRunning", isTimerRunning)

        val tStart = System.currentTimeMillis()
        sharedPreference.putLong("tStart", tStart)

    }

    override fun onDestroy() {
        super.onDestroy()

        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver)
        }
    }
}