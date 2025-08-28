package basilseed;

import basilseed.task.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Storage {
    private static final String DEFAULT_FILE_PATH = "./data/tasks.txt";
    private Path path;

    private static void createFileIfNotExists(String filePath) {
        Path path = Paths.get(filePath);
        if (! Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                String outMsg = "____________________________________________________________\n" +
                        "Something went Wrong with IO. Check your permissions and file path!\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
            }
        }
    }

    public Storage() {
        this.path = Paths.get(DEFAULT_FILE_PATH);
        createFileIfNotExists(DEFAULT_FILE_PATH);
    }

    public Storage(String fileUrl) {
        this.path = Paths.get(fileUrl);
        createFileIfNotExists(fileUrl);
    }

    public void write(List<String> lines) {
        try {
            Files.write(this.path, lines);
        } catch (IOException e) {
            String outMsg = "____________________________________________________________\n" +
                    "Something went Wrong with IO. Check your permissions and file path!\n" +
                    "____________________________________________________________\n";
            System.out.println(outMsg);
        }
    }

    public ArrayList<String> read(){
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(this.path);
        } catch (IOException e) {
            String outMsg = "____________________________________________________________\n" +
                    "Something went Wrong with IO. Check your permissions and file path!\n" +
                    "____________________________________________________________\n";
            System.out.println(outMsg);
        }
        ArrayList<String> outLines = new ArrayList<>(lines);
        return outLines;
    }
}
