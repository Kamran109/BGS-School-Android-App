package com.david.quizuppro.test.palindrome


object StringUtils{
//      Palindrome = ulta seedha same ho — "madam", "racecar"

    fun isPalindrome(str: String): Boolean {
        val reversed = str.reversed()
        return str == reversed
    }

}

