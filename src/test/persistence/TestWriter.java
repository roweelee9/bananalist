package persistence;

import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestWriter {
    private Task writtenTask1;
    private Task writtenTask2;
    private Task writtenTask3;
    private ArrayList<Task> tasks;
    private int points;
    private Writer testWriter;
    private Reader testReader;

    @BeforeEach
    void runBefore() {
        writtenTask1 = new Task("writtenTask1", false, false);
        writtenTask2 = new Task("writtenTask2", false, true);
        writtenTask3 = new Task("writtenTask3", true, true);
        tasks = new ArrayList<Task>();
        tasks.add(writtenTask1);
        tasks.add(writtenTask2);
        tasks.add(writtenTask3);
        points = 20;
        testWriter = new Writer("./data/testtasks2.json", "./data/testpoints2.json");
        testReader = new Reader("./data/testtasks2.json", "./data/testpoints2.json");
    }

    @Test
    void testSave() {
        try {
            testWriter.save(tasks, points);
            // check that written data is what is expected
            ArrayList<Task> parsedTasks = testReader.parseTasks();

            assertEquals(tasks.size(), parsedTasks.size());

            assertEquals(writtenTask1.getTask(), parsedTasks.get(0).getTask());
            assertEquals(writtenTask2.getTask(), parsedTasks.get(1).getTask());
            assertEquals(writtenTask3.getTask(), parsedTasks.get(2).getTask());

            assertFalse(parsedTasks.get(0).isComplete());
            assertFalse(parsedTasks.get(1).isComplete());
            assertTrue(parsedTasks.get(2).isComplete());

            assertFalse(parsedTasks.get(0).isUrgent());
            assertTrue(parsedTasks.get(1).isUrgent());
            assertTrue(parsedTasks.get(2).isUrgent());

            int parsedPoints = testReader.parseBananaPoints();
            assertEquals(20, parsedPoints);

        } catch (IOException e) {
            fail("IOException should not be thrown");
        }
    }
}
