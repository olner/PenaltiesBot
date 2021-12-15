package olner;


import lombok.extern.slf4j.Slf4j;
import olner.Bot.Bot;
import olner.Utilities.DataBase.MySQLConnection;
import olner.Utilities.Files.ExportFiles;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import olner.Bot.BotUtilities.HashMaps;
import olner.Utilities.Mailing.PenaltiesMailling;

import java.util.Timer;

@Slf4j
public class Launcher {

    private static final ExportFiles exportFiles = new ExportFiles();
    private static final HashMaps hashMaps = new HashMaps();

    public void launcher() {
        exportFiles.export();
        MySQLConnection.connect();
        hashMaps.Initialize();
        schedulersStart();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Не удалось инициализировать бота. Производится завершение программы.");
            System.out.println("Не удалось инициализировать бота. Производится завершение программы.");
            System.exit(0);
        }
        try { Thread.sleep(2500); } catch (InterruptedException ignored) {}
        log.info("Бот запущен!");
        System.out.println("Бот запущен");
    }
    private void schedulersStart(){
        int mins30 = 30 * 60 * 1000;
        int mins10 = 10 * 60 * 1000;
        int min = 60 * 1000;
        int sec10 =10 * 1000;
        Timer time = new Timer();
        PenaltiesMailling mailing = new PenaltiesMailling();
        time.schedule(mailing,0,sec10);
    }
}
