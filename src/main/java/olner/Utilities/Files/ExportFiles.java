package olner.Utilities.Files;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

@Slf4j
public class ExportFiles {

    public void export() {
        var config = new File(getClass().getClassLoader().getResource("config.properties").getFile());
        try {
            copyFile(new File("PenaltiesBotFiles/config.properties"),config);
        } catch (IOException e) {
            log.warn("Произошла ошибка при копировании конфига: {}", e.getMessage());
        }
    }

    private static void copyFile(File sourceFile, File destFile) throws IOException {
        if (! sourceFile.exists()) {
            return;
        }
        if (! destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }

    }
}
