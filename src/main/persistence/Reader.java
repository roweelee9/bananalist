package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Task;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

// A reader that can read to-do list data from a JSON file
public class Reader extends JsonInteractor {

    // EFFECTS: creates a reader with paths for tasks file and points file
    public Reader(String tasksPath, String pointsPath) {
        super(tasksPath, pointsPath);
    }

    // EFFECTS: returns an ArrayList of tasks parsed from JSON file
    // FileNotFoundException if file cannot be found/path is incorrect
    public ArrayList<Task> parseTasks() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(tasksPath));

        Gson gson = new Gson();
        ArrayList<Task> tasks = gson.fromJson(reader, new TypeToken<List<Task>>() {}.getType());

        return tasks;
    }

    // EFFECTS: returns number of BananaPoints parsed from JSON file
    // FileNotFoundException if file cannot be found/path is incorrect
    public int parseBananaPoints() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(pointsPath));

        Gson gson = new Gson();
        int points = gson.fromJson(reader, int.class);

        return points;
    }
}
