package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUSID;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.person.NusId;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the NusId used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_NUSID + "NUSID\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NUSID + "E1234567\n"
            + "OR \n"
            + COMMAND_WORD
            + ": Deletes all the people identified by the group used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_GROUP + "GROUP\n"
            + "Example: " + COMMAND_WORD + " g/CS2103-T15";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";
    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted Group: %1$s";

    private final NusId nusId;

    private final Group group;

    /**
     * @param nusId of the person in the filtered person list to delete
     */
    public DeleteCommand(NusId nusId) {
        this.nusId = nusId;
        this.group = null;
    }
    /**
     * @param group in which all people in the filtered person list are to be deleted
     */
    public DeleteCommand(Group group) {
        this.group = group;
        this.nusId = null;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (this.nusId != null) {
            Person personToDelete = model.filterPersonListWithNusId(nusId);
            if (personToDelete == null) {
                throw new CommandException(Messages.MESSAGE_NON_EXISTENT_PERSON);
            }

            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        } else {
            List<Person> peopleToDelete = new ArrayList<Person>();
            Group deletedGroup = group;



            for (int i = 0; i < lastShownList.size(); i++) {
                System.out.println(lastShownList.get(i).getGroups().contains(deletedGroup));
                if (lastShownList.get(i).getGroups().contains(deletedGroup)) {
                    peopleToDelete.add(lastShownList.get(i));
                }
            }
            System.out.println(lastShownList.size());
            if (peopleToDelete.size() == 0) {
                throw new CommandException(Messages.MESSAGE_NON_EXISTENT_GROUP);
            }

            for (int i = 0; i < peopleToDelete.size(); i++) {
                model.deletePerson(peopleToDelete.get(i));
            }

            return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, this.group.groupName));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        if (nusId != null) {
            return nusId.equals(otherDeleteCommand.nusId);
        } else if (group != null) {
            return group.equals(otherDeleteCommand.group);
        }
        return false;
    }

    @Override
    public String toString() {
        if (nusId != null) {
            return new ToStringBuilder(this)
                    .add("targetNusId", nusId.toString())
                    .toString();
        } else if (group != null) {
            return new ToStringBuilder(this)
                    .add("targetGroup", group.toString())
                    .toString();
        }
        return null;
    }
}
