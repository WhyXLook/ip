package basilseed.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import basilseed.Storage;
import basilseed.ui.UiError;

import basilseed.ui.UiSuccess;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class TaskManagerTest {

    @BeforeEach
    public void setup() {
        Path path = Paths.get(Storage.DEFAULT_FILE_PATH);
        try {
            Files.write(path, List.of());
        } catch (IOException e){
            System.out.println("Error writing to file: " + e);
        }
    }

    @AfterEach
    public void tearDown() {
        Path path = Paths.get(Storage.DEFAULT_FILE_PATH);
        try {
            Files.write(path, List.of());
        } catch (IOException e){
            System.out.println("Error writing to file: " + e);
        }
    }

    @Test
    public void processCommand_success() {
        UiSuccess uiSuccess = new UiSuccess();
        TaskManager taskManager = new TaskManager(uiSuccess);
        taskManager.processCommand("todo", "borrow book", List.of(""), false, "");
        assertEquals(1, taskManager.getTaskCount());
        taskManager.processCommand("deadline", "book", List.of("2025-01-02"), false,
            "yyyy-MM-dd");
        assertEquals(2, taskManager.getTaskCount());
        taskManager.processCommand("event", "meeting", List.of("2025-01-02","2025-01-03"), false,
            "yyyy-MM-dd");
        assertEquals(3, taskManager.getTaskCount());
        taskManager.processCommand("delete", "", List.of("1"), false,
            "");
        assertEquals(2, taskManager.getTaskCount());
    }

}
