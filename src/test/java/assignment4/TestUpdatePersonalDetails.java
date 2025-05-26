package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestUpdatePersonalDetails {

    private static final Path CHANGED_VALID_OUTPUT_PATH = Paths.get("Update_CHANGED.txt");
    private static final Path SAME_VALID_OUTPUT_PATH = Paths.get("Update_SAME.txt");
    private static final Path SAME_EVEN_VALID_OUTPUT_PATH = Paths.get("Update_SAME_EVEN.txt");

    private static final Path CHANGED_EXPECTED_OUTPUT_PATH = Paths.get("Update_CHANGED_expected.txt");
    private static final Path SAME_EXPECTED_OUTPUT_PATH = Paths.get("Update_SAME_expected.txt");
    private static final Path SAME_EVEN_EXPECTED_OUTPUT_PATH = Paths.get("Update_SAME_EVEN_expected.txt");

    private static Person older18;
    private static Person younger18;
    private static Person evenPerson;

    public Person createYounger18() throws Exception{
        Person younger18 = new Person();
        younger18.setPersonID("36s_d#@fAJ");
        younger18.setFirstName("Ajay");
        younger18.setLastName("Peeris");
        younger18.setAddress("26|YeahNah Road|Melbourne|Victoria|Australia");
        younger18.setBirthdate("26-02-2014");

        try {
            FileOutputStream fileStream = new FileOutputStream("Update_SAME.txt", true);
            PrintWriter outFS = new PrintWriter(fileStream);

            outFS.println(younger18.getPersonID() + " " + younger18.getFirstName() + " " + 
                younger18.getLastName() + " " + younger18.getAddress() + " " + younger18.getBirthdate());

            outFS.close();
        } 
        
        catch (FileNotFoundException e) {
            System.out.println("File could not be created or opened: " + e.getMessage());
        }

        return younger18;
    }

    public Person createOlder18() throws Exception{
        Person older18 = new Person();
        older18.setPersonID("35s_d%&fAB");
        older18.setFirstName("Paul");
        older18.setLastName("Vennat");
        older18.setAddress("17|Landscape Drive|Melbourne|Victoria|Australia");
        older18.setBirthdate("10-09-1999");

        try {
            FileOutputStream fileStream = new FileOutputStream("Update_CHANGED.txt", true);
            PrintWriter outFS = new PrintWriter(fileStream);

            outFS.println(older18.getPersonID() + " " + older18.getFirstName() + " " + 
                older18.getLastName() + " " + older18.getAddress() + " " + older18.getBirthdate());

            outFS.close();
        } 
        
        catch (FileNotFoundException e) {
            System.out.println("File could not be created or opened: " + e.getMessage());
        }
        
        return older18;
    }

    public Person createEvenID() throws Exception{
        Person evenPerson = new Person();
        evenPerson.setPersonID("26s_d#@fAJ");
        evenPerson.setFirstName("Evan");
        evenPerson.setLastName("Nombour");
        evenPerson.setAddress("22|Salmon Road|Melbourne|Victoria|Australia");
        evenPerson.setBirthdate("21-04-2000");

        try {
            FileOutputStream fileStream = new FileOutputStream("Update_SAME_EVEN.txt", true);
            PrintWriter outFS = new PrintWriter(fileStream);

            outFS.println(evenPerson.getPersonID() + " " + evenPerson.getFirstName() + " " + 
                evenPerson.getLastName() + " " + evenPerson.getAddress() + " " + evenPerson.getBirthdate());

            outFS.close();
        } 
        
        catch (FileNotFoundException e) {
            System.out.println("File could not be created or opened: " + e.getMessage());
        }

        return evenPerson;
    }
    
    @BeforeAll
    public static void clearValidOutput() throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Update_CHANGED.txt", false))) {
            writer.print("");
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Update_SAME_EVEN.txt", false))) {
            writer.print("");
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Update_SAME.txt", false))) {
            writer.print("");
        }
        // older18 = createOlder18();
        // younger18 = createYounger18();
        // evenPerson = createEvenID();

    }

    @AfterAll
    public static void verifyUpdateFileOutput() throws IOException {
        List<String> actualChangedLines = Files.readAllLines(CHANGED_VALID_OUTPUT_PATH);
        List<String> actualSameLines = Files.readAllLines(SAME_VALID_OUTPUT_PATH);
        List<String> actualSameEVENLines = Files.readAllLines(SAME_EVEN_VALID_OUTPUT_PATH);

        List<String> expectedChangedLines = Files.readAllLines(CHANGED_EXPECTED_OUTPUT_PATH);
        List<String> expectedSameLines = Files.readAllLines(SAME_EXPECTED_OUTPUT_PATH);
        List<String> expectedSameEVENLines = Files.readAllLines(SAME_EVEN_EXPECTED_OUTPUT_PATH);

        Assertions.assertEquals(expectedChangedLines, actualChangedLines, "Final output in UPDATE_CHANGED.txt does not match expected output.");
        Assertions.assertEquals(actualSameLines, expectedSameLines, "Final output in UPDATE_SAME.txt does not match expected output.");
        Assertions.assertEquals(actualSameEVENLines, expectedSameEVENLines, "Final output in UPDATE_SAME_EVEN.txt does not match expected output.");
    }

    // @BeforeEach
    // public static void resetPeople() throws Exception{

    // }

    @ParameterizedTest
    @CsvSource({
        "35s_d%&fAB, Sam, Donk, 32|Changed Drive|Melbourne|Victoria|Australia, NULL",
        "65s_d%&fAB, Bob, John, 48|Again Road|Melbourne|Victoria|Australia, NULL",
        "75s_d%&fAB, Sharon, Parm, 62|OnceMore Drive|Melbourne|Victoria|Australia, NULL"
    })
    void testUpdatePersonalDetails_ValidInputs(String id, String firstName, String lastName, String address, String birthdate) throws Exception {
        assertTrue(older18.updatePersonalDetails(id, firstName, lastName, address, birthdate,"Update_CHANGED.txt"));

        List<String> lines = Files.readAllLines(CHANGED_VALID_OUTPUT_PATH);
        String lastLine = lines.get(lines.size() - 1);

        String expectedLine = ("UPDATED: " + id + " " + firstName + " " + lastName + " " + address + " 10-09-1999");

        assertEquals(expectedLine, lastLine, "Output doesn't match expected output.");

    }

    @ParameterizedTest
    @CsvSource({
        "NULL, NULL, NULL, 32|Changed Drive|Melbourne|Victoria|Australia, NULL",
        "NULL, NULL, NULL, 48|Again Road|Melbourne|Victoria|Australia, NULL",
        "NULL, NULL, NULL, 62|OnceMore Drive|Melbourne|Victoria|Australia, NULL"
    })
    void testUpdatePersonalDetails_ValidAddressUnder18(String id, String firstName, String lastName, String address, String birthdate) throws Exception {
        assertFalse(younger18.updatePersonalDetails(id, firstName, lastName, address, birthdate, "Update_SAME.txt"));

    }

    @ParameterizedTest
    @CsvSource({
        "35s_d%&fAB, NULL, NULL, NULL, 15-11-1990",
        "NULL, Sam, NULL, NULL, 13-10-1980",
        "NULL, NULL, Parm, NULL, 23-12-1992"
    })
    void testUpdatePersonalDetails_ValidBirthdayValidDetails(String id, String firstName, String lastName, String address, String birthdate) throws Exception {
        assertFalse(younger18.updatePersonalDetails(id, firstName, lastName, address, birthdate, "Update_SAME.txt"));

    }

    @ParameterizedTest
    @CsvSource({
        "35s_d%&fAB, NULL, NULL, NULL, NULL",
        "55s_d%&fAB, NULL, NULL, NULL, NULL",
        "75s_d%&fAB, NULL, NULL, NULL, NULL"
    })
    void testUpdatePersonalDetails_ValidIdEvenCurrId(String id, String firstName, String lastName, String address, String birthdate) throws Exception {
        assertFalse(evenPerson.updatePersonalDetails(id, firstName, lastName, address, birthdate, "Update_SAME_EVEN.txt"));
    }

    @ParameterizedTest
    @CsvSource({
        "NULL, NULL, NULL, NULL, 15/11/1990",
        "NULL, NULL, NULL, NULL, 13-16-1980",
        "NULL, NULL, NULL, NULL, 23.12.1992"
    })
    void testUpdatePersonalDetails_InvalidBirthday(String id, String firstName, String lastName, String address, String birthdate) throws Exception {
        assertFalse(younger18.updatePersonalDetails(id, firstName, lastName, address, birthdate, "Update_SAME.txt"));
    }

    //TEST


}
