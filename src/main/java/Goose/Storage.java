package Goose;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws GooseException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File taskFile = new File(filePath);
            File taskFolder = taskFile.getParentFile();
            if (!taskFolder.exists()) taskFolder.mkdirs();
            if (!taskFile.exists()) taskFile.createNewFile();

            Scanner fileScanner = new Scanner(taskFile);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                case "T":
                    Todo todo = new Todo(description);
                    if (isDone) todo.markAsDone();
                    tasks.add(todo);
                    break;
                case "D":
                    String by = parts.length > 3 ? parts[3] : "";
                    Deadline deadline = new Deadline(description, by);
                    if (isDone) deadline.markAsDone();
                    tasks.add(deadline);
                    break;
                case "E":
                    String fromTo = parts.length > 3 ? parts[3] : "";
                    String[] fromToSplit = fromTo.split(" to ");
                    String from = fromToSplit.length > 0 ? fromToSplit[0] : "";
                    String to = fromToSplit.length > 1 ? fromToSplit[1] : "";
                    Event event = new Event(description, from, to);
                    if (isDone) event.markAsDone();
                    tasks.add(event);
                    break;
                default:
                    System.out.println("Warning: Unknown task type in file: " + line);
                }
            }
            fileScanner.close();
        } catch (Exception e) {
            throw new GooseException("Could not load tasks from file.");
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) {
        try {
            File taskFile = new File(filePath);
            File taskFolder = taskFile.getParentFile();
            if (!taskFolder.exists()) taskFolder.mkdirs();

            PrintWriter writer = new PrintWriter(taskFile);
            for (Task t : tasks) {
                String line;
                String doneFlag = t.getStatusIcon().equals("X") ? "1" : "0";
                if (t instanceof Todo) {
                    line = "T | " + doneFlag + " | " + t.getDescription();
                } else if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    line = "D | " + doneFlag + " | " + d.getDescription() + " | " + d.getBy();
                } else if (t instanceof Event) {
                    Event e = (Event) t;
                    line = "E | " + doneFlag + " | " + e.getDescription() + " | " + e.getFrom() + " to " + e.getTo();
                } else {
                    continue;
                }
                writer.println(line);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Warning: Could not save tasks to file.");
        }
    }
}
