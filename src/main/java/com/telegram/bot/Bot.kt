package com.telegram.bot

import org.apache.log4j.Logger
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import java.io.IOException
import java.security.GeneralSecurityException
import java.time.LocalDate
import java.util.*

class Bot(private val botToken: String?, private val botUserName: String?) : TelegramLongPollingBot() {

    private val googleSheet = GoogleSheet()
    private val checkUserInput = CheckUserInput()
    private var count = 0

    private val log = Logger.getLogger(Bot::class.java)
    private val RECONNECT_PAUSE = 15000

    override fun getBotToken() = botToken
    override fun getBotUsername() = botUserName

    override fun onUpdateReceived(update: Update) {
        log.debug("Receive new Update. updateID: " + update.updateId)

        val chatId = update.message.chatId
        val inputText = update.message.text
//        val message = update.message
        val sendMessage = SendMessage().setChatId(chatId)
        try {
            checkUserInput.checkInput(sendMessage, inputText)
            if (checkUserInput.checker == "commands") {
//                setMyKeyboard(sendMessage)
            } else if (checkUserInput.checker == "expenses") {
                if (inputText.contains("этот", true))
                    sendMessage.text = googleSheet.readData(intMonthToString(LocalDate.now().monthValue))
                else
                    sendMessage.text = googleSheet.readData(intMonthToString(LocalDate.now().monthValue.minus(1)))
            } else {
                val list = inputText.partition { it.isLetter() }.toList()
                if (list.all { it.isNotEmpty() }) {
                    googleSheet.writeData(list[0], list[1].toDouble())
                    sendMessage.text = "Данные добавлены!"
                } else sendMessage.text = "Введены неправильные данные!"
            }
            execute(sendMessage)
        } catch (e: Exception) {
            when (e) {
                is TelegramApiException -> log.error("TelegramApiException")
                is IOException -> log.error("IOException")
                is GeneralSecurityException -> log.error("GeneralSecurityException")
                is RuntimeException -> log.error("RuntimeException")
            }
            e.printStackTrace()
            sendMessage.text = "Произошла ошибка. Попробуйте снова.\n /help"
            execute(sendMessage)
        }
    }

    private fun setMyKeyboard(sendMessage: SendMessage) {
        if (count >= 1) return
        else {
            val replyKeyboardMarkup = ReplyKeyboardMarkup().apply {
                selective = true
                resizeKeyboard = true
                oneTimeKeyboard = true
            }
            sendMessage.replyMarkup = replyKeyboardMarkup
            val keyboard: MutableList<KeyboardRow> = ArrayList()
            val keyboardRow1 = KeyboardRow()
            val keyboardRow2 = KeyboardRow()

            //if(message.equalsIgnoreCase("/menu")) {
            //keyboard.clear();
            //keyboardRow1.clear();
            //keyboardRow2.clear();
            keyboardRow1.add("Помощь")
            keyboardRow2.add("Расходы за этот месяц")
            keyboardRow2.add("Расходы за прошлый месяц")
            //keyboardRow2.add("Выйти");
            keyboard.add(keyboardRow1)
            keyboard.add(keyboardRow2);
            replyKeyboardMarkup.keyboard = keyboard
            count++
        }
    }

    fun botConnect() {
        val telegramBotsApi = TelegramBotsApi()
        try {
            telegramBotsApi.registerBot(this)
            log.info("TelegramAPI started. Look for messages")
        } catch (e: TelegramApiRequestException) {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.message)
            try {
                Thread.sleep(RECONNECT_PAUSE.toLong())
            } catch (e1: InterruptedException) {
                e1.printStackTrace()
                return
            }
            botConnect()
        }
    }

    private fun intMonthToString(monthInt: Int) = when (monthInt) {
        1 -> "January"
        2 -> "February"
        3 -> "March"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "August"
        9 -> "September"
        11 -> "October"
        12 -> "December"
        else -> ""
    }
}