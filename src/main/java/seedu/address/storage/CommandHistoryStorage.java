package seedu.address.storage;

import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.CommandHistory;


/**
 * Represents a Storage for the commands the user has entered
 */
public interface CommandHistoryStorage {
    /**
     * Returns the file path of user's command history
     */
    Path getCommandHistoryFilePath();

    Optional<CommandHistory> readCommandHistory() throws DataLoadingException;
}
