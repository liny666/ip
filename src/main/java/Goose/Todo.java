package Goose;

/**
 * Represents a todo task with no date or time constraint.
 */
public class Todo extends Task {

    /**
     * Creates a new Todo task with the given description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
