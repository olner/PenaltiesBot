package olner.Bot.Commands;

import lombok.extern.slf4j.Slf4j;
import olner.Bot.BotUtilities.BotUtilities;
import olner.Bot.BotUtilities.Keyboard;
import olner.Utilities.DataBase.SQLCommands;
import olner.Utilities.DataBase.SQLPenaltiesCommands;
import olner.Utilities.Files.Configuration;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;
@Slf4j
public class PenaltiesCommands {
    private final Configuration config = new Configuration();
    private final Keyboard keyboard = new Keyboard();
    private final BotUtilities bot = new BotUtilities();

    private StringBuilder penaltiesStringBuilder(JSONObject penalties) {
        var sb = new StringBuilder();
        if (penalties != null) {
            JSONArray name = (JSONArray) penalties.get("penalty");
            JSONArray date = (JSONArray) penalties.get("date");
            JSONArray sum = (JSONArray) penalties.get("sum");
            JSONArray status = (JSONArray) penalties.get("status");
            Iterator<String> nameIterator = name.iterator();
            Iterator<String> dateIterator = date.iterator();
            Iterator<String> sumIterator = sum.iterator();
            Iterator<String> statusIterator = status.iterator();
            while (nameIterator.hasNext()) {
                sb.append(nameIterator.next());
                sb.append(": ");
                sb.append(dateIterator.next());
                sb.append(", сумма: ");
                sb.append(sumIterator.next());
                sb.append(", статус- ");
                sb.append(statusIterator.next());
                sb.append("\n");
            }
            return sb;
        }
        return null;
    }
    public void penaltiesButton(Update update) {
        var chat = update.getMessage().getChatId().toString();
        bot.sendMessage(
                chat,
                "Выберите нужный вариант",
                keyboard.Keyboard(true,false,false, "Все штрафы", "Активные штрафы", null, "Назад",null),
                null
        );
    }

    public  void getPenalties(Update update) {
        var chat = update.getMessage().getChatId().toString();
        var penalties = SQLPenaltiesCommands.getAllPenalties(chat);
        var sb =  penaltiesStringBuilder(penalties);
        if (sb != null){
            bot.sendMessage(
                    chat,
                    "Ваши штрафы:",
                    null,
                    null
            );
            bot.sendMessage(
                    chat,
                    sb.toString(),
                    null,
                    null
            );
            return;
        }
        bot.sendMessage(
                chat,
                "У вас нет штрафов",
                null,
                null
        );
    }


    public void getActivePenalties(Update update){
        var chat = update.getMessage().getChatId().toString();
        var penalties = SQLPenaltiesCommands.getActivePenalties(chat);
        var sb = penaltiesStringBuilder(penalties);
        if (sb != null){
            bot.sendMessage(
                    chat,
                "Ваши неоплачненые штрафы:",
                null,
                null
            );
            bot.sendMessage(
                    chat,
                    sb.toString(),
                    null,
                    null
            );
            return;
        }
        bot.sendMessage(
                chat,
                "У вас нет неоплаченных штрафов",
                null,
                null
        );
    }
    public void addPenalty(Update update) {
        var chat = update.getMessage().getChatId().toString();
        var command = update.getMessage().getText().replace("@Olegs_test_bot", "").replace("/", "").replace("addpenalty","");
        String[] values = command.split(", ");
        try {
             SQLPenaltiesCommands.addPenalty(values[0], values[1], values[2], values[3], values[4], values[5], values[6]);
            bot.sendMessage(
                    chat,
                    "Штраф был успешно добавлен",
                    null,
                    null
            );
            return;
        } catch(Exception e) {
            log.warn("Ошибка в методе addPenalty");
        }
        bot.sendMessage(
                chat,
                "Произошла ошибка при доюавлении",
                null,
                null
        );
    }

}
