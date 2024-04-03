package seedu.address.logic;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents the list of commands the user has typed,
 * Persists between opening and closing of AronaPro.
 */
public class CommandHistory implements ReadOnlyCommandHistory {
    private int cursorPosition = 0;
    private final ObservableList<String> internalList = FXCollections.observableArrayList();
    private final ObservableList<String> unmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Creates a new Command History the user can interact with using their up and down keys.
     */
    public CommandHistory() {
    }

    /**
     * Creates a Command History with the given history data.
     */
    public CommandHistory(ReadOnlyCommandHistory history) {
        this();
        resetData(history);
    }

    @Override
    public ObservableList<String> getCommandHistory() {
        return unmodifiableList;
    }

    public void resetData(ReadOnlyCommandHistory replacement) {
        internalList.setAll(replacement.getCommandHistory());
    }

    /**
     * Adds a command to the Command History of the user.
     * Removes the oldest command if the length exceeds 50
     * @param s the Command string to store
     */
    public void add(String s) {
        internalList.add(s);

        // Ensures the list doesn't grow infinitely
        if (internalList.size() > 50) {
            internalList.remove(0);
        }
    }

    /**
     * Returns the previous Command relative to the cursor's current position.
     * If the previous command doesn't exist, this returns nothing
     * @see Optional
     */
    public Optional<String> getPreviousCommand() {
        if (historyIsEmpty()) {
            return Optional.empty();
        } else if (cursorPosition == 0) {
            return Optional.of(internalList.get(0));
        } else if (cursorPosition > internalList.size()) {
            return Optional.empty();
        } else {
            cursorPosition--;
            assert cursorPosition >= 0;
            return Optional.of(internalList.get(cursorPosition));
        }
    }

    /**
     * Returns the next Command relative to the cursor's current position
     * If the next command doesn't exist, this returns nothing
     */
    public Optional<String> getNextCommand() {
        if (historyIsEmpty()) {
            return Optional.empty();
        }

        if (cursorPosition < internalList.size() - 1) {
            cursorPosition++;
            return Optional.of(internalList.get(cursorPosition));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Moves the cursor to the position of the last Command
     */
    public void moveCursorToMostRecent() {
        if (historyIsEmpty()) {
            return;
        }
        cursorPosition = internalList.size();
    }

    /**
     * Checks if the user's command history is empty
     */
    public boolean historyIsEmpty() {
        return internalList.isEmpty();
    }

    protected void setCursorPosition(int index) {
        cursorPosition = index;
    }
}
