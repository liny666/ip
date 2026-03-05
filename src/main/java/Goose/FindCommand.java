package Goose;

import java.util.ArrayList;

/**
 * Represents the command to search for tasks whose descriptions contain a given keyword.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Creates a FindCommand with the given search keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /** Searches the task list for matching tasks and prints the results. */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        ui.showMatchingTasks(matchingTasks);
    }
}
