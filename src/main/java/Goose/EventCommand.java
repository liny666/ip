package Goose;

/**
 * Represents the command to add a new Event task.
 */
public class EventCommand extends Command {
    private String description;
    private String from;
    private String to;

    /**
     * Creates an EventCommand with the given description and time range.
     *
     * @param description The description of the event.
     * @param from        The start time of the event.
     * @param to          The end time of the event.
     */
    public EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /** Creates the Event task, adds it to the list, saves, and prints a confirmation. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task task = new Event(description, from, to);
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}
