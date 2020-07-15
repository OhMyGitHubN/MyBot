package com.telegram.bot

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import java.util.*
import javax.validation.constraints.Digits

class CheckUserInput {
    //    companion object { var flag: Boolean = false }
    private val helloText = """ 
        Привет! Меня зовут ПростБот. Я веду учёт твоих расходов.
        Присылай мне свои траты в форме продукты 250, и я запишу их в категорию 'Продукты' в твоей Google-таблице.
        Список доступных команд можешь посмотреть по:
        /help
                            """.trimIndent()
    private val helpText = """  
            Доступные команды в данный момент:
            /help - список доступных команд.
            /table - ссылка на Google-таблицу.
            /list - список доступных категорий.
                        """.trimIndent()
//    private val googleSheetURL = "https://docs.google.com/spreadsheets/d/1Y5jeX_ox55RgjUbQyDfaNJ8QFJEfjsIN5waS-kdYR0I/edit#gid=0"
    private val googleSheetURL = "https://docs.google.com/spreadsheets/d/1vpVGHoR__VyDvf3pU0BLyGZBa-HwD6x-9oOEyANf1W4/edit#gid=0"

    //    var flag: Boolean = false
    var checker: String = "commands"

    fun checkInput(sendMessage: SendMessage, userInput: String) {
//        flag = true
        checker = "commands"
        if (userInput.isNotEmpty()) {
            when (userInput) {
                "/start" ->
                    sendMessage.text = helloText
                "/help", "Помощь" ->
                    sendMessage.text = helpText
                "/table" ->
                    sendMessage.text = googleSheetURL
                "/list" -> {
                    val list: MutableList<String> = ArrayList()
                    for (t in CategoryTitle.values()) {
                        list.add(t.title)
                    }
//                    sendMessage.text = java.lang.String.join("\n", list)
                    sendMessage.text = list.joinToString("\n")
                }
                "Расходы за этот месяц", "Расходы за прошлый месяц" -> {
                    checker = "expenses"
                } else -> {
                    for (t in CategoryTitle.values()) {
                        if (userInput.capitalize().contains(t.title)) checker = "data"
                        else sendMessage.text = "Что-то не так. Попробуйте снова.\n /help"
                    }
                }
            }
        }
    }

//    fun parseUserInputToCategory(userInput: String): String? = userInput.trim().asSequence()
//            .filter { it.isLetter() }.joinToString("")
//
//    fun parseUserInputToAmount(userInput: String): String? = userInput.trim().asSequence()
//            .filter { !it.isLetter() }.joinToString("")

}