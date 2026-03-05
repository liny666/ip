package Goose;

/**
 * Represents the command to add a new Deadline task.
 */
public class DeadlineCommand extends Command {
    private String description;
    private String by;

    /**
     * Creates a DeadlineCommand with the given description and due date.
     *
     * @param description The description of the deadline task.
     * @param by          The due date or time for the task.
     */
    public DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    /** Creates the Deadline task, adds it to the list, saves, and prints a confirmation. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = new Deadline(description, by);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}
