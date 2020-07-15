package com.telegram.bot

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.*
import org.apache.log4j.Logger
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.security.GeneralSecurityException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GoogleSheet {
    private val log = Logger.getLogger(GoogleSheet::class.java)
    private var sheetsService: Sheets? = null
    private val APPLICATION_NAME = "Google Sheet App"
//    private val SPREADSHEET_ID = "1Y5jeX_ox55RgjUbQyDfaNJ8QFJEfjsIN5waS-kdYR0I"
    private val SPREADSHEET_ID = "1vpVGHoR__VyDvf3pU0BLyGZBa-HwD6x-9oOEyANf1W4"


    @Throws(IOException::class, GeneralSecurityException::class)
    private fun authorize(): Credential? {
        val input = this::class.java.classLoader.getResourceAsStream("credentials.json")

        val clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), InputStreamReader(input)
        )

        val scopes = listOf(SheetsScopes.SPREADSHEETS)

        val flow = GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
                clientSecrets, scopes)
                .setDataStoreFactory(FileDataStoreFactory(File("tokens")))
                .setAccessType("offline")
                .build()

        return AuthorizationCodeInstalledApp(
                flow, LocalServerReceiver())
                .authorize("user")

    }

    @Throws(IOException::class, GeneralSecurityException::class)
    fun getSheetsService(): Sheets? {
        val credential: Credential? = authorize()
        return Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build()
    }

    @Throws(IOException::class, GeneralSecurityException::class)
    fun readData(month: String): String {
        sheetsService = getSheetsService()

        //Read DATA
//        val range = "'Sheet1'!A5:C5"
        val range = "'Sheet1'"
        val response: ValueRange? = sheetsService?.spreadsheets()?.values()?.get(SPREADSHEET_ID, range)?.execute()
        val values = response?.getValues()
        val myResponse: StringBuilder = StringBuilder()
        var sum: Map<String, Double?>? = null

        values?.let {
            if (values.isNotEmpty()) {
                sum = it.asSequence().filter { list -> (list[0] as String).contains(month) }
//                        .map { row -> (row[2] as String).toInt().also {
//                                myResponse.append("${row[0]}    ${row[1]}    ${row[2]}    \n") } }
//                        .reduce { it1, it2 -> it1 + it2 }

//                        .groupByTo (HashMap(), { row -> row[1].also {
//                                myResponse.append("${row[0]}    ${row[1]}    ${row[2]}    \n") }},
//                                { row -> (row[2] as String).toInt() })
                        .groupingBy { row -> (row[1] as String).also {
                            myResponse.append(
                                    String.format("%12.17s      %-12.12s    %10.10s%n", row[0], row[1], row[2])) }}
//                            "${row[0]}  ${row[1]}   ${row[2]}\n") }}
                        .aggregate { key, accumulator: Double?, element: List<Any>, first ->
                            if (first) (element[2] as String).toDouble()
                            else accumulator?.plus((element[2] as String).toDouble())
                        }
            }
        }
        if (myResponse.isNotEmpty()) {
            myResponse.append("\nСумма: \n")
            sum?.let {
                for ((k, v) in it.entries) {
                    myResponse.append(String.format("%-18.18s%10.10s\n", k, v)) } }
        } else myResponse.append("Нет данных")
        return myResponse.toString()
    }

    @Throws(IOException::class, GeneralSecurityException::class)
    fun writeData(userCategory: String, userAmount: Double) {
        sheetsService = getSheetsService()
        val date = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.getDefault()).format(LocalDate.now())

        //Write DATA
        val appendBody = ValueRange().setValues(listOf(listOf(date, userCategory, userAmount)))

        val appendResult: AppendValuesResponse? = sheetsService?.spreadsheets()?.values()
                ?.append(SPREADSHEET_ID, "sheet1", appendBody)
                ?.setValueInputOption("USER_ENTERED")
                ?.setInsertDataOption("INSERT_ROWS")
                ?.setIncludeValuesInResponse(true)
                ?.execute()
        log.info("Write data OK")
        //return "OK";
    }

    @Throws(IOException::class, GeneralSecurityException::class)
    fun updateData(): String? {
        sheetsService = getSheetsService()
        //Update row
        val body = ValueRange()
                .setValues(listOf(listOf<Any>("test")))
        val updateResult: UpdateValuesResponse? = sheetsService?.spreadsheets()?.values()
                ?.update(SPREADSHEET_ID, "C3", body)
                ?.setValueInputOption("RAW")
                ?.execute()
        return "OK"
    }

    @Throws(IOException::class, GeneralSecurityException::class)
    fun deleteData(): String? {
        sheetsService = getSheetsService()
        //Delete rows
        val deleteRequest = DeleteDimensionRequest()
                .setRange(DimensionRange()
                        .setSheetId(0)
                        .setDimension("ROWS")
                        .setStartIndex(3)
                )
        val requests: MutableList<Request> = ArrayList()
        requests.add(Request().setDeleteDimension(deleteRequest))
        val body = BatchUpdateSpreadsheetRequest().setRequests(requests)
        sheetsService?.spreadsheets()?.batchUpdate(SPREADSHEET_ID, body)?.execute()
        return "OK"
    }
}