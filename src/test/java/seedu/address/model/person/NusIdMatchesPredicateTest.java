package seedu.address.model.person;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



class NusIdMatchesPredicateTest {

    @Test
    public void test_matchingNusId_returnsTrue() {
        Person person = new PersonBuilder().withNusId("E0123456").build();

        NusIdMatchesPredicate fullMatch = new NusIdMatchesPredicate("E0123456");
        assertTrue(fullMatch.test(person));

        NusIdMatchesPredicate partialPrefixMatch = new NusIdMatchesPredicate("E0123");
        assertTrue(partialPrefixMatch.test(person));
    }

    @Test
    public void test_noMatchingNusId_returnsFalse() {
        Person person = new PersonBuilder().withNusId("E0123456").build();

        NusIdMatchesPredicate fullMatch = new NusIdMatchesPredicate("E0123457");
        assertFalse(fullMatch.test(person));

        NusIdMatchesPredicate partialPrefixMatch = new NusIdMatchesPredicate("E0321");
        assertFalse(partialPrefixMatch.test(person));
    }
    @Test
    void testToString() {
        String nusIdToMatch = "E01234567";
        NusIdMatchesPredicate p = new NusIdMatchesPredicate(nusIdToMatch);
        String expected = NusIdMatchesPredicate.class.getCanonicalName() + "{nusIdKeyword=" + nusIdToMatch + "}";
        assertEquals(p.toString(), expected);
    }
}
