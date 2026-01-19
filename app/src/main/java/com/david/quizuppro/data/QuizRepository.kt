package com.david.quizuppro.data

import com.david.quizuppro.model.Category
import com.david.quizuppro.model.Question

object QuizRepository {

    val categories = listOf(
        Category(id = 1, name = "Islamiat", icon = "🕋"),  // Tasbih
        Category(id = 2, name = "Science", icon = "🔬"),   // Microscope
        Category(id = 3, name = "English", icon = "📚"),   // Books
        Category(id = 4, name = "Urdu", icon = "✍️"),      // Writing hand
        Category(id = 5, name = "Math", icon = "🔢"),      // Numbers
        Category(id = 6, name = "Pak Study", icon = "🇵🇰"), // Pakistan flag
        Category(id = 7, name = "Computer", icon = "💻"),  // Laptop
        Category(id = 8, name = "Sindhi", icon = "🗣️")    // Speaking
    )

    private val islamiatQuestions = listOf(
        Question(1, "What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), 2),
        Question(2, "How many continents are there?", listOf("5", "6", "7", "8"), 2),
        Question(3, "What is the largest ocean?", listOf("Atlantic", "Pacific", "Indian", "Arctic"), 1),
        Question(4, "Who painted the Mona Lisa?", listOf("Van Gogh", "Picasso", "Da Vinci", "Monet"), 2),
        Question(5, "What is the smallest country?", listOf("Monaco", "Vatican City", "Malta", "Liechtenstein"), 1),
        Question(6, "How many colors in a rainbow?", listOf("5", "6", "7", "8"), 2),
        Question(7, "What is the tallest mountain?", listOf("K2", "Everest", "Kilimanjaro", "Denali"), 1),
        Question(8, "Which planet is closest to the Sun?", listOf("Venus", "Mercury", "Mars", "Earth"), 1),
        Question(9, "How many days in a leap year?", listOf("365", "366", "364", "367"), 1),
        Question(10, "What is the largest mammal?", listOf("Elephant", "Blue Whale", "Giraffe", "Hippo"), 1)
    )
    private val scienceQuestions = listOf(
        Question(1, "What is H2O?", listOf("Oxygen", "Hydrogen", "Water", "Carbon"), 2),
        Question(2, "How many bones in human body?", listOf("186", "206", "226", "246"), 1),
        Question(3, "What is the speed of light?", listOf("300,000 km/s", "150,000 km/s", "450,000 km/s", "600,000 km/s"), 0),
        Question(4, "What gas do plants absorb?", listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"), 2),
        Question(5, "What is the center of an atom?", listOf("Electron", "Proton", "Neutron", "Nucleus"), 3),
        Question(6, "What is the hardest natural substance?", listOf("Gold", "Iron", "Diamond", "Platinum"), 2),
        Question(7, "How many planets in solar system?", listOf("7", "8", "9", "10"), 1),
        Question(8, "What is the largest organ?", listOf("Heart", "Brain", "Liver", "Skin"), 3),
        Question(9, "What is DNA?", listOf("Protein", "Genetic Material", "Enzyme", "Hormone"), 1),
        Question(10, "What causes tides?", listOf("Sun", "Moon", "Wind", "Earth's rotation"), 1)
    )
    private val englishQuestions = listOf(
        Question(1, "What is H2O?", listOf("Oxygen", "Hydrogen", "Water", "Carbon"), 2),
        Question(2, "How many bones in human body?", listOf("186", "206", "226", "246"), 1),
        Question(3, "What is the speed of light?", listOf("300,000 km/s", "150,000 km/s", "450,000 km/s", "600,000 km/s"), 0),
        Question(4, "What gas do plants absorb?", listOf("Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen"), 2),
        Question(5, "What is the center of an atom?", listOf("Electron", "Proton", "Neutron", "Nucleus"), 3),
        Question(6, "What is the hardest natural substance?", listOf("Gold", "Iron", "Diamond", "Platinum"), 2),
        Question(7, "How many planets in solar system?", listOf("7", "8", "9", "10"), 1),
        Question(8, "What is the largest organ?", listOf("Heart", "Brain", "Liver", "Skin"), 3),
        Question(9, "What is DNA?", listOf("Protein", "Genetic Material", "Enzyme", "Hormone"), 1),
        Question(10, "What causes tides?", listOf("Sun", "Moon", "Wind", "Earth's rotation"), 1)
    )




    private val urduQuestions = listOf(
        Question(1, "What is the capital of France?", listOf("London", "Berlin", "Paris", "Madrid"), 2),
        Question(2, "How many continents are there?", listOf("5", "6", "7", "8"), 2),
        Question(3, "What is the largest ocean?", listOf("Atlantic", "Pacific", "Indian", "Arctic"), 1),
        Question(4, "Who painted the Mona Lisa?", listOf("Van Gogh", "Picasso", "Da Vinci", "Monet"), 2),
        Question(5, "What is the smallest country?", listOf("Monaco", "Vatican City", "Malta", "Liechtenstein"), 1),
        Question(6, "How many colors in a rainbow?", listOf("5", "6", "7", "8"), 2),
        Question(7, "What is the tallest mountain?", listOf("K2", "Everest", "Kilimanjaro", "Denali"), 1),
        Question(8, "Which planet is closest to the Sun?", listOf("Venus", "Mercury", "Mars", "Earth"), 1),
        Question(9, "How many days in a leap year?", listOf("365", "366", "364", "367"), 1),
        Question(10, "What is the largest mammal?", listOf("Elephant", "Blue Whale", "Giraffe", "Hippo"), 1)
    )


    private val mathQuestions = listOf(
        Question(1, "How many players in a soccer team?", listOf("9", "10", "11", "12"), 2),
        Question(2, "Where were the first Olympics?", listOf("Rome", "Athens", "Paris", "London"), 1),
        Question(3, "How many rings in Olympic logo?", listOf("4", "5", "6", "7"), 1),
        Question(4, "What sport is Wimbledon?", listOf("Golf", "Cricket", "Tennis", "Badminton"), 2),
        Question(5, "How many points for a touchdown?", listOf("5", "6", "7", "8"), 1),
        Question(6, "What is a perfect score in bowling?", listOf("200", "250", "300", "350"), 2),
        Question(7, "How long is a marathon?", listOf("26.2 miles", "20 miles", "30 miles", "25 miles"), 0),
        Question(8, "What sport uses a puck?", listOf("Baseball", "Hockey", "Cricket", "Polo"), 1),
        Question(9, "How many Grand Slams in tennis?", listOf("3", "4", "5", "6"), 1),
        Question(10, "What is the diameter of a basketball hoop?", listOf("16 inches", "18 inches", "20 inches", "22 inches"), 1)
    )

    private val pakStudyQuestions = listOf(
        Question(1, "When did World War II end?", listOf("1943", "1944", "1945", "1946"), 2),
        Question(2, "Who discovered America?", listOf("Magellan", "Columbus", "Vespucci", "Cortez"), 1),
        Question(3, "When did the Titanic sink?", listOf("1910", "1911", "1912", "1913"), 2),
        Question(4, "Who was the first US President?", listOf("Jefferson", "Washington", "Adams", "Madison"), 1),
        Question(5, "When did Berlin Wall fall?", listOf("1987", "1988", "1989", "1990"), 2),
        Question(6, "Who built the pyramids?", listOf("Romans", "Greeks", "Egyptians", "Persians"), 2),
        Question(7, "When did WWI start?", listOf("1912", "1913", "1914", "1915"), 2),
        Question(8, "Who was Napoleon?", listOf("King", "Emperor", "General", "President"), 1),
        Question(9, "When was the Renaissance?", listOf("12th-13th century", "14th-17th century", "18th-19th century", "10th-11th century"), 1),
        Question(10, "Who invented the printing press?", listOf("Da Vinci", "Galileo", "Gutenberg", "Newton"), 2)
    )

    private val computerQuestions = listOf(
        Question(
            1,
            "Which of the following is located near the top left corner of the Word 20 13 window?",
            listOf("Title bar", "File menu", "Ribbon", "Font group"),
            1),
        Question(
            2,
            "Which of the following displays the name of the document?",
            listOf("Ribbon", "Title bar","Font group", "Document Window"),
            1),
        Question(
            3,
            "Which of the following contains tabs and commands divided into groups?",
            listOf("File menu", "Title bar", "Document Window","Font group"),
            0),
        Question(
            4,
            "Which of the following keys do you press and hold, while selecting the text?",
            listOf("Tab Key", "Shift Key", "Ctrl Key","Alt Key"),
            1),
        )

    private val sindhiQuestions = listOf(
        Question(1, "What is the capital of Japan?", listOf("Osaka", "Kyoto", "Tokyo", "Nagoya"), 2),
        Question(2, "What is the longest river?", listOf("Amazon", "Nile", "Yangtze", "Mississippi"), 1),
        Question(3, "What is the largest desert?", listOf("Gobi", "Sahara", "Arabian", "Kalahari"), 1),
        Question(4, "How many countries in Africa?", listOf("48", "52", "54", "58"), 2),
        Question(5, "What is the capital of Australia?", listOf("Sydney", "Canberra", "Melbourne", "Brisbane"), 1),
        Question(6, "What is the smallest continent?", listOf("Europe", "Antarctica", "Australia", "South America"), 2),
        Question(7, "What ocean is between US and Europe?", listOf("Pacific", "Atlantic", "Indian", "Arctic"), 1),
        Question(8, "What is the largest country by area?", listOf("Canada", "China", "USA", "Russia"), 3),
        Question(9, "How many time zones in Russia?", listOf("7", "9", "11", "13"), 2),
        Question(10, "What is the capital of Canada?", listOf("Toronto", "Montreal", "Ottawa", "Vancouver"), 2)
    )

    fun getQuestionsForCategory(categoryId: Int): List<Question> {
        return when (categoryId) {
            1 -> islamiatQuestions
            2 -> scienceQuestions
            3 -> englishQuestions
            4 -> urduQuestions
            5 -> mathQuestions
            6 -> pakStudyQuestions
            7 -> computerQuestions
            8 -> sindhiQuestions
            else -> emptyList()
        }
    }
}
