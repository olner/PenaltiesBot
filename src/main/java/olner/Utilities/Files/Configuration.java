package olner.Utilities.Files;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    public String getProperty(String key){
        try {
            var property = new Properties();
            property.load(new FileInputStream("PenaltiesBotFiles/config.properties"));
            return property.getProperty(key);
        } catch (IOException e) {
            System.out.println("Произошла ошибка при получении property");
        }
        return null;
    }
}
