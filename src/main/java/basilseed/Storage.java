package basilseed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import basilseed.exception.BasilSeedIoException;

/**
 * Provides persistent storage for tasks.
 * Reads and writes tasks from a file.
 */
public class Storage {
    public static final String DEFAULT_FILE_PATH = "./src/main/java/basilseed/data/tasks.txt";
    private Path path;


    /**
     * Creates a Storage instance with the default file path.
     */
    public Storage() throws BasilSeedIoException {
        this.path = Paths.get(DEFAULT_FILE_PATH);
        createFileIfNotExists(DEFAULT_FILE_PATH);
    }

    private static void createFileIfNotExists(String filePath) throws BasilSeedIoException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new BasilSeedIoException(DEFAULT_FILE_PATH);
            }
        }
    }

    /**
     * Writes a list of strings to storage.
     *
     * @param lines List of strings to write.
     */
    public void write(List<String> lines) throws BasilSeedIoException {
        try {
            Files.write(this.path, lines);
        } catch (IOException e) {
            throw new BasilSeedIoException(DEFAULT_FILE_PATH);
        }
    }

    /**
     * Reads all lines from storage.
     *
     * @return List of strings read from storage.
     */
    public ArrayList<String> read() throws BasilSeedIoException {
        List<String> lines;
        try {
            lines = Files.readAllLines(this.path);
        } catch (IOException e) {
            throw new BasilSeedIoException(DEFAULT_FILE_PATH);
        }
        ArrayList<String> outLines = new ArrayList<>(lines);
        return outLines;
    }
}
