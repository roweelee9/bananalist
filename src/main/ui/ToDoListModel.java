package ui;

import model.Task;
import model.ToDoList;

import javax.swing.*;
import java.util.ArrayList;

// model Class for ToDoList for JFrame compatibility
public class ToDoListModel extends AbstractListModel<Task> {
    private ToDoList toDoList;

    // EFFECTS: creates a ToDoListModel from a ToDoList
    public ToDoListModel(ToDoList toDoList) {
        this.toDoList = toDoList;
    }

    // REQUIRES: a task
    // MODIFIES: this
    // EFFECTS: adds task to end of to-do list
    public void add(Task task) {
        toDoList.addTask(task);
        fireContentsChanged(this, toDoList.getTasks().size() - 1, toDoList.getTasks().size() - 1);
    }

    // REQUIRES: index must be in range of list
    // MODIFIES: this
    // EFFECTS: removes task at index from to-do list
    public void delete(int index) {
        toDoList.deleteTask(index);
        fireContentsChanged(this, index, index);
    }

    // REQUIRES: index must be in range of list
    // MODIFIES: this
    // EFFECTS: toggles task at index's complete value
    public void toggleComplete(int index) {
        toDoList.toggleTaskCompleteAtIndex(index);
        fireContentsChanged(this, index, index);
    }

    // REQUIRES: index must be in range of list
    // MODIFIES: this
    // EFFECTS: toggles task at index's urgent value
    public void toggleUrgent(int index) {
        toDoList.toggleTaskUrgentAtIndex(index);
        fireContentsChanged(this, index, index);
    }

    // EFFECTS: returns list containing completed tasks only
    public ArrayList<Task> getCompletedTasks() {
        return toDoList.getCompletedTasks();
    }

    @Override
    // EFFECTS: returns size of list
    public int getSize() {
        return toDoList.getTasks().size();
    }

    @Override
    // EFFECTS: returns task at index
    public Task getElementAt(int index) {
        return toDoList.getTaskAtIndex(index);
    }


}
