package olner.Bot;

import olner.Bot.BotUtilities.CallBackData;
import olner.Bot.Commands.CommandsHandler;
import olner.Utilities.Files.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    private static final Configuration config = new Configuration();
    private static final CommandsHandler commands = new CommandsHandler();
    private static final CallBackData callBackData = new CallBackData();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            new Thread(() -> commands.Command(update, update.getMessage().getChatId().toString(), update.getMessage().getText())).start();
        } else {
            new Thread(() -> callBackData.callBackDataHandler(update)).start();
        }
    }

    @Override
    public String getBotUsername() {
        return config.getProperty("bot.name");
    }

    @Override
    public String getBotToken() {
        return config.getProperty("bot.token");
    }
}
