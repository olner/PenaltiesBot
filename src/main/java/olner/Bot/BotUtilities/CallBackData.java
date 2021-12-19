package olner.Bot.BotUtilities;

import olner.Bot.Commands.Commands;
import olner.Utilities.DataBase.SQLCommands;
import olner.Utilities.Files.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CallBackData {
    private static  final Commands command = new Commands();
    private static final BotUtilities botUtilities = new BotUtilities();
    private static final Keyboard keyboard = new Keyboard();
    private static final Configuration config = new Configuration();

    public void callBackDataHandler(Update update) {
        var chat = update.getCallbackQuery().getMessage().getChatId().toString();
        if (SQLCommands.hasChat(chat)) {
            boolean isGroupMessage = update.getCallbackQuery().getMessage().isGroupMessage() || update.getCallbackQuery().getMessage().isSuperGroupMessage();
            int message_id = update.getCallbackQuery().getMessage().getMessageId();

            switch (update.getCallbackQuery().getData()) {
                case "Изменить паспорт" -> {
                    SQLCommands.deletePassport(chat);
                    botUtilities.editMessageText(
                            chat,
                            message_id,
                            "Пожалуйста введите паспорт",
                            null
                    );
                }
                case "Изменить имя"-> {
                    SQLCommands.deleteName(chat);
                    botUtilities.editMessageText(
                            chat,
                            message_id,
                            "Пожалуйста введите имя",
                            null
                    );
                }
                case "Изменить Фамилию"-> {
                    SQLCommands.deleteSurname(chat);
                    botUtilities.editMessageText(
                            chat,
                            message_id,
                            "Пожалуйста введите фамилию",
                            null
                    );
                }
                case "Завершить" -> editToSettings(chat, message_id, null);

                case "Продолжить" -> editToSettings(
                        chat,
                        message_id,
                        keyboard.settings()
                );
            }
        }
    }

    private void editToSettings(String chat, int message_id,InlineKeyboardMarkup keyboard) {
        botUtilities.editMessageText(
                chat,
                message_id,
                "Настройка завершена",
                keyboard
        );
    }
}
