package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Person's schedule in the address book with the user.
 * Guarantees: immutable; is valid as declared in {@link #isValidSchedule(String)}
 */
public class Schedule {

    public static final String MESSAGE_CONSTRAINTS =
            "Schedule should contain only dates formatted as \"DD/MM/YYYY\", \"DD-MM-YYYY\", "
                    + "\"DD.MM.YYYY\", \"DD MMM YYYY\", \"MMM DD, YYYY\"";

    public static final List<String> VALIDATION_REGEX_WITH_DATA = new ArrayList<>(
            List.of(
                    "^([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2})$",
                    "^(0?[1-9]|[1-2][0-9]|3[01])\\s+(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+\\d{4}$",
                    "^(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s+(0?[1-9]|[1-2][0-9]|3[01]),\\s+\\d{4}$"));

    public final String date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Schedule(String date) {
        requireNonNull(date);
        if (date != "") {
            checkArgument(isValidSchedule(date), MESSAGE_CONSTRAINTS);
        }
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidSchedule(String test) {
        for (String regex : VALIDATION_REGEX_WITH_DATA) {
            if (test.matches(regex)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPresent() {
        return !date.equals("");
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Schedule)) {
            return false;
        }

        Schedule otherSchedule = (Schedule) other;
        return date.equals(otherSchedule.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
