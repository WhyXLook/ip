package basilseed;

import basilseed.ui.UiError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;


public class Storage {
    private static final String DEFAULT_FILE_PATH = "./ip/src/main/java/basilseed/data/tasks.txt";

    private Path path;
    private UiError uiError;

    private static void createFileIfNotExists(String filePath) {
        Path path = Paths.get(filePath);
        if (! Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                UiError uiError = new UiError();
                uiError.displayIoException();
            }
        }
    }

    public Storage() {
        this.path = Paths.get(DEFAULT_FILE_PATH);
        createFileIfNotExists(DEFAULT_FILE_PATH);
        this.uiError = new UiError();
    }

    public Storage(String fileUrl) {
        this.path = Paths.get(fileUrl);
        createFileIfNotExists(fileUrl);
        this.uiError =  new UiError();
    }

    public void write(List<String> lines) {
        try {
            Files.write(this.path, lines);
        } catch (IOException e) {
            this.uiError.displayIoException();
        }
    }

    public ArrayList<String> read(){
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(this.path);
        } catch (IOException e) {
            this.uiError.displayIoException();
        }
        ArrayList<String> outLines = new ArrayList<>(lines);
        return outLines;
    }
}
