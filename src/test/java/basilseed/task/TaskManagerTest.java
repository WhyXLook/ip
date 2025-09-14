package basilseed.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import basilseed.Storage;
import basilseed.exception.BasilSeedIoException;

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
    public void processCommand_success() throws BasilSeedIoException {
        UiSuccess uiSuccess = new UiSuccess();
        Storage storage = new Storage();
        TaskManager taskManager = new TaskManager(uiSuccess, storage);
        taskManager.addToDoTask("borrow book", false);
        assertEquals(1, taskManager.getTaskCount());
        taskManager.addDeadlineTask("book", false, "2025-01-02", "yyyy-MM-dd");
        assertEquals(2, taskManager.getTaskCount());
        taskManager.addEventTask("meeting", false, "2025-01-02", "2025-01-03",
            "yyyy-MM-dd");
        assertEquals(3, taskManager.getTaskCount());
        taskManager.deleteTask(1);
        assertEquals(2, taskManager.getTaskCount());
    }

}
