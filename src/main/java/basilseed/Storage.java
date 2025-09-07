package basilseed;

import basilseed.ui.UiError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import basilseed.exception.BasilSeedIOException;

/**
 * Provides persistent storage for tasks.
 * Reads and writes tasks from a file.
 */
public class Storage {
    public static final String DEFAULT_FILE_PATH = "./src/main/java/basilseed/data/tasks.txt";
    private static final String IOError = String.format("Something went Wrong with IO. Check your perms and file path!\n" +
            "Default is at %s\n", DEFAULT_FILE_PATH);
    private Path path;

    private static void createFileIfNotExists(String filePath) throws BasilSeedIOException {
        Path path = Paths.get(filePath);
        if (! Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new BasilSeedIOException(IOError);
            }
        }
    }

    /**
     * Creates a Storage instance with the default file path.
     */
    public Storage() throws BasilSeedIOException {
        this.path = Paths.get(DEFAULT_FILE_PATH);
        createFileIfNotExists(DEFAULT_FILE_PATH);
    }

    /**
     * Creates a Storage instance with a specified file path.
     *
     * @param fileUrl Path to the storage file.
     */
    public Storage(String fileUrl) throws BasilSeedIOException {
        this.path = Paths.get(fileUrl);
        createFileIfNotExists(fileUrl);
    }

    /**
     * Writes a list of strings to storage.
     *
     * @param lines List of strings to write.
     */
    public void write(List<String> lines) throws BasilSeedIOException {
        try {
            Files.write(this.path, lines);
        } catch (IOException e) {
            throw new BasilSeedIOException(IOError);
        }
    }

    /**
     * Reads all lines from storage.
     *
     * @return List of strings read from storage.
     */
    public ArrayList<String> read() throws BasilSeedIOException {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(this.path);
        } catch (IOException e) {
            throw new BasilSeedIOException(IOError);
        }
        ArrayList<String> outLines = new ArrayList<>(lines);
        return outLines;
    }
}
