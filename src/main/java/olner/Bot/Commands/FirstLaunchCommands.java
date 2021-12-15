package olner.Bot.Commands;

import olner.Bot.BotUtilities.BotUtilities;
import olner.Bot.BotUtilities.Keyboard;
import olner.Utilities.DataBase.SQLCommands;
import olner.Utilities.Files.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Pattern;

public class FirstLaunchCommands extends Commands {
    private static final Configuration config = new Configuration();
    private static final Keyboard keyboard = new Keyboard();
    private static final BotUtilities bot = new BotUtilities();
    private static final Pattern passportRegex = Pattern.compile("\\d{4}\\d{6}");

    public void FirstStart(String chat, Update update) {
        bot.sendMessage(
                chat,
                "Добро пожаловать " + update.getMessage().getFrom().getFirstName() + "\nДля начала работы с ботом пожалуйста введите ваши данные",
                null,
                null
        );
        SQLCommands.addToDb(chat, update.getMessage().getFrom().getUserName() != null ? update.getMessage().getFrom().getUserName() : "Не указано");
        bot.sendMessage(
                chat,
                "Пожалуйста, введите ваше имя",
                null,
                null
        );
    }
    @Override
    public void setName(Update update) {
        var chat = update.getMessage().getChatId().toString();
        var name = update.getMessage().getText();
        SQLCommands.setName(chat,name);
        bot.sendMessage(
                chat,
                " →Имя было успешно сохранено",
                null,
                null
        );
        bot.sendMessage(
                chat,
                "Пожалуйста введите фамилию",
                null,
                null
        );
    }
    @Override
    public void setSurname(Update update){
        var chat = update.getMessage().getChatId().toString();
        var surname = update.getMessage().getText();
        if (SQLCommands.hasChat(chat)) {
            SQLCommands.setSurname(chat,surname);
            bot.sendMessage(
                    chat,
                    " →Фамилия была успешно сохранена",
                    null,
                    null
            );
            bot.sendMessage(
                    chat,
                    "Пожалуйста введите паспорт",
                    null,
                    null
            );
        }
    }
    @Override
    public void setPassport(Update update) {
        var chat = update.getMessage().getChatId().toString();
        var passport_number = update.getMessage().getText();
        if (passportRegex.matcher(passport_number).matches()) {
            SQLCommands.setPassport(chat,passport_number);
            bot.sendMessage(
                    chat,
                    " →Паспорт успешно сохранен!",
                    null,
                    null
            );
            StartButton(update);
        } else {
            bot.sendMessage(
                    chat,
                    " →Паспорт введен неверно!\nПопробуйте снова ッ",
                    null,
                    null
            );
        }
    }
}
