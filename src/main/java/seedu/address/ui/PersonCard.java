package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag.TagType;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label tag;
    @FXML
    private Label email;
    @FXML
    private Label nusId;
    @FXML
    private Label schedule;
    @FXML
    private Label remark;
    @FXML
    private FlowPane groups;

    private Insets tagPadding = new Insets(0.69, 4.20, 0.69, 4.20);
    private BackgroundFill tagFill = new BackgroundFill(Color.HOTPINK, new CornerRadii(4.20), new Insets(0));
    private Background tagBackground = new Background(tagFill);

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        nusId.setText(person.getNusId().value);
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        setUiTag();
        email.setText(person.getEmail().value);
        schedule.setText(person.getSchedule().date);
        remark.setText(person.getRemark().value);
        person.getGroups().stream()
                .sorted(Comparator.comparing(group -> group.groupName))
                .forEach(group -> groups.getChildren().add(new Label(group.groupName)));
    }

    /**
     * Sets the UI tag for the {@code Person}.
     * If the tag is None, the tag will not be displayed.
     */
    public void setUiTag() {
        TagType tagType = person.getTag().value;
        if (tagType != TagType.None) {
            tag.setText(tagType.toString());
            tag.setPadding(tagPadding);
            tag.setBackground(tagBackground);
        }
    }
}
