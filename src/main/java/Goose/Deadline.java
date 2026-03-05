package Goose;

/**
 * Represents a task that must be completed by a specific date or time.
 */
public class Deadline extends Task {
    private String by;

    /**
     * Creates a new Deadline task with the given description and due date.
     *
     * @param description The description of the deadline task.
     * @param by          The due date or time by which the task must be completed.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the due date or time of this deadline task.
     *
     * @return The due date/time string.
     */
    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
