package com.david.quizuppro.test.abstraction

import android.util.Log

class Crow: Animal() {
    override fun sound() {
        Log.d("TAG","Kayen Kayen")
    }
}