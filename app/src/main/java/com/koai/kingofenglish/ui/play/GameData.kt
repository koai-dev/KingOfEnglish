package com.koai.kingofenglish.ui.play

data class GameData(val level: Int, val question: String, val score: Int)

object GameDataProvider {
    val gameDataList = listOf(
        GameData(level = 1, question = "HAPPY", score = 50),
        GameData(level = 2, question = "SUNNY", score = 60),
        GameData(level = 3, question = "SMILE", score = 70),
        GameData(level = 4, question = "LAUGH", score = 80),
        GameData(level = 5, question = "CHEER", score = 90)
    )
}
