package Goose;

/**
 * Represents the command to add a new Todo task.
 */
public class TodoCommand extends Command {
    private String description;

    /**
     * Creates a TodoCommand with the given task description.
     *
     * @param description The description of the todo task to add.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /** Creates the Todo task, adds it to the list, saves, and prints a confirmation. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = new Todo(description);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}
