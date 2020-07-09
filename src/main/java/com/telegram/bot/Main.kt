package com.telegram.bot

import org.apache.log4j.BasicConfigurator
import org.apache.log4j.Logger
import org.telegram.telegrambots.ApiContextInitializer
import java.io.IOException
import java.security.GeneralSecurityException


    fun main() {
        val log = Logger.getLogger(Bot::class.java)
        try {
            BasicConfigurator.configure()
            ApiContextInitializer.init()
            val bot = Bot("846932094:AAH28OGqA--u9tIM76TF5qPHISnzQpkccug", "@NahalnyBot")
            bot.botConnect()

        } catch (e: IOException) {
            log.error("IOException")
            e.printStackTrace(System.out)
        } catch (e: GeneralSecurityException) {
            log.error("GeneralSecurityException")
            e.printStackTrace(System.out)
        }
    }