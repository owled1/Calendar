package com.lotusending.kkcalendar

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.kkcalendar.R

class SearchFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:Bundle?):View?{
        val view = inflater.inflate(R.layout.frag_search,container, false)
        return view
    }



}
