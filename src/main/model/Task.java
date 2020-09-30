package model;

// represents a task on the to-do list
public class Task {
    private String task;
    private boolean complete;
    private boolean urgent;

    // EFFECTS: creates a task with a complete and urgency label
    public Task(String task, boolean complete, boolean urgent) {
        this.task = task;
        this.complete = complete;
        this.urgent = urgent;
    }

    // EFFECTS: toggles complete status
    public void toggleComplete() {
        complete = !complete;
    }

    // EFFECTS: toggles urgent status
    public void toggleUrgent() {
        urgent = !urgent;
    }

    // EFFECTS: returns task as string
    public String getTask() {
        return task;
    }

    // EFFECTS: returns whether or not task is complete as boolean
    public boolean isComplete() {
        return complete;
    }

    // EFFECTS: returns whether or not task is urgent as boolean
    public boolean isUrgent() {
        return urgent;
    }

    @Override
    public String toString() {
        if (complete) {
            if (urgent) {
                return "[COMPLETE][URGENT] " + task;
            } else {
                return "[COMPLETE] " + task;
            }
        } else {
            if (urgent) {
                return "[URGENT] " + task;
            } else {
                return task;
            }
        }
    }
}
