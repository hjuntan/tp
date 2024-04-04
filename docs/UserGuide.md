---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# AronaPro's User Guide

AronaPro is a **desktop app** catered primarily for Computing Professors and Teaching Assistants(TAs) to manage your students/professors/TAs contacts efficiently, optimised for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). 
Our product ensures that you can always centralise their contacts, connect and coordinate with them. 


<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `addressbook.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try to get started with our application:

   * `view` : Lists all contacts.

   * `add id/E1234567 n/John Doe p/98765432 e/johnd@example.com t/Student g/CS2103T-T15` : Adds a student with `NAME` John Doe with `NUSD` of E1234567 to the Address Book.

   * `delete id/E1234567` : Deletes the person with `NUSID` of E1234567.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features


### **Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  > `add id/NUSID n/NAME`, `NAME` is a parameter which can be used.

* Items in square brackets are optional.<br>
  > `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or simply as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  > `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  > if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `view`, `exit` and `clear`) will be ignored.<br>
  > if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add id/NUSID n/NAME p/PHONE_NUMBER e/EMAIL t/TAG [g/GROUP]…​`

> Note:
> * The `NUSID` **must be a 7-digit number following an 'E'**.

**Tip:** A person can have 0 or more groups

Examples:
* `add id/E1234567 n/John Doe p/98765432 e/johnd@example.com t/Student g/CS2103T`
  > This command would add a person with `NUSID` of E1234567, `NAME` of John Doe, `PHONE_NUMBER` of 98765432, `EMAIL` of johnd@example.com
    `TAG` of Student, `GROUP` of CS2103T-T15 into the address book.
  >
  > ![add-new-person](images/add/add-new-person.png)
* `add id/E7654321 n/Betsy Crowe t/TA e/betsycrowe@example.com p/92345678`
  > This command would add a person with `NUSID` of E7654321, `NAME` of Betsy Crowe, `TAG` of TA,
    `EMAIL` of betsycrowe@example.com, `PHONE_NUMBER` of 92345678 into the address book.
  > 
  > ![add-new-TA](images/add/add-new-ta-without-group.png)

### Viewing all persons : `view`

Shows a list of all persons in the address book.

Format: `view`

### Editing a person's information : `edit`

Edits the information of a person of a specified `NUSID` in the address book.

Format: `edit id/NUSID [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG] [g/GROUP]` 

> Note:
> * The `NUSID` refers to the NUSID shown in the displayed person list.
> * The `NUSID` **must be a 7-digit number following an 'E'**.
> * At least one of the optional fields must be provided.
> * Existing values will be replaced by and updated to the new input values.
> * When editing tags, the valid forms have to be either 1 of these:  Professor, TA, Student, None.
> * Attempt to edit a person with `NUSID` not in the address book would result in an error message.


Examples:
*  `edit id/E0123456 p/91234567 e/johndoe@example.com` 
   > This command would edit the phone number and email address of the person with `NUSID` E0123456 to be `91234567` and `johndoe@example.com` respectively.
   >
   > ![edit-phone-and-email](images/edit/edit-phone-and-email.png)
*  `edit id/E1234567 n/Betsy t/Professor` 
   > This command would edit the name and the tag of the person with `NUSID` E1234567 to be `Betsy` and `Professor` respectively.
   >
   > ![edit-name-and-tag](images/edit/edit-name-and-tag.png)

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG] [g/GROUP] [g/MORE GROUPS]`

> Note:
> * The NAME search is case-insensitive. e.g `hans` will match `Hans`.
> * The order of NAME keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
> * Only full words will be matched for NAME e.g. `Han` will not match `Hans`.
> * Students matching ANY word will be selected for NAME (e.g. `Hans Bo` will fetch `Hans Gruber`, `Bo Yang`).
> * The PHONE_NUMBER search matches student that has a number that STARTS WITH the query (e.g `9123` fetches `91237654`).
> * The EMAIL search uses a PARTIAL, case-insensitive match. (e.g. `charles` matches `PrinceCharles@kingston.com`).
> * The TAG search uses an EXACT case-sensitive match. 
> * The GROUP search fetches students with ALL specified groups (e.g `g/CS2101 g/CS2103T` matches a person who minimally has BOTH these Groups).
>* Persons matching all parameters will be returned (i.e. `AND` search).
  

Examples:
* `find n/roy` 
  > This command would return `Roy G Biv` and `Roy Balakrishnan`. 
  > 
  > ![findbyName](images/find/find-by-name.png)
* `find n/roy g/CS2101`
  > This command would return `Roy Balakrishnan` who is the only person matching both the 2 conditions.
  > 
  > ![findByNameAndGroup](images/find/find-by-name-and-group.png)

### Deleting a person : `delete`

Deletes person of a specified `NUSID` from the address book.

Format 1: `delete id/NUSID`

> Note:
> * Deletes the person of a specified `NUSID`.
> * The `NUSID` refers to the NUSID shown in the displayed person list.
> * The `NUSID` **must be a 7-digit number following an 'E'**.

Examples:
* `delete id/E0123456` 
  > This commmand will delete an existing person with `NUSID` of "E0123456".
  >
  >  ![delete-person](images/delete/delete-person.png)

Format 2: `delete g/group`

> Note:
> * Deletes the person in a specified `group`.
> * The group refers to the group shown in the displayed person list.
> * The group **must exist in the address book beforehand**.

Examples:
* `delete g/CS2103T`
  > This command will delete an existing person with `group` of "CS2013-T15".
  > 
  > ![delete-group](images/delete/delete-group.png)

### Assigning a person to a group : `group`

Assigns either a group or a tag to a person of a specified `NUSID` from the address book.

Format: `group [id/NUSID] [g/GROUP] [t/TAG]`

> Note:
> * Groups the person of a specified `NUSID`.
> * The `NUSID` refers to the NUSID shown in the displayed person list.
> * The `NUSID` **must be a 7-digit number following an 'E'**
> * At least one of the optional fields must be provided.
> * When editing tags, the valid forms have to be either 1 of these:  Professor, TA, Student, None

Examples:
* `group id/E0123456 g/CS2101`
  > This command will assign or change a group of the person with `NUSID` E0123456 to 'CS2101'.
  >
  > ![assign-group](images/group/assign-group.png)
* `group id/E0123456 t/TA` 
 > This command will assign or change the tag of the person with `NUSID` E0123456 to 'TA'.
 > ![assign-tag](images/group/assign-tag.png)


### Schedule a meeting with a person: `schedule`

Schedule a meeting with a person in the address book.

Format: `schedule id/NUSID d/DATE [r/REMARK]`

> Note: 
> * Schedule a meeting with a person of the specified `nusId` on the specified `date` with an optional `remark`.
> * The `NUSID` refers to the NUSID shown in the displayed person list.
> * The `NUSID` **must be a 7-digit number following an 'E'**
> * If `date` and `remark` are not provided, the schedule will be removed.
>* If `remark` is provided, `date` must be provided as well.

**Tip:** The `date` must be in one of the formats: `DD/MM/YYYY`, `DD-MM-YYYY`, `DD.MM.YYYY`, `MMM DD, YYYY`, `DD MMM YYYY`.


Examples:
* `schedule id/E0123456 s/12-12/2024 r/Consultation` 
  > This command will schedule a meeting with the person of `NUSID` E0123456, with `DATE` on 12th December 2024 and a `REMARK` of Consultation.
  > If there was a previous date, or remark, it will be replaced!
  > 
  > ![schedule-with-remark](images/schedule/schedule-with-remark.png)
* `schedule id/E0123456 d/Dec 12, 2021`
  > This command will schedule a meeting with the person of `NUSID` E0123456, with `DATE` on 12th December 2024.
  > If there was a previous date, it will be replaced, similarly the previous remark would be removed.
  >
  > ![schedule-without-remark](images/schedule/schedule-without-remark.png)
* `schedule id/E0123456` 
  > This command will remove the schedule with the person of `NUSID` E0123456.
  >
  > ![remove-schedule](images/schedule/remove-schedule.png) 

### Pinning a person: `pin`

Pins a person to the top of the address book.

Format: `pin id/NUSID`

> Note:
> * The `NUSID` refers to the NUSID shown in the displayed person list.
> * The `NUSID` **must be a 7-digit number following an 'E'**.

Examples:

* `pin id/E0123456` 
  > This command will pin a student with `NUSID` of "E0123456".
  >
  > ![pin](images/pin/pin.png) 


### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

![clear](images/clear.png)

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action       | Format, Examples                                                                                                                           |
|--------------|--------------------------------------------------------------------------------------------------------------------------------------------|
| **Add**      | `add n/NAME p/PHONE_NUMBER e/EMAIL t/TAG [g/GROUP]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com t/Student g/CS2103T-T15` |
| **Clear**    | `clear`                                                                                                                                    |
| **Delete**   | `delete id/NUSID`<br> e.g., `delete id/E01234567 OR delete g/GROUP` <br> e.g., `delete g/CS2103-T15`                                       |
| **Edit**     | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                |
| **Find**     | `find [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG] [g/GROUP]`<br> e.g., `find n/James g/CS2103T`                                            |
| **Group**    | `group [id/NUSID] [g/GROUP] [t/TAG] `                                                                                                      |
| **Schedule** | `schedule id/NUSID s/DATE [r/REMARK]` <br> e.g., `schedule id/E1234567 s/12-12-2021 r/Consultation`                                        |
| **Pin**      | `pin id/NUSID`                                                                                                                             |
| **View**     | `view`                                                                                                                                     |
| **Help**     | `help`                                                                                                                                     |

