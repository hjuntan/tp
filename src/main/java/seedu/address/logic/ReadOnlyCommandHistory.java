package seedu.address.logic;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of the user's Command History
 */
public interface ReadOnlyCommandHistory {

    /**
     * Returns an unmodifiable view of the Command History.
     * This list will not contain any duplicate persons.
     */
    ObservableList<String> getCommandHistory();
}
