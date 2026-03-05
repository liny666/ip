package Goose;

/**
 * Represents a task with a description and completion status.
 * This is the base class for all task types (Todo, Deadline, Event).
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Creates a new Task with the given description, initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void unmarkAsDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon representing the completion state of this task.
     *
     * @return "X" if the task is done, " " otherwise.
     */
    protected String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the description of this task.
     *
     * @return The task description string.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
