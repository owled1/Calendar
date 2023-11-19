package com.example.kkcalendar


import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var editId: EditText
    private lateinit var editPw: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editId = findViewById(R.id.edit_id)
        editPw = findViewById(R.id.edit_pw)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_register)
        val intent = Intent(this, ActivityCalendar::class.java)
        startActivity(intent)
        btnLogin.setOnClickListener {
            val username = editId.text.toString()
            val password = editPw.text.toString()

            val sharedPreference = getSharedPreferences("account", Context.MODE_PRIVATE)
            val savedId = sharedPreference.getString("id", "")
            val savedPw = sharedPreference.getString("pw", "")
            if (username.isNotEmpty() && password.isNotEmpty() && username == savedId && password == savedPw) {
                val intent = Intent(this, ActivityCalendar::class.java)
                startActivity(intent)
            } else {
                // 로그인 실패 다이얼로그 보여주기
                dialog("fail")
            }

        }

        btnRegister.setOnClickListener {
            val intent = Intent(this, CreateAccountScreen::class.java)
            startActivity(intent)
        }
    }

    fun dialog(type: String) {
        var dialog = AlertDialog.Builder(this)

        if (type.equals("success")) {
            dialog.setTitle("로그인 성공")
            dialog.setMessage("로그인 성공!")
        } else if (type.equals("fail")) {
            dialog.setTitle("로그인 실패")
            dialog.setMessage("아이디와 비밀번호를 확인해주세요")
        }

        var dialog_listener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE ->
                        Log.d(TAG, "")
                }
            }
        }

        dialog.setPositiveButton("확인", dialog_listener)
        dialog.show()
    }
}