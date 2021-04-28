package com.example.habittrackertraining.ui.fragments.createhabit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.habittrackertraining.R
import com.example.habittrackertraining.data.models.Habit
import com.example.habittrackertraining.ui.viewmodels.HabitViewModel
import com.example.habittrackertraining.utils.Calculations
import kotlinx.android.synthetic.main.fragment_create_habit_item.*
import java.util.*

class CreateHabitItem : Fragment(R.layout.fragment_create_habit_item), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private var title = ""
    private var description = ""
    private var drawableSelected = 0
    private var timeStamp = ""

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var cleanDate = ""
    private var cleanTime = ""

    private lateinit var habitViewModel: HabitViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        habitViewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

        btn_confirm.setOnClickListener {
            addHabitToDB()
        }

        pickDateAndTime()

        drawableSelected()

    }

    private fun addHabitToDB() {
        title = et_habitTitle.text.toString()
        description = et_habitDescription.text.toString()

        timeStamp = "$cleanDate $cleanTime"

        if (!title.isEmpty() && !description.isEmpty() && !cleanDate.isEmpty() && !cleanTime.isEmpty() && drawableSelected != 0) {
            val habit = Habit(0, title, description, timeStamp, drawableSelected)

            habitViewModel.addHabit(habit)
            Toast.makeText(context, "新增成功!", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_createHabitItem_to_habitList)
        } else {
            when {
                title.isEmpty() -> {
                    Toast.makeText(context, "請輸入標題", Toast.LENGTH_SHORT).show()
                }
                description.isEmpty() -> {
                    Toast.makeText(context, "請輸入描述", Toast.LENGTH_SHORT).show()
                }
                cleanDate.isEmpty() -> {
                    Toast.makeText(context, "請選擇日期", Toast.LENGTH_SHORT).show()
                }
                cleanTime.isEmpty() -> {
                    Toast.makeText(context, "請選擇時間", Toast.LENGTH_SHORT).show()
                }
                drawableSelected == 0 -> {
                    Toast.makeText(context, "請選擇習慣", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(context, "請填寫所有資料", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun drawableSelected() {
        iv_fastFoodSelected.setOnClickListener {
            iv_fastFoodSelected.isSelected = !iv_fastFoodSelected.isSelected
            drawableSelected = R.drawable.ic_fastfood

            iv_smokingSelected.isSelected = false
            iv_teaSelected.isSelected = false
        }

        iv_smokingSelected.setOnClickListener {
            iv_smokingSelected.isSelected = !iv_smokingSelected.isSelected
            drawableSelected = R.drawable.ic_smoking

            iv_teaSelected.isSelected = false
            iv_fastFoodSelected.isSelected = false
        }

        iv_teaSelected.setOnClickListener {
            iv_teaSelected.isSelected = !iv_teaSelected.isSelected
            drawableSelected = R.drawable.ic_tea

            iv_smokingSelected.isSelected = false
            iv_fastFoodSelected.isSelected = false
        }
    }

    private fun pickDateAndTime() {
        btn_pickDate.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        btn_pickTime.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(context, this, hour, minute, true).show()
        }
    }

    override fun onDateSet(p0: DatePicker?, yearX: Int, monthX: Int, dayX: Int) {
        cleanDate = Calculations.cleanDate(dayX, monthX, yearX)
        tv_dateSelected.text = "日期: $cleanDate"
    }

    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        cleanTime = Calculations.cleanTime(p1, p2)
        tv_timeSelected.text = "時間: $cleanTime"
    }

}