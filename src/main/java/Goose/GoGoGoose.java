package Goose;

public class GoGoGoose {
    private static final String FILE_PATH = "./data/goose.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new GoGoGoose(FILE_PATH).run();
    }
}
