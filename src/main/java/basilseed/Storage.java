package basilseed;

import basilseed.ui.UiError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides persistent storage for tasks.
 * Reads and writes tasks from a file.
 */
public class Storage {
    public static final String DEFAULT_FILE_PATH = "./src/main/java/basilseed/data/tasks.txt";

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

    /**
     * Creates a Storage instance with the default file path.
     */
    public Storage() {
        this.path = Paths.get(DEFAULT_FILE_PATH);
        createFileIfNotExists(DEFAULT_FILE_PATH);
        this.uiError = new UiError();
    }

    /**
     * Creates a Storage instance with a specified file path.
     *
     * @param fileUrl Path to the storage file.
     */
    public Storage(String fileUrl) {
        this.path = Paths.get(fileUrl);
        createFileIfNotExists(fileUrl);
        this.uiError =  new UiError();
    }

    /**
     * Writes a list of strings to storage.
     *
     * @param lines List of strings to write.
     */
    public void write(List<String> lines) {
        try {
            Files.write(this.path, lines);
        } catch (IOException e) {
            this.uiError.displayIoException();
        }
    }

    /**
     * Reads all lines from storage.
     *
     * @return List of strings read from storage.
     */
    public ArrayList<String> read() {
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
