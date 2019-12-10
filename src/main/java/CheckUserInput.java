import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class checkUserInput {

    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    public String title;
    public boolean flag = true;

    public void checkInput(SendMessage sendMessage, String userInputText) {
        if (!userInputText.isEmpty()) {
            if (userInputText.startsWith("/start")) {
                sendMessage.setText("Привет! Меня зовут ПростБот. Я веду учёт твоих расходов.\n" +
                        "Присылай мне свои траты в форме продукты 250" +
                        ", и я запишу их в категорию 'продукты' в твоей Google-таблице.\n" +
                        "\nСписок доступных команд можешь посмотреть по:\n" +
                        "/help");
                //execute(sendMessage);
            } else if (userInputText.equalsIgnoreCase("/menu")) {
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setText(userInputText);
                //execute(sendMessage);
            } else if (userInputText.equalsIgnoreCase("/help")) {
                sendMessage.setText("\nДоступные команды в данный момент:\n" +
                        "/help - список доступных команд\n" +
                        "/menu - вывод меню\n" +
                        "/table - ссылка на Google-таблицу.");
                //execute(sendMessage);
            } else if (userInputText.equalsIgnoreCase("/table")) {
                sendMessage.setText("https://docs.google.com/spreadsheets/d/1Y5jeX_ox55RgjUbQyDfaNJ8QFJEfjsIN5waS-kdYR0I/edit#gid=0");
                //execute(sendMessage);  //пока так
            } else {
                for (CategoryTitle t : CategoryTitle.values()) {
                    if (userInputText.contains(t.getTitle()) || userInputText.contains(t.getTitle().toLowerCase())) {
                        //System.out.println(t.title);
                        title =  t.getTitle();
                        flag = false;
                    } else {
                        sendMessage.setText("Что-то не так. Попробуйте снова.");
                    }
                }
            }
            }

        }
    }
}
