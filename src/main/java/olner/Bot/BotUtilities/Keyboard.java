package olner.Bot.BotUtilities;

import olner.Utilities.Files.Configuration;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Keyboard {
    private static final Configuration _Config = new Configuration();

    public ReplyKeyboardMarkup Keyboard(Boolean is_row1_2, Boolean is_row1_3, Boolean is_row2_2, String row1, String row1_2, String row1_3, String Row2, String row2_2){
        var replyKeyboardMarkup = new ReplyKeyboardMarkup();
        var keyboard = new ArrayList<KeyboardRow>();
        var keyboardFirstRow = new KeyboardRow();
        var keyboardSecondRow = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        keyboard.clear();
        keyboardFirstRow.clear();
        keyboardSecondRow.clear();
        keyboardFirstRow.add(row1);
        if (is_row1_2) {
            keyboardFirstRow.add(row1_2);
        }
        if (is_row1_3) {
            keyboardFirstRow.add(row1_3);
        }
        keyboardSecondRow.add(Row2);
        if (is_row2_2) {
            keyboardSecondRow.add(row2_2);
        }
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public InlineKeyboardMarkup sponsor() {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText("ПЕРЕЙДИ К СПОНСОРУ💗");
        inlineKeyboardButton.setUrl(_Config.getProperty("sponsor.button"));
        var keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton>();
        keyboardButtonsRow1.add(inlineKeyboardButton);
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardButtonsRow1));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup inlineconstructor(String button1, String callback1, String button2, String callback2) {
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var inlineKeyboardButton1 = new InlineKeyboardButton();
        var inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(button1);
        inlineKeyboardButton1.setCallbackData(callback1);
        inlineKeyboardButton2.setText(button2);
        inlineKeyboardButton2.setCallbackData(callback2);
        var keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton> ();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardButtonsRow1));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup settings() {
        var rows = new ArrayList<List<InlineKeyboardButton>>();
        var inlineKeyboardMarkup = new InlineKeyboardMarkup();
        var inlineKeyboardButton1 = new InlineKeyboardButton();
        var inlineKeyboardButton2 = new InlineKeyboardButton();
        var inlineKeyboardButton3 = new InlineKeyboardButton();
        var inlineKeyboardButton5 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Авто-рассылка✉");
        inlineKeyboardButton1.setCallbackData("Авто-рассылка");
        inlineKeyboardButton2.setText("Рассылка пар✉");
        inlineKeyboardButton2.setCallbackData("Рассылка пар");
        inlineKeyboardButton3.setText("Изменить паспорт");
        inlineKeyboardButton3.setCallbackData("Изменить паспорт");
        inlineKeyboardButton5.setText("Закончить");
        inlineKeyboardButton5.setCallbackData("Завершить");

        var keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton> ();
        var keyboardButtonsRow2 = new ArrayList<InlineKeyboardButton> ();
        var keyboardButtonsRow3 = new ArrayList<InlineKeyboardButton> ();
        var keyboardButtonsRow5 = new ArrayList<InlineKeyboardButton> ();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        rows.add(keyboardButtonsRow1);
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        rows.add(keyboardButtonsRow2);
        keyboardButtonsRow3.add(inlineKeyboardButton3);
        rows.add(keyboardButtonsRow3);

        keyboardButtonsRow5.add(inlineKeyboardButton5);
        rows.add(keyboardButtonsRow5);

        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }
}
