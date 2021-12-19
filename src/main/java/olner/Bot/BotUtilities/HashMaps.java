package olner.Bot.BotUtilities;

import lombok.extern.slf4j.Slf4j;
import olner.Bot.Commands.Commands;
import olner.Bot.Commands.PenaltiesCommands;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Slf4j
public class HashMaps {
    public interface Function {
        void run(Update update);
    }
    private static final HashMap<String, Function> commandMap = new HashMap<>();
    private static final HashMap<String, Function> commandAdminMap = new HashMap<>();
    private final Commands commands = new Commands();
    private final PenaltiesCommands penaltiesCommands = new PenaltiesCommands();

    public void Initialize() {
        commandMap.clear();
        commandAdminMap.clear();
        try {
            commandMap.put("start", commands::StartButton);
            commandMap.put("назад",commands::StartButton);
            commandMap.put("домой🏠", commands::StartButton);
            commandMap.put("settings", commands::SettingsButton);
            commandMap.put("настройки", commands::SettingsButton);
            commandMap.put("настройки⚙", commands::SettingsButton);
            commandMap.put("паспорт",commands::setPassport);
            commandMap.put("штрафы",penaltiesCommands::penaltiesButton);
            commandMap.put("всештрафы", penaltiesCommands::getPenalties);
            commandMap.put("allpenalties", penaltiesCommands::getPenalties);
            commandMap.put("активныештрафы",penaltiesCommands::getActivePenalties);
            commandMap.put("activepenalties",penaltiesCommands::getActivePenalties);
            commandAdminMap.put("commands", commands::CommandsButton);
            commandAdminMap.put("admins", commands::Admins);
            commandMap.put("users", commands::Users);
        } catch (Exception e) {
            log.warn("Произошла ошибка в инициализации команд HashMap! {}", e.getMessage());
        }
    }
    public void runCommandMapFunction(String value, Update update) {
        commandMap.get(value).run(update);
    }

    public boolean hasCommandMapValue(String value) {
        return commandMap.containsKey(value);
    }

    public void runAdminCommandMapFunction(String value, Update update) {
        commandAdminMap.get(value).run(update);
    }

    public boolean hasAdminCommandMapValue(String value) {
        return commandAdminMap.containsKey(value);
    }
}
