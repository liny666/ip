package Goose;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    public String getDescription() {
        return super.toString().substring(3); // or just expose a description field getter in Task
    }

}
