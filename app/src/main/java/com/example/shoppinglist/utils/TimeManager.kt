package com.example.shoppinglist.utils

import android.content.SharedPreferences
import com.example.shoppinglist.const.Const
import com.example.shoppinglist.const.Const.DEF_TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

object TimeManager {
     fun getCurrentTime(): String {
        val formatter = SimpleDateFormat(Const.DEF_TIME_FORMAT, Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    fun getTimeFormat(time: String,defPreference: SharedPreferences): String{
        val defFormatter = SimpleDateFormat(Const.DEF_TIME_FORMAT, Locale.getDefault())
        val defDate =  defFormatter.parse(time)
        val newFormat = defPreference.getString("time_format_key",DEF_TIME_FORMAT)
        val newFormatter = SimpleDateFormat(newFormat, Locale.getDefault())
        return if(defDate != null){
            newFormatter.format(defDate)
        }else{
            time
        }
    }

}