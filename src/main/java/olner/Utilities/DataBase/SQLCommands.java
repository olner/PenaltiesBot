package olner.Utilities.DataBase;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Objects;

@Slf4j
public class SQLCommands {
    public static void addToDb(String chat, String username) {
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("INSERT INTO bot_data.users (chat, username) VALUES (?,?)");
            ps.setString(1, chat);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка addToDb. Код ошибки: {}", e.getErrorCode());
        }
    }

    public static void updateChatStatus(String chat, String status) {
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("UPDATE bot_data.users SET status = ? WHERE chat = ?");
            ps.setString(1, status);
            ps.setString(2,  chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка updateChatStatus. Код ошибки: {}", e.getErrorCode());
        }
    }

    public static String getStatus(String chat) {
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT status FROM bot_data.users WHERE chat = ?");
            ps.setString(1, String.valueOf(chat));
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка getStatus. Код ошибки: {}", e.getErrorCode());
        }
        return "none";
    }

    public static void updateAdmin(String chat, String admin) {
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("UPDATE bot_data.users SET admin = ? WHERE username = ?");
            ps.setString(1, admin);
            ps.setString(2,  chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка updateAdmin. Код ошибки: {}", e.getErrorCode());
        }
    }

    public static String isAdmin(String chat) {
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT admin FROM bot_data.users WHERE chat = ?");
            ps.setString(1, chat);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("admin");
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка isAdmin. Код ошибки: {}", e.getErrorCode());
        }
        return "нет";
    }
    public static StringBuilder adminList() {
        var msg = new StringBuilder("▬▬▬ Админы ▬▬▬");
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT * FROM bot_data.users WHERE admin = 'да'");
            var rs = ps.executeQuery();
            while(rs.next()) {
                msg.append("\n").append(rs.getInt("ID")).append(" | ").append(rs.getString("Chat")).append(" | ").append(rs.getString("username"));
            }
            return msg;
        } catch (SQLException e) {
            log.warn("Произошла ошибка adminList. Код ошибки: {}", e.getErrorCode());
        }
        return msg.append("Здесь пусто");
    }

    public static Boolean hasUser(String user) {
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT * FROM bot_data.users WHERE UserName = ?");
            ps.setString(1, user);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка hasUser. Код ошибки: {}", e.getErrorCode());
        }
        return false;
    }
    public static boolean hasChat(String chat) {
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT * FROM bot_data.users WHERE chat = ?");
            ps.setString(1, chat);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка hasChat. Код ошибки: {}", e.getErrorCode());
        }
        return false;
    }
    public static int totalChats() {
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT COUNT(chat) AS total FROM bot_data.users");
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка totalChats. Код ошибки: {}", e.getErrorCode());
        }
        return 0;
    }
    public static String getChat(int id) {
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT Chat FROM bot_data.users WHERE id = ?");
            ps.setLong(1, id);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Chat");
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка getChat. Код ошибки: {}", e.getErrorCode());
        }
        return null;
    }
    public  static  void setPassport(String chat, String passport_number){
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("UPDATE bot_data.users SET passport_number = ? WHERE chat = ?");
            ps.setString(1, passport_number);
            ps.setString(2,  chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка setPasport. Код ошибки: {}", e.getErrorCode());
        }
    }
    public static void deletePassport(String chat){
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("UPDATE bot_data.users SET Passport_Number = ? WHERE Chat = ?");
            ps.setString(1, "ЖДЕТ ИЗМЕНЕНИЯ");
            ps.setString(2, chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка deletePassport. Код ошибки: {}", e.getErrorCode());
        }
    }
    public static void deleteName(String chat){
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("UPDATE bot_data.users SET name = ? WHERE Chat = ?");
            ps.setString(1, "не указано");
            ps.setString(2, chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка deleteName. Код ошибки: {}", e.getErrorCode());
        }
    }
    public static void deleteSurname(String chat){
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("UPDATE bot_data.users SET surname = ? WHERE Chat = ?");
            ps.setString(1, "не указано");
            ps.setString(2, chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка deleteSurname. Код ошибки: {}", e.getErrorCode());
        }
    }
    public static boolean hasPassport(String chat){
        try{
            var ps =Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT * FROM bot_data.users WHERE passport_number = ? AND Chat = ?");
            ps.setString(1,"ЖДЕТ ИЗМЕНЕНИЯ");
            ps.setString(2,chat);
            var rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e)
        {
            log.warn("Произошла ошибка hasPassport. Код ошибки: {}", e.getErrorCode());
        }
        return false;
    }
    public static void setName(String chat, String name){
        try{
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("UPDATE bot_data.users SET name = ? WHERE chat = ? ");
            ps.setString(1,name);
            ps.setString(2,chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка setName. Код ошибки: {}", e.getErrorCode());
        }
    }
    public static boolean hasName(String chat) {
        try{
            var ps =Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT * FROM bot_data.users WHERE chat = ? AND name = ? ");
            ps.setString(1,chat);
            ps.setString(2,"не указано");
            var rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e)
        {
            log.warn("Произошла ошибка hasName. Код ошибки: {}", e.getErrorCode());
        }
        return false;
    }
    public static void setSurname(String chat, String surname){
        try{
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("UPDATE bot_data.users SET surname = ? WHERE chat = ? ");
            ps.setString(1,surname);
            ps.setString(2,chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка setSurname. Код ошибки: {}", e.getErrorCode());
        }
    }
    public static boolean hasSurname(String chat) {
        try{
            var ps =Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT surname FROM bot_data.users WHERE chat = ? AND surname = ?");
            ps.setString(1,chat);
            ps.setString(2,"не указано");
            var rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e)
        {
            log.warn("Произошла ошибка hasSurname. Код ошибки: {}", e.getErrorCode());
        }
        return false;
    }
    public static String getPassport(String chat){
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT passport_number FROM bot_data.users WHERE chat = ?");
            ps.setString(1, String.valueOf(chat));
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("passport_number");
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка getPassport. Код ошибки: {}", e.getErrorCode());
        }
        return "none";
    }
    public static String getName(String chat){
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT name FROM bot_data.users WHERE chat = ?");
            ps.setString(1, String.valueOf(chat));
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка getName. Код ошибки: {}", e.getErrorCode());
        }
        return "none";
    }
    public static String getSurname(String chat){
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT surname FROM bot_data.users WHERE chat = ?");
            ps.setString(1, String.valueOf(chat));
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("surname");
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка getSurname. Код ошибки: {}", e.getErrorCode());
        }
        return "none";
    }
    public static String getChatByPassport(String passport){
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT chat FROM bot_data.users WHERE passport_number = ?");
            ps.setString(1, passport);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("chat");
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка getChatByPassport. Код ошибки: {}", e.getErrorCode());
        }
        return "none";
    }
}
