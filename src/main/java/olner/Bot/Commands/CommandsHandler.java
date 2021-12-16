package olner.Bot.Commands;

import olner.Bot.BotUtilities.BotUtilities;
import olner.Bot.BotUtilities.HashMaps;
import olner.Utilities.DataBase.SQLCommands;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CommandsHandler {
    private final HashMaps hashMaps = new HashMaps();
    private final PenaltiesCommands penaltiesCommands = new PenaltiesCommands();
    private final Commands commands = new Commands();
    private final BotUtilities botUtilities = new BotUtilities();
    private final FirstLaunchCommands firstLaunchCommands = new FirstLaunchCommands();

    public void Command(Update update, String chat, String text) {
        if (!SQLCommands.hasChat(chat)) {
            firstLaunchCommands.FirstStart(chat, update);
        } else if (SQLCommands.hasName(chat)) {
            firstLaunchCommands.setName(update);
        } else if (SQLCommands.hasSurname(chat)) {
            firstLaunchCommands.setSurname(update);
        } else if (SQLCommands.hasPassport(chat)) {
            botUtilities.sendMessage(chat, "Пожалуйста введите пасспорт", null, null);
            commands.setPassport(update);
        } else {
            HashMapCommandsHandler(
                    update,
                    chat,
                    text.toLowerCase().replace("/", "").replace(" ", ""));
        }
    }



    public void HashMapCommandsHandler(Update update, String chat, String command) {
        if (hashMaps.hasCommandMapValue(command)) {
            hashMaps.runCommandMapFunction(command, update);
        } else if (SQLCommands.isAdmin(chat).equals("да")) {
            if (hashMaps.hasAdminCommandMapValue(command)) {
                hashMaps.runAdminCommandMapFunction(command, update);
            } else {
                if (command.contains("addadmin")) {
                    commands.addAdmin(update);
                } else if (command.contains("removeadmin")) {
                    commands.removeAdmin(update);
                } else if (command.contains("addpenalty")) {
                    penaltiesCommands.addPenalty(update);
                }
            }
        }
    }
}
