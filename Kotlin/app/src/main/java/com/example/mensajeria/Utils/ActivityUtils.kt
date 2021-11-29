package com.example.mensajeria.Utils

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

open class ActivityUtils : AppCompatActivity() {

    fun String.show(c: Context) {
        Toast.makeText(c, this, Toast.LENGTH_LONG).show()
    }

}