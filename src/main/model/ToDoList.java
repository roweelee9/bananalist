package model;

import java.util.ArrayList;
import java.util.Iterator;

// represents a to-do list with tasks.json and a point system
public class ToDoList implements Iterable<Task> {
    private static final int POINTS_PER_TASK = 5;
    private static final int POINTS_PER_URGENT_TASK = 7;

    private ArrayList<Task> tasks;
    private int bananaPoints;

    // EFFECTS: creates a to-do list with tasks and bananaPoints
    public ToDoList(ArrayList<Task> tasks, int bananaPoints) {
        this.tasks = tasks;
        this.bananaPoints = bananaPoints;
    }

    // REQUIRES: a task
    // MODIFIES: this
    // EFFECTS: adds task to end of to-do list
    public void addTask(Task task) {
        tasks.add(task);
    }

    // REQUIRES: index must be in range of list
    // MODIFIES: this
    // EFFECTS: removes task at index from to-do list
    public void deleteTask(int index) {
        tasks.remove(index);
    }


    // REQUIRES: index must be in range of list
    // EFFECTS: returns task at index
    public Task getTaskAtIndex(int index) {
        return tasks.get(index);
    }

    // EFFECTS: returns list of tasks
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    // EFFECTS: returns list containing completed tasks only
    public ArrayList<Task> getCompletedTasks() {
        ArrayList<Task> completedTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.isComplete()) {
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }

    // EFFECTS: returns list containing urgent tasks only
    public ArrayList<Task> getUrgentTasks() {
        ArrayList<Task> urgentTasks = new ArrayList<Task>();
        for (Task task : tasks) {
            if (task.isUrgent()) {
                urgentTasks.add(task);
            }
        }
        return urgentTasks;
    }

    // EFFECTS: returns number of complete tasks
    public int countCompleteTasks() {
        int counter = 0;
        for (Task task : tasks) {
            if (task.isComplete()) {
                counter++;
            }
        }
        return counter;
    }

    // EFFECTS: returns number of urgent tasks
    public int countUrgentTasks() {
        int counter = 0;
        for (Task task : tasks) {
            if (task.isUrgent()) {
                counter++;
            }
        }
        return counter;
    }

    // EFFECTS: returns number of bananaPoints
    public int getBananaPoints() {
        return bananaPoints;
    }

    // REQUIRES: index must be in range of list
    // MODIFIES: this
    // EFFECTS: toggles task's complete-status at index, and adds/removes bananaPoints as appropriate
    public void toggleTaskCompleteAtIndex(int index) {
        Task task = tasks.get(index);
        task.toggleComplete();
        if (task.isComplete()) {
            if (task.isUrgent()) {
                bananaPoints += POINTS_PER_URGENT_TASK;
            } else {
                bananaPoints += POINTS_PER_TASK;
            }
        } else {
            if (task.isUrgent()) {
                bananaPoints -= POINTS_PER_URGENT_TASK;
            } else {
                bananaPoints -= POINTS_PER_TASK;
            }
        }
    }

    // REQUIRES: index must be in range of list
    // MODIFIES: this
    // EFFECTS: toggles task's urgency-status at index
    public void toggleTaskUrgentAtIndex(int index) {
        tasks.get(index).toggleUrgent();
    }


    // EFFECTS: returns the tasks iterator
    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
