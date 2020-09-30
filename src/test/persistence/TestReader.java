package persistence;

import model.Task;
import persistence.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestReader {
    private Task task1;
    private Task task2;
    private Task task3;
    private ArrayList<Task> tasks;
    private Reader testReader;

    @BeforeEach
    void runBefore() {
        task1 = new Task("task1", false, false);
        task2 = new Task("task2", false, true);
        task3 = new Task("task3", true, true);
        tasks = new ArrayList<Task>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        testReader = new Reader("./data/testtasks1.json", "./data/testpoints1.json");
    }

    @Test
    void testParseTasks() {
        try {
            ArrayList<Task> parsedTasks = testReader.parseTasks();
            assertEquals(tasks.size(), parsedTasks.size());

            assertEquals(task1.getTask(), parsedTasks.get(0).getTask());
            assertEquals(task2.getTask(), parsedTasks.get(1).getTask());
            assertEquals(task3.getTask(), parsedTasks.get(2).getTask());

            assertFalse(parsedTasks.get(0).isComplete());
            assertFalse(parsedTasks.get(1).isComplete());
            assertTrue(parsedTasks.get(2).isComplete());

            assertFalse(parsedTasks.get(0).isUrgent());
            assertTrue(parsedTasks.get(1).isUrgent());
            assertTrue(parsedTasks.get(2).isUrgent());
        } catch (FileNotFoundException fileNotFoundException) {
            fail("incorrect file path");
        } catch (Exception e) {
            fail("exception should not be thrown");
        }

    }

    @Test
    void testParseBananaPoints() {
        try {
            int parsedPoints = testReader.parseBananaPoints();
            assertEquals(10, parsedPoints);
        } catch (FileNotFoundException fileNotFoundException) {
            fail("incorrect file path");
        } catch (Exception e) {
            fail("exception should not be thrown");
        }
    }

    @Test
    void testExceptionThrowing() {
        Reader test = new Reader("./nonexistent1", "./nonexistent2");
        try {
            ArrayList<Task> parsedTasks = test.parseTasks();
            fail("exception should be thrown");
        } catch (FileNotFoundException fileNotFoundException) {
            // pass!
        }
    }
}
