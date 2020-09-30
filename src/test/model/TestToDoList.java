package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// tests for ToDoList class
public class TestToDoList {
    private ToDoList testList;
    private ArrayList<Task> returned;

    private Task zero;
    private Task one;
    private Task two;
    private Task three;
    private Task four;

    @BeforeEach
    public void runBefore() {
        testList = new ToDoList(new ArrayList<Task>(), 0);
        returned = new ArrayList<Task>();

        zero = new Task("0", false, false);
        one = new Task("1", false, false);
        two = new Task("2", false, false);
        three = new Task("3", false, false);
        four = new Task("4", false, false);
    }

    @Test
    // tests adding a task
    public void testAddTask() {
        testList.addTask(zero);
        assertEquals(zero, testList.getTaskAtIndex(0));
        assertEquals(1, testList.getTasks().size());

        // extra
        testList.addTask(one);
        assertEquals(one, testList.getTaskAtIndex(1));
        assertEquals(2, testList.getTasks().size());
    }

    @Test
    // tests removing a task at index
    public void testDeleteTask() {
        testList.addTask(zero);
        testList.deleteTask(0);
        assertEquals(0, testList.getTasks().size());

        // many tasks.json
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        testList.deleteTask(1);
        assertEquals(two, testList.getTaskAtIndex(1));
        assertEquals(2, testList.getTasks().size());
    }

    @Test
    // tests getting completed tasks.json in an empty list
    public void testGetCompletedTasksEmpty() {
        assertEquals(returned.size(), testList.getCompletedTasks().size());
    }

    @Test
    // tests getting completed tasks.json in a list with zero
    public void testGetCompletedTasksZero() {
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        assertTrue(testList.getCompletedTasks().isEmpty());
    }

    @Test
    // tests getting completed tasks.json in a list with one
    public void testGetCompletedTasksOne() {
        one.toggleComplete();
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        returned.add(one);
        assertTrue(equalLists(returned, testList.getCompletedTasks()));
    }

    @Test
    // tests getting completed Tasks in a list with many
    public void testGetCompletedTasksMany() {
        one.toggleComplete();
        three.toggleComplete();
        four.toggleComplete();
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        testList.addTask(three);
        testList.addTask(four);
        returned.add(one);
        returned.add(three);
        returned.add(four);
        assertTrue(equalLists(returned, testList.getCompletedTasks()));
    }

    @Test
    // tests getting urgent tasks.json in an empty list
    public void testGetUrgentTasksEmpty() {
        assertEquals(returned.size(), testList.getUrgentTasks().size());
    }

    @Test
    // tests getting urgent tasks.json in a list with zero
    public void testGetUrgentTasksZero() {
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        assertTrue(testList.getUrgentTasks().isEmpty());
    }

    @Test
    // tests getting urgent tasks.json in a list with one
    public void testGetUrgentTasksOne() {
        one.toggleUrgent();
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        returned.add(one);
        assertTrue(equalLists(returned, testList.getUrgentTasks()));
    }

    @Test
    // tests getting urgent Tasks in a list with many
    public void testGetUrgentTasksMany() {
        one.toggleUrgent();
        three.toggleUrgent();
        four.toggleUrgent();
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        testList.addTask(three);
        testList.addTask(four);
        returned.add(one);
        returned.add(three);
        returned.add(four);
        assertTrue(equalLists(returned, testList.getUrgentTasks()));
    }

    @Test
    // tests counting completed tasks.json in an empty list
    public void testCountCompletedTasksEmpty() {
        assertEquals(0, testList.countCompleteTasks());
    }

    @Test
    // tests counting completed tasks.json in a list with zero
    public void testCountCompletedTasksZero() {
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        assertEquals(0, testList.countCompleteTasks());
    }

    @Test
    // tests counting completed tasks.json in a list with one
    public void testCountCompletedTasksOne() {
        one.toggleComplete();
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        assertEquals(1, testList.countCompleteTasks());
    }

    @Test
    // tests counting completed Tasks in a list with many
    public void testCountCompletedTasksMany() {
        one.toggleComplete();
        three.toggleComplete();
        four.toggleComplete();
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        testList.addTask(three);
        testList.addTask(four);
        assertEquals(3, testList.countCompleteTasks());
    }

    @Test
    // tests counting urgent tasks.json in an empty list
    public void testCountUrgentTasksEmpty() {
        assertEquals(0, testList.countUrgentTasks());
    }

    @Test
    // tests counting urgent tasks.json in a list with zero
    public void testCountUrgentTasksZero() {
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        assertEquals(0, testList.countUrgentTasks());
    }

    @Test
    // tests counting urgent tasks.json in a list with one
    public void testCountUrgentTasksOne() {
        one.toggleUrgent();
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        assertEquals(1, testList.countUrgentTasks());
    }

    @Test
    // tests counting urgent Tasks in a list with many
    public void testCountUrgentTasksMany() {
        one.toggleUrgent();
        three.toggleUrgent();
        four.toggleUrgent();
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);
        testList.addTask(three);
        testList.addTask(four);
        assertEquals(3, testList.countUrgentTasks());
    }

    @Test
    // tests toggling an urgent task to complete and to incomplete
    public void testToggleUrgentTaskComplete() {
        one.toggleUrgent();
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);

        // toggle complete
        testList.toggleTaskCompleteAtIndex(1);
        assertTrue(testList.getTaskAtIndex(1).isComplete());
        assertEquals(7, testList.getBananaPoints());

        // toggle incomplete
        testList.toggleTaskCompleteAtIndex(1);
        assertFalse(testList.getTaskAtIndex(1).isComplete());
        assertEquals(0, testList.getBananaPoints());
    }

    @Test
    // tests toggling a non-urgent task to complete and to incomplete
    public void testToggleNonUrgentTaskComplete() {
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);

        // toggle complete
        testList.toggleTaskCompleteAtIndex(1);
        assertTrue(testList.getTaskAtIndex(1).isComplete());
        assertEquals(5, testList.getBananaPoints());

        // toggle incomplete
        testList.toggleTaskCompleteAtIndex(1);
        assertFalse(testList.getTaskAtIndex(1).isComplete());
        assertEquals(0, testList.getBananaPoints());
    }

    @Test
    // tests toggling a non-urgent task to complete and to incomplete
    public void testToggleTaskUrgent() {
        testList.addTask(zero);
        testList.addTask(one);
        testList.addTask(two);

        // toggle urgent
        testList.toggleTaskUrgentAtIndex(1);
        assertTrue(testList.getTaskAtIndex(1).isUrgent());

        // toggle not urgent
        testList.toggleTaskUrgentAtIndex(1);
        assertFalse(testList.getTaskAtIndex(1).isUrgent());
    }

    // helper methods
    public boolean equalLists(ArrayList<Task> one, ArrayList<Task> two) {
        for (int i = 0; i < one.size(); i++) {
            if (!one.get(i).equals(two.get(i))) {
                return false;
            }
        }
        for (int i = 0; i < two.size(); i++) {
            if (!one.get(i).equals(two.get(i))) {
                return false;
            }
        }
        return true;
    }
}
