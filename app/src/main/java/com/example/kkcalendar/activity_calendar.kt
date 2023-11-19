package com.example.kkcalendar


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.example.kkcalendar.databinding.ActivityCalendarBinding
import com.google.android.material.navigation.NavigationView

import com.lotusending.kkcalendar.SearchFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class ActivityCalendar : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var layoutDrawer: DrawerLayout
    private lateinit var btnMenu: Button
    private lateinit var btnPlus: Button
    private lateinit var datetext: EditText
    private lateinit var textview2: TextView
    private lateinit var event: TextView
    private lateinit var btnSearch: Button
    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutDrawer = binding.layoutDrawer
        btnMenu = binding.btnMenu
        btnPlus = binding.btnPlus
        btnSearch = binding.btnSearch
        datetext = binding.selectDateText
        textview2 = binding.textView2
        event = binding.event

        val dayText: TextView = binding.selectDateText
        val calendar: CalendarView = binding.calendarView2
        val dateFormat: DateFormat = SimpleDateFormat("yyyy년MM월dd일")
        val date: Date = Date(calendar.date)
        dayText.text = dateFormat.format(date)

        calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val day: String = "${month + 1}월 ${dayOfMonth}일" // 월은 0부터 시작하므로 +1
            dayText.text = day
        }

        btnPlus.setOnClickListener {
            setFrag(1)
            datetext.visibility = View.INVISIBLE
            textview2.visibility = View.INVISIBLE
            event.visibility = View.INVISIBLE
        }

        btnSearch.setOnClickListener {
            setFrag(0)
        }

        btnMenu.setOnClickListener {
            layoutDrawer.openDrawer(GravityCompat.START)
        }

        val naviView: NavigationView = binding.btnNaviView
        naviView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.access -> Toast.makeText(applicationContext, "접근성", Toast.LENGTH_SHORT).show()
            R.id.access2 -> Toast.makeText(applicationContext, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
        }
        layoutDrawer.closeDrawer(GravityCompat.START) // 네비 뷰 닫기
        return true
    }

    private fun setFrag(fragNum: Int) {
        val ft = supportFragmentManager.beginTransaction()
        when (fragNum) {
            0 -> ft.replace(R.id.mainframe, SearchFragment()).commitAllowingStateLoss()
            1 -> ft.replace(R.id.mainframe, PlusFragment()).commitAllowingStateLoss()
        }
    }
}
