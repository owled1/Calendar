package com.example.kkcalendar

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.kkcalendar.R
import java.text.SimpleDateFormat
import java.util.Date

class PlusFragment : Fragment() {

    private lateinit var btnSave: Button
    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText
    private lateinit var selectedDate: Date
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_plus, container, false)

        btnSave = view.findViewById(R.id.btnSaveSchedule)
        editTitle = view.findViewById(R.id.editScheduleTitle)
        editContent = view.findViewById(R.id.editScheduleContent)

        arguments?.getLong(ARG_SELECTED_DATE)?.let {
            selectedDate = Date(it)
        }

        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        btnSave.setOnClickListener {
            saveTextToCalendar()
        }

        return view
    }

    private fun saveTextToCalendar() {
        val title = editTitle.text.toString()
        val content = editContent.text.toString()

        // SimpleDateFormat을 사용하여 선택된 날짜를 문자열로 변환
        val dateFormat = SimpleDateFormat("yyyyMMdd")
        val dateString = dateFormat.format(selectedDate)

    }

    companion object {
        private const val ARG_SELECTED_DATE = "selected_date"
        private const val PREF_NAME = "calendar_prefs"

        fun newInstance(selectedDate: Long): PlusFragment {
            val fragment = PlusFragment()
            val args = Bundle()
            args.putLong(ARG_SELECTED_DATE, selectedDate)
            fragment.arguments = args
            return fragment
        }
    }

    data class DataModel(
        val entries: MutableList<EntryModel> = mutableListOf()
    ) {
        data class EntryModel(val title: String, val content: String)

        fun addData(title: String, content: String) {
            entries.add(EntryModel(title, content))
        }
    }
}
