package com.david.quizuppro.data

import com.david.quizuppro.model.Category
import com.david.quizuppro.model.Question
import com.david.quizuppro.model.Unit

// data/QuizRepository.kt

object QuizRepository {

    val categories = listOf(
        Category(id = 1, name = "Islamiat", icon = "🕋"),
        Category(id = 2, name = "Science", icon = "🔬"),
        Category(id = 3, name = "English", icon = "📚"),
        Category(id = 4, name = "Urdu", icon = "✍️"),
        Category(id = 5, name = "Math", icon = "🔢"),
        Category(id = 6, name = "Pak Study", icon = "🇵🇰"),
        Category(id = 7, name = "Computer", icon = "💻"),
        Category(id = 8, name = "Sindhi", icon = "🗣️")
    )


    fun getUnitById(categoryId: Int, unitId: Int): Unit? {
        return getUnitsForCategory(categoryId).find { it.id == unitId }  // ✅ Correct
    }

    // ➕ Units ka data add karo (category-wise)
    fun getUnitsForCategory(categoryId: Int): List<Unit> {
        return when (categoryId) {
            1 -> listOf(  // Islamiat
                Unit(1, "Basic Beliefs", "Basic Beliefs", "📿", 10),
                Unit(2, "Pillars of Islam", "Pillars of Islam", "🕌", 10),
                Unit(3, "Islamiat Unit-3", "Islamiat Unit-3", "🕌", 10),
                Unit(4, "Islamiat Unit-4", "Islamiat Unit-4", "🕌", 10),
                Unit(5, "Islamiat Unit-5", "Islamiat Unit-5", "🕌", 10)
            )
            2 -> listOf(  // Science
                Unit(1, "Science Unit  1", "Science Unit 1", "📿", 10),
                Unit(2, "Science Unit  2", "Science Unit 2", "🕌", 10),
                Unit(3, "Science Unit  3", "Science Unit 3", "🕌", 10),
                Unit(4, "Science Unit  4", "Science Unit 4", "🕌", 10),
                Unit(5, "Science Unit  5", "Science Unit 5", "🕌", 10),
            )
            3 -> listOf(  // English
                Unit(1, "English Unit 1", "English Unit 1", "📿", 10),
                Unit(2, "English Unit 2", "English Unit 2", "🕌", 10),
                Unit(3, "English Unit 3", "English Unit 3", "🕌", 10),
                Unit(4, "English Unit 4", "English Unit 4", "🕌", 10),
                Unit(5, "English Unit 5", "English Unit 5", "🕌", 10),
            )
            4 -> listOf(  // Urdu
                Unit(1, "Urdu Chapter-1", "Urdu Chapter-1", "📿", 10),
                Unit(2, "Urdu Chapter-2", "Urdu Chapter-2", "🕌", 10),
                Unit(3, "Urdu Chapter-2", "Urdu Chapter-3", "🕌", 10),
                Unit(4, "Urdu Chapter-2", "Urdu Chapter-4", "🕌", 10),
            )
            5 -> listOf(  // Math
                Unit(1, "Math Unit 1", "Math Unit-1", "📿", 10),
                Unit(2, "Math Unit 2", "Math Unit-2", "🕌", 10),
                Unit(3, "Math Unit 3", "Math Unit-3", "🕌", 10),
                Unit(4, "Math Unit 4", "Math Unit-4", "🕌", 10),
                Unit(5, "Math Unit 5", "Math Unit-5", "🕌", 10),
                Unit(6, "Math Unit 6", "Math Unit-6", "🕌", 10),
            )
            6 -> listOf(  // Pak Study
                Unit(1, "Pak Study Unit 1", "Pak Study Unit-1", "📿", 10),
                Unit(2, "Pak Study Unit 2", "Pak Study Unit-2", "🕌", 10),
                Unit(3, "Pak Study Unit 3", "Pak Study Unit-3", "🕌", 10),
                Unit(4, "Pak Study Unit 4", "Pak Study Unit-4", "🕌", 10),
                Unit(5, "Pak Study Unit 5", "Pak Study Unit-5", "🕌", 10),
            )
            7 -> listOf(  // Computer
                Unit(1, "Hardware Basics", "Hardware Basics", "⚙️", 10),
                Unit(2, "Software Basics", "Software Basics", "💻", 10),
                Unit(3, "Customising a Word Document", "Customising a Word Document", "📝", 5),
                Unit(4, "Multimedia Presentations", "Multimedia Presentations", "📊", 10),
                Unit(5, "Electronic Mail", "Electronic Mail", "📧", 10)
            )
            8 -> listOf(  // Sindhi
                Unit(1, "Sindhi Chapter-1", "Sindhi Chapter-1", "📿", 10),
                Unit(2, "Sindhi Chapter-2", "Sindhi Chapter-2", "🕌", 10),
                Unit(3, "Sindhi Chapter-3", "Sindhi Chapter-3", "🕌", 10),
                )
            // Baaki categories ke liye bhi add karo
            else -> emptyList()
        }
    }

    // ➕ Computer Unit 1 ke questions (example)
    private val computerUnit1Questions = listOf(
         Question(
            1,
            "Which of the following displays the name of the document?",
            listOf("Ribbon", "Title bar", "Font group", "Document Window"),
            1,
            unitId = 1
        )
    )

    // ➕ Computer Unit 2 ke questions (example)
    private val computerUnit2Questions = listOf(
        Question(
            1,
            "Which of the following is located near the top left corner of the Word 2013 window?",
            listOf("Title bar", "File menu", "Ribbon", "Font group"),
            1,
            unitId = 2  // ➕ Unit ID add karo
        )
    )

    // ➕ Computer Unit 3 ke questions (example)
    private val computerUnit3Questions = listOf(
        Question(
            1,
            "Which of the following is located near the top left corner of the Word 2013 window?",
            listOf("Title bar", "File menu", "Ribbon", "Font group"),
            1,
            unitId = 3  // ➕ Unit ID add karo
        ),
        Question(
            2,
            "Which of the following displays the name of the document?",
            listOf("Ribbon", "Title bar", "Font group", "Document Window"),
            1,
            unitId = 3
        ),
        Question(
            3,
            "Which of the following contains tabs and commands divided into groups?",
            listOf("File menu", "Title bar", "Document Window", "Font group"),
            0,
            unitId = 3
        ),
        Question(
            4,
            "Which of the following keys do you press and hold, while selecting the text?",
            listOf("Tab Key", "Shift Key", "Ctrl Key", "Alt Key"),
            1,
            unitId = 3
        ),
        Question(
            5,
            "A network that connects computers and other devices in a large area or region is called?",
            listOf("LAN", "MAN", "WAN", "PAN"),
            2,
            unitId = 3
        )
    )

    // ➕ Computer Unit 4 ke questions (example)

    private val computerUnit4Questions = listOf(
        Question(
            1,
            "Which of the following is located near the top left corner of the Word 2013 window?",
            listOf("Title bar", "File menu", "Ribbon", "Font group"),
            1,
            unitId = 3  // ➕ Unit ID add karo
        )
    )
    // ➕ Computer Unit 5 ke questions (example)

    private val computerUnit5Questions = listOf(
        Question(
            1,
            "Which of the following is located near the top left corner of the Word 2013 window?",
            listOf("Title bar", "File menu", "Ribbon", "Font group"),
            1,
            unitId = 3  // ➕ Unit ID add karo
        )
    )

    // ➕ Islamiat Unit 1 ke questions (example)

    private val islamiatUnit1Questions = listOf(
        Question(
            1,
            "Islamiat Unit 1 - Q1",
            listOf("Title bar", "File menu", "Ribbon", "Font group"),
            1,
            unitId = 3  // ➕ Unit ID add karo
        ),
    )

    // ➕ Islamiat Unit 1 ke questions (example)
    private val islamiatUnit2Questions = listOf(
        Question(
            1,
            "Islamiat Unit 2 - Q 1",
            listOf("Title bar", "File menu", "Ribbon", "Font group"),
            1,
            unitId = 3  // ➕ Unit ID add karo
        ),
    )

    // ➕ Science Unit 1 ke questions (example)
    private val scienceUnit1Questions = listOf(
        Question(
            1,
            "Which of the following is located near the top left corner of the Word 2013 window?",
            listOf("Title bar", "File menu", "Ribbon", "Font group"),
            1,
            unitId = 3  // ➕ Unit ID add karo
        ),
    )
    // ➕ Science Unit 2 ke questions (example)
    private val scienceUnit2Questions = listOf(
        Question(
            1,
            "Which of the following is located near the top left corner of the Word 2013 window?",
            listOf("Title bar", "File menu", "Ribbon", "Font group"),
            1,
            unitId = 3  // ➕ Unit ID add karo
        ),
    )



    // ➕ Ab function ko update karo - categoryId AUR unitId dono chahiye
    fun getQuestionsForUnit(categoryId: Int, unitId: Int): List<Question> {
        return when (categoryId) {
            7 -> when (unitId) {  // Computer
                3 -> computerUnit3Questions
                1 -> computerUnit1Questions  // Ye bhi banao
                2 -> computerUnit2Questions  // Ye bhi banao
                else -> emptyList()
            }
            1 -> when (unitId) {  // Islamiat
                1 -> islamiatUnit1Questions
                2 -> islamiatUnit2Questions
                else -> emptyList()
            }
            2 -> when (unitId) {  // Science
                1 -> scienceUnit1Questions
                2 -> scienceUnit2Questions
                else -> emptyList()
            }
            // Baaki categories
            else -> emptyList()
        }
    }
}
