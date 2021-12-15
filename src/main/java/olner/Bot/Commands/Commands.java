package olner.Bot.Commands;

import olner.Bot.BotUtilities.BotUtilities;
import olner.Bot.BotUtilities.CallBackData;
import olner.Bot.BotUtilities.Keyboard;
import olner.Utilities.DataBase.SQLCommands;
import olner.Utilities.Files.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Pattern;

public class Commands {
    private static final Configuration config = new Configuration();
    private static final Keyboard keyboard = new Keyboard();
    private static final BotUtilities bot = new BotUtilities();
    private static final Pattern passportRegex = Pattern.compile("\\d{4}\\d{6}");

    public void StartButton(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                "Вы вернулись в начало " +update.getMessage().getFrom().getFirstName(),
                keyboard.Keyboard(false,false,false, "Штрафы", null, null, "Настройки⚙",null),
                null
        );
    }
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
                StartButton(update);
    }

    public void setSurname(Update update){
        var chat = update.getMessage().getChatId().toString();
        var surname = update.getMessage().getText();
        if (SQLCommands.hasChat(chat)) {
            SQLCommands.setSurname(chat,surname);
            bot.sendMessage(
                    chat,
                    " →Имя было успешно сохранено",
                    null,
                    null
            );
            StartButton(update);
        }
    }
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
                bot.sendMessage(
                        chat,
                        "Ваши данные были сохранены. Приятного использования бота.",
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
    public void SettingsButton(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                "Настройки \n" + config.getProperty("bot.version"),
                null,
                keyboard.settings()

        );
    }

    public void CommandsButton(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                //TODO запихать комманды
                "Тут должны были быть комманды но их нет ): ",
                null,
                null
        );
    }

//    public void getUsers(Update update) {
//        var chat = update.getMessage().getChatId().toString();
//        bot.sendMessage(
//                chat,
//                "▬▬▬▬ Чаты ▬▬▬▬\n→Общее количество чатов: " + SQLCommands.totalChats() + "\n→Бота заблокировали: " + SQLCommands.totalBlockedChats() + "\n→Бота используют: " + (SQLCommands.totalChats() - SQLCommands.totalBlockedChats()) + "\n→Количество бесед: " + SQLCommands.totalGroupChats() + "\n→Чатов в бане: " + SQLCommands.totalBans(),
//                null,
//                null
//        );
//    }

    public void addAdmin(Update update) {
        var chat = update.getMessage().getChatId().toString();
        var command = update.getMessage().getText().replace("@Olegs_test_bot", "").replace("/", "");
        var user = command.replace("addadmin ", "");
        if (SQLCommands.hasUser(user)) {
            SQLCommands.updateAdmin(user, "да");
            bot.sendMessage(
                    chat,
                    "Администратор: @" + user + " успешно добавлен.",
                    null,
                    null
            );
        } else {
            bot.sendMessage(
                    chat,
                    "Данного пользователя нет в базе данных бота.",
                    null,
                    null
            );
        }
    }

    public void removeAdmin(Update update) {
        var chat = update.getMessage().getChatId().toString();
        var command = update.getMessage().getText().replace("@PKGHScheduleBot", "").replace("/", "");
        var user = command.replace("removeadmin ", "");
        if (SQLCommands.hasUser(user)) {
            SQLCommands.updateAdmin(user, "нет");
            bot.sendMessage(
                    chat,
                    "Администратор: @" + user + " успешно удалён.",
                    null,
                    null
            );
        } else {
            bot.sendMessage(
                    chat,
                    "Данного пользователя нет в базе данных бота.",
                    null,
                    null
            );
        }
    }

    public void Admins(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                SQLCommands.adminList().toString(),
                null,
                null
        );
    }

    public void getMessageToAll(Update update) {
        var chat = update.getMessage().getChatId().toString();
        var command = update.getMessage().getText().toLowerCase().replace("@Olegs_test_bot", "").replace("/", "");

        if (command.equals("all")) {
            bot.sendMessage(
                    chat,
                    "Ваше сообщение пустое.",
                    null,
                    null
            );
        } else {
            var message = update.getMessage().getText().substring(5);
            bot.sendMessage(
                    chat,
                    "<b>Сообщение:</b>\n\n" + message + "\n\nОтправить его?",
                    null,
                    keyboard.inlineconstructor("Отправить" , "Отправить Оповещение", "Отменить" , "Отменить Отправку")
            );
            new CallBackData().saveMsgToAll(message);
        }

    }
    public void Users(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                "▬▬▬▬ Чаты ▬▬▬▬\n→Общее количество чатов: " + SQLCommands.totalChats(),
                null,
                null
        );
    }


}
