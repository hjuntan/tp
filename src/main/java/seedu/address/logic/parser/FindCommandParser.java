package seedu.address.logic.parser;

import static seedu.address.logic.commands.FindCommand.NOT_REQUIRED_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.EmailMatchesPredicate;
import seedu.address.model.person.GroupMatchesPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NusIdMatchesPredicate;
import seedu.address.model.person.PhoneMatchesPredicate;
import seedu.address.model.person.TagMatchesPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NUSID,
                        PREFIX_NAME,
                        PREFIX_PHONE,
                        PREFIX_EMAIL,
                        PREFIX_TAG,
                        PREFIX_GROUP);

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NUSID, PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);
        argumentMultimap.verifyAtLeastOnePrefixExists(PREFIX_NUSID, PREFIX_NAME,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG, PREFIX_GROUP);
        // Problems: Can't create Objects unless proper regex used.
        // Solution: Don't create objects
        String nusIdToMatch = argumentMultimap.getValue(PREFIX_NUSID).orElse(NOT_REQUIRED_VALUE);
        String nameToMatch = argumentMultimap.getValue(PREFIX_NAME).orElse(NOT_REQUIRED_VALUE);
        String phoneToMatch = argumentMultimap.getValue(PREFIX_PHONE).orElse(NOT_REQUIRED_VALUE);
        String emailToMatch = argumentMultimap.getValue(PREFIX_EMAIL).orElse(NOT_REQUIRED_VALUE);
        String tagToMatch = argumentMultimap.getValue(PREFIX_TAG).orElse(NOT_REQUIRED_VALUE);
        List<String> groupToMatch = argumentMultimap.getAllValues(PREFIX_GROUP);

        if (someParameterIsEmpty(groupToMatch, nusIdToMatch, nameToMatch, phoneToMatch, emailToMatch, tagToMatch)) {
            throw new ParseException(buildErrorMessage(nusIdToMatch, nameToMatch, phoneToMatch,
                    emailToMatch, tagToMatch, groupToMatch));
        }

        String[] nameKeywords = nameToMatch.split("\\s+");

        return new FindCommand(new NusIdMatchesPredicate(nusIdToMatch),
                new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)),
                new EmailMatchesPredicate(emailToMatch),
                new GroupMatchesPredicate(groupToMatch),
                new PhoneMatchesPredicate(phoneToMatch),
                new TagMatchesPredicate(tagToMatch));
    }

    private String buildErrorMessage(String id, String name, String phone,
                                     String email, String tag, List<String> groups) {
        // Stop user from entering a command if any field is empty
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("The provided fields should not be empty: ");
        if (id.isBlank()) {
            errorMessage.append("[id/NUSID] ");
        }

        if (name.isBlank()) {
            errorMessage.append("[n/NAME] ");
        }

        if (phone.isBlank()) {
            errorMessage.append("[p/PHONE_NUMBER] ");
        }

        if (email.isBlank()) {
            errorMessage.append("[e/EMAIL] ");
        }

        if (tag.isBlank()) {
            errorMessage.append("[t/TAG] ");
        }

        boolean someGroupIsEmpty = false;
        for (String group : groups) {
            if (group.isBlank()) {
                someGroupIsEmpty = true;
                break;
            }
        }

        if (someGroupIsEmpty) {
            errorMessage.append("[g/GROUP]...");
        }

        return errorMessage.toString();
    }

    private boolean someParameterIsEmpty(List<String> groups, String... params) {
        boolean hasEmptyParams = false;
        for (String group : groups) {
            if (group.isBlank()) {
                hasEmptyParams = true;
                break;
            }
        }

        for (String param : params) {
            if (param.isBlank()) {
                hasEmptyParams = true;
                break;
            }
        }

        return hasEmptyParams;
    }
}
