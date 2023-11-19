package com.example.kkcalendar

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class CreateAccountScreen : AppCompatActivity() {
    private var isExistBlank = false
    private var isPWSame = false
    private lateinit var btnRegister: Button
    private lateinit var edit_nickname: EditText
    private lateinit var edit_username: EditText
    private lateinit var edit_password: EditText
    private lateinit var edit_password_match: EditText
    private lateinit var btnDuplicatecheck: Button


    companion object {
        const val TAG = "CreateAccountScreen"
        const val DIALOG_BLANK = "blank"
        const val DIALOG_NOT_SAME = "not_same"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        btnDuplicatecheck = findViewById(R.id.btnDuplicatecheck)
        btnRegister = findViewById(R.id.btnRegister)
        edit_nickname = findViewById(R.id.edit_nickname)
        edit_username = findViewById(R.id.edit_username)
        edit_password = findViewById(R.id.edit_password)
        edit_password_match = findViewById(R.id.edit_password_match)

        btnDuplicatecheck.setOnClickListener {
            val username = edit_username.text.toString()
            val sharedPreference = getSharedPreferences("account", Context.MODE_PRIVATE)
            val savename = sharedPreference.getString("name", "")
            val allEntries: Map<String, *> = sharedPreference.all

            for ((key, value) in allEntries) {
                Log.d("SharedPreferences", "$key: $value")
            }

            Log.d(TAG, "$username   $savename")
            if (username.isNotEmpty() && username == savename) {

                dialog("duplication")
            } else {
                dialog("success")
            }

        }

        btnRegister.setOnClickListener {
            Log.d(TAG, "회원가입 버튼 클릭")

            val nickname = edit_nickname.text.toString()
            val username = edit_username.text.toString()
            val password = edit_password.text.toString()
            val password_re = edit_password_match.text.toString()

            isExistBlank = false
            isPWSame = false

            if (nickname.isEmpty() || username.isEmpty() || password.isEmpty() || password_re.isEmpty()) {
                isExistBlank = true
            } else {
                isPWSame = password == password_re
            }

            if (!isExistBlank && isPWSame) {
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()

                val sharedPreference = getSharedPreferences("account", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                editor.putString("nick", nickname)
                editor.putString("id", username)
                editor.putString("pw", password)
                editor.apply()

                // 회원가입 즉시 이동할거면 MainActivity를 ActivityCalendar로 교체
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else {
                if (isExistBlank) {
                    dialog(DIALOG_BLANK)
                } else if (!isPWSame) {
                    dialog(DIALOG_NOT_SAME)
                }
            }
        }
    }

    private fun dialog(type: String) {
        val dialog = AlertDialog.Builder(this)
        if (type.equals("success")) {
            dialog.setTitle("중복되지 않은 아이디 입니다.")
            dialog.setMessage("중복되지 않은 아이디 입니다.")
        } else if (type.equals("duplication")) {
            dialog.setTitle("중복된 아이디입니다.")
            dialog.setMessage("중복된 아이디입니다.")
        }
        else if (type == DIALOG_BLANK) {
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("입력란을 모두 작성해주세요")
        }
        else if (type == DIALOG_NOT_SAME) {
            dialog.setTitle("회원가입 실패")
            dialog.setMessage("비밀번호가 다릅니다")
        }

        val dialogListener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        Log.d(TAG, "다이얼로그 확인 버튼 클릭")
                    }
                }
            }
        }

        dialog.setPositiveButton("확인", dialogListener)
        dialog.show()
    }
}

