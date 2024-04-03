package seedu.address.logic;

import javafx.collections.ObservableList;

/**
 * Class that represents a non-modifiable Command History
 */
public interface ReadOnlyCommandHistory {
    ObservableList<String> getCommandHistory();
}
