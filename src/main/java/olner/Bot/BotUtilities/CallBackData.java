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
    private static String _msgToAll;
    private static String _msgToChat;
    private static String _msgChatID;

    public void saveMsgToAll(String msg) {
        _msgToAll = msg;
    }

    public void saveMsgToChat(String msg, String chat) {
        _msgToChat = msg;
        _msgChatID = chat;
    }


    public void callBackDataHandler(Update update) {
        var chat = update.getCallbackQuery().getMessage().getChatId().toString();
        if (SQLCommands.hasChat(chat)) {
            boolean isGroupMessage = update.getCallbackQuery().getMessage().isGroupMessage() || update.getCallbackQuery().getMessage().isSuperGroupMessage();
            int message_id = update.getCallbackQuery().getMessage().getMessageId();

            switch (update.getCallbackQuery().getData()) {
                case "ОтменитьОтправку" -> {
                    botUtilities.editMessageText(
                            chat,
                            message_id,
                            "Отправка оповещения отменена!\n Текст: " + _msgToAll,
                            null
                    );
                }
                case "ОтправитьОповещение" -> {
                    botUtilities.editMessageText(
                            chat,
                            message_id,
                            "Оповещение отправлено!\n Текст: " + _msgToAll,
                            null
                    );
                    sendMessageToAll(_msgToAll, chat, message_id);
                }
                case "Изменить паспорт" -> {
                    SQLCommands.deletePassport(chat);
                    botUtilities.editMessageText(
                            chat,
                            message_id,
                            "Паспорт будет изменен",
                            null
                    );
                }
                case "Завершить" -> editToSettings(chat, message_id, isGroupMessage, null);

                case "Продолжить" -> editToSettings(
                        chat,
                        message_id,
                        isGroupMessage,
                        keyboard.settings()
                );
                case "ОтправитьСообщение" -> {
                    botUtilities.editMessageText(
                            chat,
                            message_id,
                            "Сообщение отправлено в чат " + _msgChatID + "!\n Текст: " + _msgToChat,
                            null
                    );
                    botUtilities.sendMessage(
                            _msgChatID,
                            _msgToChat,
                            null,
                            null
                    );
                }
                case "ОтменитьСообщение" -> {
                    botUtilities.editMessageText(
                            chat,
                            message_id,
                             "Отправка сообщения в чат " + _msgChatID + " отменена!\n Текст: " + _msgToChat,
                            null
                    );
                }

            }
        }
    }

    private void editToSettings(String chat, int message_id, boolean isGroupMessage, InlineKeyboardMarkup keyboard) {
        botUtilities.editMessageText(
                chat,
                message_id,
                "editToSettings",
                keyboard
        );
    }
    private void sendMessageToAll(String msg, String chat, int message_id) {
        if (msg != null) {
            for (int i = 1; i <= SQLCommands.totalChats(); i++) {
                int num = i;
                new Thread(() -> botUtilities.sendMessage(
                        SQLCommands.getChat(num),
                        "▬▬▬ Оповещение ▬▬▬\n" + msg,
                        null,
                        null
                )).start();
            }
        } else {
            botUtilities.editMessageText(
                    chat,
                    message_id,
                    "Произошла ошибка!",
                    null
            );
        }
    }
}
