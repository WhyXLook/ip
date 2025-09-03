package basilseed;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import basilseed.ui.UiError;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParserTest {

    @Test
    public void getDateType_success() {
        UiError uiError = new UiError();
        assertEquals("yyyy-MM-dd", new InputParser(uiError).getDateType(List.of("2025-12-11")));
        assertEquals("yyyy-MM-dd", new InputParser(uiError).getDateType(List.of("2025-02-01")));
    }

    @Test
    public void getDateType_failure() {
        UiError uiError = new UiError();
        // single digit
        assertEquals("", new InputParser(uiError).getDateType(List.of("2024-12-1")));
        // mon day swap
        assertEquals("", new InputParser(uiError).getDateType(List.of("2024-13-01")));
        // space instead of dash
        assertEquals("", new InputParser(uiError).getDateType(List.of("2024 12 1")));

    }

    @Test
    public void parse_success() {
        UiError uiError = new UiError();
        String taskString = "todo book";
        assertEquals(taskString, new InputParser(uiError).parse(taskString, 0));
        taskString = "event Random Task Name /from 2025-01-01 /to 2025-01-02";
        assertEquals(taskString, new InputParser(uiError).parse(taskString, 1));
    }

    @Test
    public void parse_failure() {
        UiError uiError = new UiError();
        String taskString = "todo";
        // wrong argNum / no arg
        assertEquals("", new InputParser(uiError).parse(taskString, 0));
        // keyword not found
        taskString = "event /from";
        assertEquals("", new InputParser(uiError).parse(taskString, 0));
        taskString = "event /to";
        assertEquals("", new InputParser(uiError).parse(taskString, 0));
        // taskname not found
        taskString = "event /from /to";
        assertEquals("", new InputParser(uiError).parse(taskString, 0));
        // arg keyword wrong order
        taskString = "event hihi /to /from";
        assertEquals("", new InputParser(uiError).parse(taskString, 0));
        // no arg supplied
        taskString = "event heyho /from /to";
        assertEquals("", new InputParser(uiError).parse(taskString, 0));
        taskString = "event heyho /from 2025-01-02 /to";
        assertEquals("", new InputParser(uiError).parse(taskString, 0));
        taskString = "event heyho /from /to 2025-01-02";
        assertEquals("", new InputParser(uiError).parse(taskString, 0));
    }

}
