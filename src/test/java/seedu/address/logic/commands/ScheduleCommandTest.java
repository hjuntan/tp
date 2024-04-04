package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUSID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_ADD_SUCCESS;
import static seedu.address.logic.commands.ScheduleCommand.MESSAGE_DELETE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NusId;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Schedule;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ScheduleCommand.
 */
public class ScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedFilteredListSchedule_success() {
        Person personToSchedule = TypicalPersons.ALICE;
        PersonBuilder personInList = new PersonBuilder(personToSchedule);
        Person personWithSchedule = personInList.withSchedule(VALID_SCHEDULE_AMY).withRemark(VALID_REMARK_AMY).build();

        ScheduleCommand scheduleCommand = new ScheduleCommand(personWithSchedule.getNusId(),
                personWithSchedule.getSchedule(), personWithSchedule.getRemark());

        String expectedMessage = String.format(MESSAGE_ADD_SUCCESS,
                Messages.format(personWithSchedule));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToSchedule, personWithSchedule);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedFilteredListRemove_success() {
        Person personToSchedule = TypicalPersons.ALICE;
        PersonBuilder personInList = new PersonBuilder(personToSchedule);
        Person personWithOutSchedule = personInList.withSchedule("").withRemark("").build();

        ScheduleCommand scheduleCommand = new ScheduleCommand(personWithOutSchedule.getNusId(),
                personWithOutSchedule.getSchedule(), personWithOutSchedule.getRemark());

        String expectedMessage = String.format(MESSAGE_DELETE_SUCCESS,
                Messages.format(personWithOutSchedule));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToSchedule, personWithOutSchedule);

        assertCommandSuccess(scheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonUnfilteredList_failure() {
        NusId nusId = new NusId("E0000000"); // NusId does not exist
        ScheduleCommand scheduleCommand = new ScheduleCommand(nusId, new Schedule(VALID_SCHEDULE_AMY),
                new Remark(VALID_REMARK_AMY));

        assertCommandFailure(scheduleCommand, model, Messages.MESSAGE_UNKNOWN_NUSID);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        final ScheduleCommand standardCommand =
                new ScheduleCommand(person.getNusId(), person.getSchedule(), person.getRemark());
        // same values -> returns true
        ScheduleCommand commandWithSameValues =
                new ScheduleCommand(person.getNusId(), person.getSchedule(), person.getRemark());
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different schedule -> returns false
        assertFalse(standardCommand.equals(new ScheduleCommand(person.getNusId(),
                new Schedule(VALID_SCHEDULE_AMY), person.getRemark())));
        // different remark -> returns false
        assertFalse(standardCommand.equals(new ScheduleCommand(person.getNusId(),
                person.getSchedule(), new Remark(VALID_REMARK_AMY))));
    }

    @Test
    public void toStringMethod() {
        ScheduleCommand scheduleCommand = new ScheduleCommand(new NusId(VALID_NUSID_AMY),
                new Schedule(VALID_SCHEDULE_AMY), new Remark(VALID_REMARK_AMY));

        String expected = ScheduleCommand.class.getCanonicalName() + "{nusId=" + VALID_NUSID_AMY
                + ", schedule=" + VALID_SCHEDULE_AMY + ", remark=" + VALID_REMARK_AMY + "}";

        assertEquals(expected, scheduleCommand.toString());
    }
}
