package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// tests for Task class
public class TestTask {
    private Task testTask;

    @BeforeEach
    public void runBefore() {
        testTask = new Task("test", false, false);
    }

    @Test
    // tests complete toggling
    public void testToggleComplete() {
        testTask.toggleComplete();
        // complete
        assertTrue(testTask.isComplete());

        testTask.toggleComplete();
        // not complete
        assertFalse(testTask.isComplete());
    }

    @Test
    // tests urgent toggling
    public void testToggleUrgent() {
        testTask.toggleUrgent();
        // urgent
        assertTrue(testTask.isUrgent());

        testTask.toggleUrgent();
        // not urgent
        assertFalse(testTask.isUrgent());
    }

    @Test
    // tests getting the task string
    public void testGetTask() {
        assertEquals("test", testTask.getTask());
    }

    // isComplete() and isUrgent() are already tested in other tests above

    @Test
    public void testToString() {
        assertEquals("test", testTask.toString());
        testTask.toggleComplete();
        assertEquals("[COMPLETE] test", testTask.toString());
        testTask.toggleUrgent();
        assertEquals("[COMPLETE][URGENT] test", testTask.toString());
        testTask.toggleComplete();
        assertEquals("[URGENT] test", testTask.toString());
    }
}
