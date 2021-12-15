package olner.Utilities.DataBase;

import lombok.extern.slf4j.Slf4j;
import olner.Utilities.Files.Configuration;

import java.sql.*;
import java.util.Properties;

@Slf4j
public class MySQLConnection {
    private static Connection _con;
    private static final Configuration _Config = new Configuration();

    public static void connect() {
        if (!isConnected()) {
            try {
                Properties connInfo = new Properties();
                connInfo.setProperty("user",  _Config.getProperty("db.login"));
                connInfo.setProperty("password", _Config.getProperty("db.password"));
                connInfo.setProperty("useUnicode","true");
                connInfo.setProperty("characterEncoding","UTF-8");

                _con = DriverManager.getConnection("jdbc:mysql://" + _Config.getProperty("db.host")  + ":" + _Config.getProperty("db.port"), connInfo);

            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Не удалось подключиться к базе данных. Хост: {} | Порт: {} | Логин: {} | Пароль: {} | Код ошибки: {}", _Config.getProperty("db.host"), _Config.getProperty("db.port"), _Config.getProperty("db.login"), _Config.getProperty("db.password"), e.getErrorCode());
            }
        }
    }

    public static boolean isConnected() {
        return (_con != null);
    }

    public static Connection getConnection() {
        return _con;
    }
}
