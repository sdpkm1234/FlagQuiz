package com.example.nativetest.adapters


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.flagquiz.MainActivity
import com.example.flagquiz.R
import com.example.flagquiz.model.Question


class QuestionAdapter(
    var context: Context,
    private var Questions: ArrayList<Question>,
    FlagImageList: ArrayList<Int>
) :

    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private val imgList = FlagImageList
    private lateinit var cTimer: CountDownTimer
    private var correctAnswers = 0


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rv_question_row, viewGroup, false)
        return ViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") i: Int) {

        val slNo = (i + 1).toString()
        viewHolder.slNo.text = slNo

        viewHolder.imgview.setImageResource(imgList[i])

        viewHolder.op1.background = ContextCompat.getDrawable(context, R.drawable.time_bg)
        viewHolder.op2.background = ContextCompat.getDrawable(context, R.drawable.time_bg)
        viewHolder.op3.background = ContextCompat.getDrawable(context, R.drawable.time_bg)
        viewHolder.op4.background = ContextCompat.getDrawable(context, R.drawable.time_bg)


        viewHolder.op1.text = Questions[i].countries[0].country_name
        viewHolder.op2.text = Questions[i].countries[1].country_name
        viewHolder.op3.text = Questions[i].countries[2].country_name
        viewHolder.op4.text = Questions[i].countries[3].country_name

        viewHolder.op1.setOnClickListener {

            val countryID = Questions[i].countries[0].id

            if (countryID == Questions[i].answer_id && i < Questions.size) {
                showToast("CORRECT")
                viewHolder.op1.setBackground(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.correct_bg
                    )
                )
            } else {
                showToast("WRONG")
                viewHolder.op1.background = ContextCompat.getDrawable(context, R.drawable.wrong_bg)
            }


        }

        viewHolder.op2.setOnClickListener {

            val countryID = Questions[i].countries[1].id

            if (countryID == Questions[i].answer_id && i < Questions.size) {
                showToast("CORRECT")
                viewHolder.op2.background =
                    ContextCompat.getDrawable(context, R.drawable.correct_bg)
            } else {
                showToast("WRONG")
                viewHolder.op2.background = ContextCompat.getDrawable(context, R.drawable.wrong_bg)
            }

        }

        viewHolder.op3.setOnClickListener {

            val countryID = Questions[i].countries[2].id

            if (countryID == Questions[i].answer_id && i < Questions.size) {
                showToast("CORRECT")
                viewHolder.op3.background =
                    ContextCompat.getDrawable(context, R.drawable.correct_bg);
            } else {
                showToast("WRONG")
                viewHolder.op3.background = ContextCompat.getDrawable(context, R.drawable.wrong_bg)
            }


        }

        viewHolder.op4.setOnClickListener {

            val countryID = Questions[i].countries[3].id

            if (countryID == Questions[i].answer_id && i < Questions.size) {
                showToast("CORRECT")
                viewHolder.op4.background =
                    ContextCompat.getDrawable(context, R.drawable.correct_bg)

            } else {
                showToast("WRONG")
                viewHolder.op4.background = ContextCompat.getDrawable(context, R.drawable.wrong_bg)

            }


        }

        cTimer = object : CountDownTimer(31000, 100) {
            override fun onTick(millisUntilFinished: Long) {

                var seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                seconds %= 60
                (context as MainActivity).setTimerText(seconds, minutes, i)
            }

            override fun onFinish() {

                if (i < Questions.size - 1)
                    (context as MainActivity).scrolltoNext(i + 1)
                else
                    (context as MainActivity).gameOver()

            }
        }
        cTimer.start()


    }

    private fun showToast(text: String) {

        val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)

        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)

        val viewGroup = toast.view as ViewGroup?

        val textView = viewGroup!!.getChildAt(0) as TextView

        textView.textSize = 15f

        if (text == "CORRECT") {
            viewGroup.setBackgroundColor(ContextCompat.getColor(context, R.color.correct))
            correctAnswers++

            (context as MainActivity).setScore(correctAnswers)

        } else
            viewGroup.setBackgroundColor(ContextCompat.getColor(context, R.color.outline))

        // Display the Toast
        toast.show()
    }

    override fun getItemCount(): Int {
        return Questions.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgview: ImageView
        var slNo: TextView
        var op1: TextView;
        var op2: TextView;
        var op3: TextView;
        var op4: TextView

        init {
            // getting ImageView reference
            imgview = itemView.findViewById(R.id.imgView)
            slNo = itemView.findViewById(R.id.slNo)
            op1 = itemView.findViewById(R.id.op1)
            op2 = itemView.findViewById(R.id.op2)
            op3 = itemView.findViewById(R.id.op3)
            op4 = itemView.findViewById(R.id.op4)

        }
    }
}