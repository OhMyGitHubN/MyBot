
import org.apache.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private GoogleSheet googleSheet = new GoogleSheet();
    private CheckUserInput checkUserInput = new CheckUserInput();
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    String botToken;
    String botUsername;
    private static final Logger log = Logger.getLogger(Bot.class);
    final int RECONNECT_PAUSE =10000;

    //private String[] data = { "Продукты", "продукты", ""}

    public Bot(String botToken, String botUsername) {
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    public Bot() {
        super();
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotUsername() {
        return botUsername;
    }

    public void onUpdateReceived(Update update) {
        log.debug("Receive new Update. updateID: " + update.getUpdateId());

        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();
        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        checkUserInput.checkInput(sendMessage, inputText);


        /*if (inputText.startsWith("/start")) {
            sendMessage.setChatId(chatId);
            sendMessage.setText("Hello. This is start message");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }*/
        try {
            if(checkUserInput.flag) {
                if(checkUserInput.returnMessage != null)
                    execute(checkUserInput.returnMessage);
                else {
                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                    sendMessage.setText(getMessage(inputText));
                    execute(sendMessage);
                }
            } else {
                googleSheet.writeData(inputText);
                sendMessage.setText("Данные добавлены!");
                execute(sendMessage);
            }
            /*if(!inputText.isEmpty()) {
                if (inputText.startsWith("/start")) {
                    sendMessage.setText("Привет! Меня зовут ПростБот. Я веду учёт твоих расходов.\n" +
                            "Присылай мне свои траты в форме продукты 250" +
                            ", и я запишу их в категорию 'продукты' в твоей Google-таблице.\n" +
                            "\nСписок доступных команд можешь посмотреть по:\n" +
                            "/help");
                    execute(sendMessage);
                } else if (inputText.equalsIgnoreCase("/menu")) {
                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
                    sendMessage.setText(getMessage(inputText));
                    execute(sendMessage);
                } else if (inputText.equalsIgnoreCase("/help")) {
                    sendMessage.setText("\nДоступные команды в данный момент:\n" +
                            "/help - список доступных команд\n" +
                            "/menu - вывод меню\n" +
                            "/table - ссылка на Google-таблицу.");
                    execute(sendMessage);
                } else if (inputText.equalsIgnoreCase("/table")) {
                    sendMessage.setText("https://docs.google.com/spreadsheets/d/1Y5jeX_ox55RgjUbQyDfaNJ8QFJEfjsIN5waS-kdYR0I/edit#gid=0");
                    execute(sendMessage);  //пока так
                *//*} else if() {
*//*
                } else {
                    googleSheet.writeData(inputText);
                    sendMessage.setText("Данные добавлены!");
                    execute(sendMessage);
                    *//*sendMessage.setText("Я не знаю такой команды. Попробуй\n" +
                            "/help)");
                    execute(sendMessage);*//*
                }
            } else {
                sendMessage.setText("Что-то не так. Попробуйте ещё раз!");
                execute(sendMessage);
            }*/
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String message) {
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        //KeyboardRow keyboardRow2 = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        //if(message.equalsIgnoreCase("/menu")) {
            //keyboard.clear();
            //keyboardRow1.clear();
            //keyboardRow2.clear();
            keyboardRow1.add("Расходы за этот месяц");
            keyboardRow1.add("Расходы за всё время");
            //keyboardRow2.add("Выйти");
            keyboard.add(keyboardRow1);
            //keyboard.add(keyboardRow2);
            replyKeyboardMarkup.setKeyboard(keyboard);
        //}
        return "Choose";
    }

    public void botConnect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
            log.info("TelegramAPI started. Look for messages");
        } catch (TelegramApiRequestException e) {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}