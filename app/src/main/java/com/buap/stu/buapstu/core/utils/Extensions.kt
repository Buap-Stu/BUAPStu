package com.buap.stu.buapstu.core.utils

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.buap.stu.buapstu.LoginActivity
import java.text.SimpleDateFormat
import java.util.*

fun<T> Activity.launchActivity(
    activity: Class<T>,
    clearStack: Boolean,
    finishActivity:Boolean
){
    val intent = Intent(this, activity)
    if(clearStack) intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    if(finishActivity) finish()
}

fun Activity.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Long.utcToFormat(format: String): String{
    val sdf = SimpleDateFormat(format, Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    return sdf.format(this)
}