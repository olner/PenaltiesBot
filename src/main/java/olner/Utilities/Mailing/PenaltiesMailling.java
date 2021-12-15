package olner.Utilities.Mailing;


import olner.Bot.BotUtilities.BotUtilities;
import olner.Utilities.DataBase.SQLCommands;
import olner.Utilities.DataBase.SQLPenaltiesCommands;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;

public class PenaltiesMailling extends TimerTask {
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

@Override
        public void run(){
                var unreadPenalties =SQLPenaltiesCommands.getUnreadPenalties();
                //var sb = new StringBuilder();
                if(unreadPenalties != null){
                        JSONArray name = (JSONArray) unreadPenalties.get("penalty");
                        JSONArray date = (JSONArray) unreadPenalties.get("date");
                        JSONArray sum = (JSONArray) unreadPenalties.get("sum");
                        JSONArray status = (JSONArray) unreadPenalties.get("status");
                        JSONArray passport  =(JSONArray) unreadPenalties.get("passport");
                        JSONArray id = (JSONArray) unreadPenalties.get("id");
                        Iterator<String> nameIterator = name.iterator();
                        Iterator<String> dateIterator = date.iterator();
                        Iterator<String> sumIterator = sum.iterator();
                        Iterator<String> statusIterator = status.iterator();
                        Iterator<String> passportIterator = passport.iterator();
                        Iterator<String> idIterator = id.iterator();
                        new Thread(() -> {
                                while (nameIterator.hasNext()) {
                                        var sb = new StringBuilder();
                                        var chat = SQLCommands.getChatByPassport(passportIterator.next());
                                        sb.append("У вас новые штрафы: \n");
                                        sb.append(nameIterator.next());
                                        sb.append(": ");
                                        sb.append(dateIterator.next());
                                        sb.append(", сумма: ");
                                        sb.append(sumIterator.next());
                                        sb.append(", статус- ");
                                        sb.append(statusIterator.next());
                                        sb.append("\n");
                                        bot.sendMessage(
                                                chat,
                                                sb.toString(),
                                                null,
                                                null
                                        );
                                        SQLPenaltiesCommands.changeReadStatus(idIterator.next());
                                }
                        }).start();
                }
        }
}
