package com.david.quizuppro

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.david.quizuppro.navigation.QuizNavGraph
import com.david.quizuppro.test.Cat
import com.david.quizuppro.test.Dog
import com.david.quizuppro.test.abstraction.Crow
import com.david.quizuppro.test.abstraction.Lion
import com.david.quizuppro.test.palindrome.StringUtils
import com.david.quizuppro.ui.theme.QuizUpProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        INHERITANCE

//        val d = Dog()
//        val c = Cat()
//        d.eat()
//        d.bark()
//        c.eat()
//        c.drinking()

//        ABSTRACTION

//        val l = Lion()
//        l.sound()
//        val cr = Crow()
//        cr.sound()

//      Palindrome = ulta seedha same ho — "madam", "racecar"

        val result1 = StringUtils.isPalindrome("madam")
        val result2 = StringUtils.isPalindrome("hello")
        val result3 = StringUtils.isPalindrome("racecar")

        Log.d("isPalindrome","madam $result1")
        Log.d("isPalindrome","hello $result2")
        Log.d("isPalindrome","racecar $result3")

//        println(isPalindrome("madam"))    // true
//        println(isPalindrome("hello"))    // false
//        println(isPalindrome("racecar"))  // true


//        enableEdgeToEdge()
//        setContent {
//            QuizUpProTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    val navController = rememberNavController()
//                    QuizNavGraph(navController = navController)
//                }
//
//            }
//        }
    }
}
