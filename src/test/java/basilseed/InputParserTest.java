package basilseed;




import basilseed.command.Command;
import basilseed.exception.BasilSeedInvalidInputException;

import basilseed.exception.BasilSeedIoException;
import basilseed.task.Event;
import basilseed.task.TaskManager;
import basilseed.ui.UiSuccess;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputParserTest {

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
    public void dateType_failure() throws BasilSeedInvalidInputException {
        // single digit
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("deadline return book /by 2025-12-5", 0));
        // mon day swap
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("deadline return book /by 2025-13-01", 0));
        // space instead of dash
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("deadline return book /by 2025 12 5", 0));
    }

    @Test
    public void parse_success() throws BasilSeedInvalidInputException, BasilSeedIoException {
        InputParser inputParser = new InputParser();
        String taskString = "event test /from 2025-01-01 /to 2025-01-02";
        Command command = inputParser.parse(taskString, 0);
        UiSuccess  uiSuccess = new UiSuccess();
        Storage storage = new Storage();
        TaskManager taskManager = new TaskManager(uiSuccess, storage);
        String result = command.execute(taskManager);
        Event eventTask = new Event("test", "2025-01-01", "2025-01-02", "yyyy-MM-dd");
        String eventString = eventTask.toString();
        assertEquals(result, eventString);
    }

    @Test
    public void parse_failure() {
        // wrong argNum / no arg
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("todo",0));
        // keyword not found
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("event /from",0));
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("event /to",0));
        // taskname not found
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("event /from /to",0));
        // arg keyword wrong order
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("event hihi /to /from",0));
        // no arg supplied
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("event heyho /from /to",0));
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("event heyho /from 2025-01-02 /to",0));
        assertThrows(BasilSeedInvalidInputException.class, () -> new InputParser()
            .parse("event heyho /from /to 2025-01-02",0));
    }

}
