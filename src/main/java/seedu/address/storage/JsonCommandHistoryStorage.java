package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ReadOnlyCommandHistory;

/**
 * A class to access the user's Command History via JSON file format.
 */
public class JsonCommandHistoryStorage implements CommandHistoryStorage {

    private Path commandHistoryFilePath;

    public JsonCommandHistoryStorage(Path commandHistoryFilePath) {
        this.commandHistoryFilePath = commandHistoryFilePath;
    }

    @Override
    public Path getCommandHistoryFilePath() {
        return commandHistoryFilePath;
    }

    /**
     * Returns the user's {@code CommandHistory} where it is stored by default
     * @throws DataLoadingException if the file format is not as expected.
     */
    @Override
    public Optional<CommandHistory> readCommandHistory() throws DataLoadingException {
        return readCommandHistory(commandHistoryFilePath);
    }

    /**
     * Similar to {@link #readCommandHistory()} but allows reading from a specific file
     * @param commandHistoryFilePath the specified file path to read from, cannot be null.
     * @throws DataLoadingException if the file format is not as expected.
     */
    public Optional<CommandHistory> readCommandHistory(Path commandHistoryFilePath) throws DataLoadingException {
        return JsonUtil.readJsonFile(commandHistoryFilePath, CommandHistory.class);
    }

    @Override
    public void saveCommandHistory(ReadOnlyCommandHistory history) throws IOException {
        JsonUtil.saveJsonFile(history, commandHistoryFilePath);
    }
}
