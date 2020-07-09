import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GoogleSheet {
    private static Sheets sheetsService;
    private static String APPLICATION_NAME = "Google Sheet App";
    private static String SPREADSHEET_ID = "1Y5jeX_ox55RgjUbQyDfaNJ8QFJEfjsIN5waS-kdYR0I";
//    https://developers.google.com/sheets/api/quickstart/java to get your credentials


    private static Credential authorize() throws IOException, GeneralSecurityException {
        InputStream in = GoogleSheet.class.getResourceAsStream("credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), new InputStreamReader(in)
        );

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
                clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver())
                .authorize("user");

        return credential;
    }

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String readData() throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();
        //Read DATA
         String range = "'Sheet1'!A1:F3";

        ValueRange response = sheetsService.spreadsheets().values()
                .get(SPREADSHEET_ID, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found");
        } else {
            for (List row : values) {
                for(int i = 0; i < row.size(); i++) {
                    //System.out.printf("%s %s from %s\n", row.get(5), row.get(4), row.get(1));
                    System.out.print(row.get(i) + " ");
                }
                System.out.println();
            }
        }
        return "OK";
    }

    public void writeData(String userInput) throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();

        ParseUserInput parseUserInput = new ParseUserInput();
        String category = CheckUserInput.returnString;
        int amount = parseUserInput.parseInput(userInput);
        String date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());

        //Write DATA
         ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.<Object>asList(date, category, amount)
                ));

        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(SPREADSHEET_ID, "sheet1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
        //return "OK";
    }

    public String updateData() throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();
        //Update row
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.<Object>asList("fuck")
                ));

        UpdateValuesResponse updateResult = sheetsService.spreadsheets().values()
                .update(SPREADSHEET_ID, "C3", body)
                .setValueInputOption("RAW")
                .execute();
        return "OK";
    }

    public String deleteData() throws IOException, GeneralSecurityException {
        sheetsService = getSheetsService();
        //Delete rows
        DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
                .setRange(
                        new DimensionRange()
                        .setSheetId(0)
                        .setDimension("ROWS")
                        .setStartIndex(3)
                );

        List<Request> requests = new ArrayList<Request>();
        requests.add(new Request().setDeleteDimension(deleteRequest));

        BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
        sheetsService.spreadsheets().batchUpdate(SPREADSHEET_ID, body).execute();
        return "OK";
    }
}