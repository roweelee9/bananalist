package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// A writer that can write to-do list data to a JSON file
public class Writer extends JsonInteractor {

    // EFFECTS: creates a writer with paths for tasks file and points file
    public Writer(String tasksPath, String pointsPath) {
        super(tasksPath, pointsPath);
    }

    // EFFECTS: writes to-do list data to two JSON files for tasks and points
    // IOException if stream corrupted
    public void save(ArrayList<Task> tasks, int points) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        BufferedWriter taskWriter = new BufferedWriter(new FileWriter(tasksPath));
        BufferedWriter pointsWriter = new BufferedWriter(new FileWriter(pointsPath));

        gson.toJson(tasks, taskWriter);
        gson.toJson(points, pointsWriter);

        taskWriter.close();
        pointsWriter.close();
    }
}
