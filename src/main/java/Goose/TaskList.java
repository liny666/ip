package Goose;

import java.util.ArrayList;

/**
 * Represents the list of tasks managed by the application.
 * Provides operations to add, delete, and retrieve tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /** Creates an empty TaskList. */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList pre-populated with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given zero-based index.
     *
     * @param index Zero-based index of the task to remove.
     * @return The removed task.
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given zero-based index without removing it.
     *
     * @param index Zero-based index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The task count.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the underlying ArrayList of tasks.
     * Used when the raw list is needed, e.g. for saving to file.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
