package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.Storage;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private static final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static CommandHistory commandHistory = new CommandHistory();
    private final CommandExecutor commandExecutor;
    private String currentCommand = "";

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
            commandHistory.add(commandText);
            commandHistory.moveCursorToMostRecent();
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles navigational key presses while in the command box
     */
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.UP) {
            commandTextField.setText(commandHistory.getPreviousCommand().orElseGet(() -> {
                commandHistory.moveCursorToMostRecent();
                return currentCommand;
            }));
            commandTextField.positionCaret(commandTextField.getLength());
        } else if (event.getCode() == KeyCode.DOWN) {
            commandTextField.setText(commandHistory.getNextCommand().orElseGet(() -> {
                commandHistory.moveCursorToMostRecent();
                return currentCommand;
            }));
            commandTextField.positionCaret(commandTextField.getLength());
        } else {
            logger.info("Caret at " + commandTextField.getCaretPosition());
            logger.info("typed letter is " + event.getText());
            currentCommand = handleTextEnter(commandTextField.getCaretPosition(), event.getText());
            commandHistory.moveCursorToMostRecent();
        }
    }

    private String handleTextEnter(int pos, String toInsert) {
        logger.info("textField is " + commandTextField.getText());
        String firstHalf = commandTextField.getText(0, pos);
        String secondHalf = commandTextField.getText(pos, commandTextField.getText().length());
        return firstHalf + toInsert + secondHalf;
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    public void setCommandHistory(Storage storage) throws DataLoadingException {
        setCommandHistory(storage.readCommandHistory().orElse(new CommandHistory()));
    }

    /**
     * Sets the storage which the commandBox references the user's past commands from.
     */
    public static void setCommandHistory(CommandHistory commandHistory) {
        CommandBox.commandHistory = commandHistory;
    }
}
