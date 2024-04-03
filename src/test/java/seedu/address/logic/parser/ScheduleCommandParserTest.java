package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.NusId;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Schedule;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();
    @Test
    public void parse_invalidPreamble_failure() {
        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "E0123456 some random string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "E0123456 i/ string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsScheduleCommand() {
        NusId testNusId = new NusId("E1234567");
        Schedule testSchedule = new Schedule("12-12-2012");
        Remark testRemark = new Remark("Test remark");
        assertParseSuccess(parser, " id/E1234567 s/12-12-2012 r/Test remark",
                new ScheduleCommand(testNusId, testSchedule, testRemark));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "E1234567",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }
}
