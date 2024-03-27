package seedu.address.storage;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ReadOnlyCommandHistory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a Storage for the commands the user has entered
 */
public interface CommandHistoryStorage {
    /**
     * Returns the file path of user's command history
     */
    Path getCommandHistoryFilePath();

    Optional<CommandHistory> readCommandHistory() throws DataLoadingException;

    void saveCommandHistory(ReadOnlyCommandHistory history) throws IOException;
}
