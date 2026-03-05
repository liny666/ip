package Goose;

/**
 * The main class for the GoGoGoose task manager application.
 * Initialises the UI, storage, and task list, then runs the main command loop.
 */
public class GoGoGoose {
    private static final String FILE_PATH = "./data/goose.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a GoGoGoose instance, loading any previously saved tasks from the given file path.
     * If the file cannot be loaded, starts with an empty task list.
     *
     * @param filePath The path to the file used for persistent task storage.
     */
    public GoGoGoose(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (GooseException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Starts the main command loop, reading and executing user commands until exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (GooseException e) {
                ui.showError(e.getMessage());
            } catch (Exception e) {
                ui.showError("Quack!! An unexpected error occurred. Check your input.");
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }

    /**
     * Entry point of the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new GoGoGoose(FILE_PATH).run();
    }
}
