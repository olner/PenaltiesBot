package olner.Utilities.DataBase;

import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Slf4j
public class SQLPenaltiesCommands {

    public  static JSONObject getAllPenalties(String chat){
        JSONObject penalties = new JSONObject();
        JSONArray name = new JSONArray();
        JSONArray date = new JSONArray();
        JSONArray sum = new JSONArray();
        JSONArray status = new JSONArray();
        var passport = SQLCommands.getPassport(chat);
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT * FROM bot_data.penalties WHERE passport = ?");
            ps.setString(1,passport);
            var rs = ps.executeQuery();
            while(!rs.isLast()){
                    rs.next();
                    name.add(rs.getString("penalty"));
                    date.add(rs.getString("date"));
                    sum.add(rs.getString("sum"));
                    status.add(rs.getString("status"));
                }
                penalties.put("penalty",name);
                penalties.put("date",date);
                penalties.put("sum",sum);
                penalties.put("status",status);
                return penalties;
//            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка getAllPenalties. Код ошибки: {}", e.getErrorCode());
        }
        return null;
    }
    public  static JSONObject getActivePenalties(String chat){
        JSONObject penalties = new JSONObject();
        JSONArray name = new JSONArray();
        JSONArray date = new JSONArray();
        JSONArray sum = new JSONArray();
        JSONArray status = new JSONArray();
        var passport = SQLCommands.getPassport(chat);
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT * FROM bot_data.penalties WHERE passport = ? AND status = ?");
            ps.setString(1,passport);
            ps.setString(2,"не оплачено");
            var rs = ps.executeQuery();
            while(!rs.isLast()){
                rs.next();
                name.add(rs.getString("penalty"));
                date.add(rs.getString("date"));
                sum.add(rs.getString("sum"));
                status.add(rs.getString("status"));
            }
                penalties.put("penalty",name);
                penalties.put("date",date);
                penalties.put("sum",sum);
                penalties.put("status",status);
            return penalties;
        } catch (SQLException e) {
            log.warn("Произошла ошибка getActivePenalties. Код ошибки: {}", e.getErrorCode());
        }
        return null;
    }

    public static JSONObject getUnreadPenalties(){
        JSONObject penalties = new JSONObject();
        JSONArray name = new JSONArray();
        JSONArray date = new JSONArray();
        JSONArray sum = new JSONArray();
        JSONArray status = new JSONArray();
        JSONArray passport =new JSONArray();
        JSONArray id = new JSONArray();
        try {
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("SELECT * FROM bot_data.penalties WHERE readed = ? ");
            ps.setString(1,"нет");
            var rs = ps.executeQuery();
            while(!rs.isLast()){
                rs.next();
                name.add(rs.getString("penalty"));
                date.add(rs.getString("date"));
                sum.add(rs.getString("sum"));
                status.add(rs.getString("status"));
                passport.add(rs.getString("passport"));
                id.add(rs.getString("id"));
            }
            penalties.put("penalty",name);
            penalties.put("date",date);
            penalties.put("sum",sum);
            penalties.put("status",status);
            penalties.put("passport",passport);
            penalties.put("id",id);
            return penalties;
        } catch (SQLException e) {
            log.warn("Произошла ошибка getUnreadPenalties. Код ошибки: {} - 99% - не найденные записи", e.getErrorCode());
        }
        return null;
    }
    public static void changeReadStatus(String id){
        try{
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("UPDATE bot_data.penalties SET readed = ? WHERE id = ?");
            ps.setString(1,"да");
            ps.setInt(2,Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка setSurname. Код ошибки: {}", e.getErrorCode());
        }
    }
    public  static void addPenalty(String passport,String name,String surname,String penalty,String sum,String status,String date){
        try{
            var ps = Objects.requireNonNull(MySQLConnection.getConnection()).prepareStatement("INSERT INTO bot_data.penalties(passport,name,surname,penalty,sum,status,date) VALUES (?,?,?,?,?,?,?)");
            ps.setString(1,passport);
            ps.setString(2,name);
            ps.setString(3,surname);
            ps.setString(4,penalty);
            ps.setString(5,sum);
            ps.setString(6,status);
            ps.setDate(7, Date.valueOf(date));
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка addPenalty. Код ошибки: {}", e.getErrorCode());
        }
    }
}
