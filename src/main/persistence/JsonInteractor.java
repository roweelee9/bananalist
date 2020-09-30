package persistence;

// represents an abstract class that can interact with JSON files at a given path
public abstract class JsonInteractor {
    protected String tasksPath;
    protected String pointsPath;

    // MODIFIES: creates a JsonInteractor class with two paths for files
    public JsonInteractor(String tasksPath, String pointsPath) {
        this.tasksPath = tasksPath;
        this.pointsPath = pointsPath;
    }
}
