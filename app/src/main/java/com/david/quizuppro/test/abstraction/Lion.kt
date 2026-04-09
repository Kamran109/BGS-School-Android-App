package com.david.quizuppro.test.abstraction

import android.util.Log

class Lion: Animal() {
    override fun sound() {
        Log.d("TAG","Dhooond")
    }
}