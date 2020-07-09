package com.telegram.bot

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import java.util.*

class CheckUserInput {
//    companion object { var flag: Boolean = false }
    var flag: Boolean = false

    fun checkInput(sendMessage: SendMessage, userInputText: String) {
        flag = true
        if (userInputText.isNotEmpty()) {
            when(userInputText) {
                "/start" ->
                    sendMessage.text = """
                        Привет! Меня зовут ПростБот. Я веду учёт твоих расходов.
                        Присылай мне свои траты в форме продукты 250, и я запишу их в категорию 'продукты' в твоей Google-таблице.
                        Список доступных команд можешь посмотреть по:
                        /help
                        """.trimIndent()
                "/help" ->
                    sendMessage.text = """
                        Доступные команды в данный момент:
                        /help - список доступных команд.
                        /table - ссылка на Google-таблицу.
                        /list - список доступных категорий.
                        """.trimIndent()
                "/table" ->
                    sendMessage.text = "https://docs.google.com/spreadsheets/d/1Y5jeX_ox55RgjUbQyDfaNJ8QFJEfjsIN5waS-kdYR0I/edit#gid=0"
                "/list" -> {
                    val list: MutableList<String> = ArrayList()
                    for (t in CategoryTitle.values()) {
                        list.add(t.title)
                    }
                    sendMessage.text = java.lang.String.join("\n", list)
                }
                else -> {
                    for (t in CategoryTitle.values()) {
                        if (userInputText.capitalize().contains(t.title)) flag = false
                        else sendMessage.text = "Что-то не так. Попробуйте снова.\n /help"
                    }
                }
            }
        }
    }
}