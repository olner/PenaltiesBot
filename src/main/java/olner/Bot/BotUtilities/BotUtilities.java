package olner.Bot.BotUtilities;

import lombok.extern.slf4j.Slf4j;
import olner.Bot.Bot;
import olner.Utilities.DataBase.SQLCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class BotUtilities {
    private static final Bot _bot = new Bot();
    private static final Keyboard _Keyboard = new Keyboard();

    public void sendMessage(String chat, String text, ReplyKeyboardMarkup replyKeyBoard, InlineKeyboardMarkup inlineKeyboard) {
        var message = new SendMessage();
        message.setChatId(chat);
        message.enableHtml(true);
        message.setText(text);
        if (inlineKeyboard != null) {
            message.setReplyMarkup(inlineKeyboard);
        } else if (replyKeyBoard != null) {
            message.setReplyMarkup(replyKeyBoard);
        }
        execute(chat, text, message);
    }

    public void sendAutoMailing(String chat, String text) {
        var message = new SendMessage();
        message.setChatId(chat);
        message.enableHtml(true);
        message.setText(text);
        int random = 1 + (int) (Math.random() * 10);
        if (random<5) {
            message.setReplyMarkup(_Keyboard.sponsor());
        }
        execute(chat, text, message);
    }

    public void editMessageText(String chat, int message_id, String text, InlineKeyboardMarkup inlineKeyboard) {
        var message = new EditMessageText();
        message.setChatId(chat);
        message.setMessageId(message_id);
        message.enableHtml(true);
        message.setText(text);
        if (inlineKeyboard != null) {
            message.setReplyMarkup(inlineKeyboard);
        }
        try {
            _bot.execute(message);
        } catch (TelegramApiException e) {
            log.warn("Ошибка при редактировании сообщения.\nID чата: {} | ID сообщения: {} | Причина: {} |\nТекст: {}", chat, message_id, e.getMessage(), text);
        }
    }

    private void execute(String chat, String text, SendMessage message) {
        try {
            _bot.execute(message);
        } catch (TelegramApiException e) {
            if (e.getMessage().contains("was blocked by the user") || e.getMessage().contains("user is deactivated")) {
                SQLCommands.updateChatStatus(chat, "БОТ ЗАБЛОКИРОВАН");
                log.warn("Бот заблокирован. ID чата: {}", chat);
            } else {
                log.warn("Ошибка при отправке сообщения.\nID чата: {} | Группа пользователя: {} | Причина: {} |\nТекст: {}", chat, SQLCommands.getStatus(chat), e.getMessage(), text);
            }
        }
    }
}
