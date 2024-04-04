package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NUSID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCHEDULE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NUSID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SCHEDULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUSID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.NusId;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Schedule;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();
    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsScheduleCommand() {
        ScheduleCommand expectedScheduleCommand = new ScheduleCommand(new NusId(VALID_NUSID_AMY),
                new Schedule(VALID_SCHEDULE_AMY), new Remark(VALID_REMARK_AMY));

        ScheduleCommand expectedScheduleCommandRemove = new ScheduleCommand(new NusId(VALID_NUSID_AMY),
                new Schedule(""), new Remark(""));

        assertParseSuccess(parser, NUSID_DESC_AMY + SCHEDULE_DESC_AMY + REMARK_DESC_AMY,
                expectedScheduleCommand);

        assertParseSuccess(parser, NUSID_DESC_AMY, expectedScheduleCommandRemove);
    }

    @Test
    public void parse_missingArg_throwsParseException() {
        // Missing NusId prefix
        assertParseFailure(parser, VALID_NUSID_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        // Missing Remark
        assertParseFailure(parser, NUSID_DESC_AMY + SCHEDULE_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        // Missing Schedule
        assertParseFailure(parser, NUSID_DESC_AMY + REMARK_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        // Missing NusId
        assertParseFailure(parser, SCHEDULE_DESC_AMY + REMARK_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        // Missing all prefixes
        assertParseFailure(parser, VALID_NUSID_AMY + VALID_SCHEDULE_AMY + VALID_REMARK_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Schedule constraints
        assertParseFailure(parser, NUSID_DESC_AMY + INVALID_SCHEDULE_DESC + REMARK_DESC_AMY,
                Schedule.MESSAGE_CONSTRAINTS);

        // NusId constraints
        assertParseFailure(parser, INVALID_NUSID_DESC + SCHEDULE_DESC_AMY + REMARK_DESC_AMY,
                NusId.MESSAGE_CONSTRAINTS);

        // Remark constraints
        assertParseFailure(parser, NUSID_DESC_AMY + SCHEDULE_DESC_AMY + INVALID_REMARK_DESC,
                Remark.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_repeatedValue_failure() {
        // Repeated Schedule
        assertParseFailure(parser, NUSID_DESC_AMY + SCHEDULE_DESC_AMY + SCHEDULE_DESC_AMY + REMARK_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SCHEDULE));

        // Repeated Remark
        assertParseFailure(parser, NUSID_DESC_AMY + SCHEDULE_DESC_AMY + REMARK_DESC_AMY + REMARK_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

        // Repeated NusId
        assertParseFailure(parser, NUSID_DESC_AMY + NUSID_DESC_AMY + SCHEDULE_DESC_AMY + REMARK_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NUSID));
    }
}
