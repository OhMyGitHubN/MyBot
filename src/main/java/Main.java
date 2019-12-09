
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws IOException, GeneralSecurityException {


        BasicConfigurator.configure();

        ApiContextInitializer.init();

        Bot bot = new Bot("846932094:AAH28OGqA--u9tIM76TF5qPHISnzQpkccug", "@NahalnyBot");
        bot.botConnect();
    }
}
