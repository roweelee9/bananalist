package ui;

import model.Task;
import model.ToDoList;
import persistence.Reader;
import persistence.Writer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// BananaList Application
public class BananaListApp extends JFrame implements ActionListener {
    private static final String TASKS_FILE = "./data/tasks.json";
    private static final String POINTS_FILE = "./data/points.json";
    private ToDoList toDoList;
    private ToDoListModel toDoListModel;

    private Reader reader;
    private Writer writer;

    private JPanel mainPanel;
    private JPanel menuPanel;
    private JScrollPane toDoListScrollPane;
    private JList<Task> toDoJList;
    private JLabel pointsCounter;
    private JPanel addTaskPanel;
    private JTextField addTaskField;
    private JButton addTaskButton;
    private JButton deleteTaskButton;
    private JButton toggleCompleteButton;
    private JButton toggleUrgentButton;

    // MODIFIES: this
    // EFFECTS: runs BananaList app
    public BananaListApp() {
        super("BananaList");
        reader = new Reader(TASKS_FILE, POINTS_FILE);
        writer = new Writer(TASKS_FILE, POINTS_FILE);
        loadToDoList();
        toDoListModel = new ToDoListModel(toDoList);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveToDoList();
            }
        });

        setPreferredSize(new Dimension(1024, 600));
        this.setMinimumSize(new Dimension(1024, 600));

        setContentPane(getMainPanel());

        setLayout(new FlowLayout());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    // MODIFIES: this
    // EFFECTS: loads to-do list and points from TASKS_FILE and POINTS_FILE, if it exists or is not empty,
    // otherwise creates an empty to-do list with zero BananaPoints
    private void loadToDoList() {
        try {
            ArrayList<Task> tasks = reader.parseTasks();
            int bananaPoints = reader.parseBananaPoints();
            toDoList = new ToDoList(tasks, bananaPoints);
        } catch (Exception e) {
            toDoList = new ToDoList(new ArrayList<Task>(), 0);
        }
    }

    // EFFECTS: saves ToDoList data to file
    private void saveToDoList() {
        int result = JOptionPane.showConfirmDialog(null,
                "Save this BananaList?",
                "Save",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            try {
                writer.save(toDoList.getTasks(), toDoList.getBananaPoints());
                System.exit(0);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(mainPanel, "Save file not found");
            }
        } else {
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of main panel and returns it
    private Container getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel();
            mainPanel.setLayout(new BorderLayout());

            mainPanel.add(getPointsCounter());
            mainPanel.add(getMenuPanel());
            mainPanel.add(getToDoListScrollPane(), BorderLayout.CENTER);

        }
        return mainPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of menu panel and returns it
    private JPanel getMenuPanel() {
        if (menuPanel == null) {
            menuPanel = new JPanel();
            menuPanel.add(getAddTaskPanel());
            menuPanel.add(getDeleteTaskButton());
            menuPanel.add(getToggleCompleteButton());
            menuPanel.add(getToggleUrgentButton());
        }
        return menuPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of scroll pane and returns it
    private JScrollPane getToDoListScrollPane() {
        if (toDoListScrollPane == null) {
            toDoListScrollPane = new JScrollPane(getToDoJList());
            toDoListScrollPane.setPreferredSize(new Dimension(700, 500));
        }
        return toDoListScrollPane;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of JList for ToDoList and returns it
    private JList<Task> getToDoJList() {
        if (toDoJList == null) {
            toDoJList = new JList<>();
            toDoJList.setModel(toDoListModel);
        }
        return toDoJList;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of JLabel for points counter and returns it
    private JLabel getPointsCounter() {
        if (pointsCounter == null) {
            pointsCounter = new JLabel("Banana Points: " + Integer.toString(toDoList.getBananaPoints()));
        }
        return pointsCounter;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of task adding panel and returns it
    private Component getAddTaskPanel() {
        if (addTaskPanel == null) {
            addTaskPanel = new JPanel();

            addTaskPanel.add(getAddTaskField());
            addTaskPanel.add(getAddTaskButton());
        }
        return addTaskPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of JTextField for adding tasks and returns it
    private JTextField getAddTaskField() {
        if (addTaskField == null) {
            addTaskField = new JTextField();
            addTaskField.setPreferredSize(new Dimension(100, 20));
        }
        return addTaskField;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of button for adding tasks and returns it
    private JButton getAddTaskButton() {
        if (addTaskButton == null) {
            addTaskButton = new JButton("Add");
            addTaskButton.setActionCommand("addTask");
            addTaskButton.addActionListener(this);
        }
        return addTaskButton;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of button for deleting tasks and returns it
    private JButton getDeleteTaskButton() {
        if (deleteTaskButton == null) {
            deleteTaskButton = new JButton("Delete");
            deleteTaskButton.setActionCommand("deleteTask");
            deleteTaskButton.addActionListener(this);
        }
        return deleteTaskButton;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of button for toggling complete and returns it
    private JButton getToggleCompleteButton() {
        if (toggleCompleteButton == null) {
            toggleCompleteButton = new JButton("Toggle Complete");
            toggleCompleteButton.setActionCommand("toggleComplete");
            toggleCompleteButton.addActionListener(this);
        }
        return toggleCompleteButton;
    }

    // MODIFIES: this
    // EFFECTS: creates single instance of button for toggling urgennt and returns it
    private JButton getToggleUrgentButton() {
        if (toggleUrgentButton == null) {
            toggleUrgentButton = new JButton("Toggle Urgent");
            toggleUrgentButton.setActionCommand("toggleUrgent");
            toggleUrgentButton.addActionListener(this);
        }
        return toggleUrgentButton;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: handles actions performed on mouse clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addTask")) {
            if (getAddTaskField().getText().length() > 0) {
                playSound("./resources/add.wav");
                toDoListModel.add(new Task(getAddTaskField().getText().trim(), false, false));
                getAddTaskField().setText("");
                getToDoJList().setSelectedIndex(getToDoJList().getModel().getSize() - 1);
            }
        } else if (e.getActionCommand().equals("deleteTask")) {
            playSound("./resources/delete.wav");
            toDoListModel.delete(getToDoJList().getSelectedIndex());
        } else if (e.getActionCommand().equals("toggleComplete")) {
            toDoListModel.toggleComplete(getToDoJList().getSelectedIndex());
            updatePointsCounter();
        } else if (e.getActionCommand().equals("toggleUrgent")) {
            toDoListModel.toggleUrgent(getToDoJList().getSelectedIndex());
            updatePointsCounter();
        }
    }

    // EFFECTS: plays sound effect
    private void playSound(String path) {
        File file = new File(path);
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates point counter to display correct number of points
    private void updatePointsCounter() {
        pointsCounter.setText("Banana Points: " + Integer.toString(toDoList.getBananaPoints()));
    }
}
