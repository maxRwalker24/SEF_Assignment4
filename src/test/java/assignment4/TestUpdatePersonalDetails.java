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
// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestUpdatePersonalDetails {

    //Valid outputs and input paths
    private static final Path CHANGED_VALID_OUTPUT_PATH = Paths.get("Update_CHANGED.txt");
    private static final Path SAME_VALID_OUTPUT_PATH = Paths.get("Update_SAME.txt");
    private static final Path SAME_EVEN_VALID_OUTPUT_PATH = Paths.get("Update_SAME_EVEN.txt");

    private static final Path CHANGED_EXPECTED_OUTPUT_PATH = Paths.get("Update_CHANGED_expected.txt");
    private static final Path SAME_EXPECTED_OUTPUT_PATH = Paths.get("Update_SAME_expected.txt");
    private static final Path SAME_EVEN_EXPECTED_OUTPUT_PATH = Paths.get("Update_SAME_EVEN_expected.txt");

    private static Person younger18;
    private static Person older18;
    private static Person evenPerson;

    //Creating a person thats younger than 18 to test condition
    public static Person createYounger18() throws Exception{
        Person younger18 = new Person();
        younger18.addPerson("36s_d#@fAJ", "Ajay", "Peeris", "26|YeahNah Road|Melbourne|Victoria|Australia", "26-02-2014");


        // Create a text file with the details
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

    // Create a person thats older than 18, all valid inputs are used with this gut
    public static Person createOlder18() throws Exception{
        Person older18 = new Person();
        older18.addPerson("35s_d%&fAB", "Paul", "Vennat", "17|Landscape Drive|Melbourne|Victoria|Australia", "10-09-1999");
       

        // Create a text file with the details
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

    //Create a person with an where the first number of his ID is even
    public static Person createEvenID() throws Exception{
        Person evenPerson = new Person();
        evenPerson.addPerson("26s_d#@fAJ", "Evan", "Nombour", "22|Salmon Road|Melbourne|Victoria|Australia","21-04-2000");

        // Create a text file with the details
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
    
    //Before all tests....
    @BeforeAll
    public static void clearValidOutput() throws Exception {
        //clear the output files
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Update_CHANGED.txt", false))) {
            writer.print("");
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Update_SAME_EVEN.txt", false))) {
            writer.print("");
        }
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Update_SAME.txt", false))) {
            writer.print("");
        }

        //create the people to test on
        older18 = createOlder18();
        younger18 = createYounger18();
        evenPerson = createEvenID();

    }

    //After all the tests//
    @AfterAll
    public static void verifyUpdateFileOutput() throws IOException {
        //Check if the output files match the expected output files, if not give the messgae
        List<String> actualChangedLines = Files.readAllLines(CHANGED_VALID_OUTPUT_PATH);
        List<String> actualSameLines = Files.readAllLines(SAME_VALID_OUTPUT_PATH);
        List<String> actualSameEVENLines = Files.readAllLines(SAME_EVEN_VALID_OUTPUT_PATH);

        List<String> expectedChangedLines = Files.readAllLines(CHANGED_EXPECTED_OUTPUT_PATH);
        List<String> expectedSameLines = Files.readAllLines(SAME_EXPECTED_OUTPUT_PATH);
        List<String> expectedSameEVENLines = Files.readAllLines(SAME_EVEN_EXPECTED_OUTPUT_PATH);

        Assertions.assertEquals(expectedChangedLines, actualChangedLines, "Final output in UPDATE_CHANGED.txt does not match expected output.");
        Assertions.assertEquals(expectedSameLines, actualSameLines, "Final output in UPDATE_SAME.txt does not match expected output.");
        Assertions.assertEquals(expectedSameEVENLines, actualSameEVENLines, "Final output in UPDATE_SAME_EVEN.txt does not match expected output.");
    }

    //Tests run for each line of the csv source
    @ParameterizedTest
    @CsvSource({
        "95s_d%&fAB, Sam, Donk, 32|Changed Drive|Melbourne|Victoria|Australia, NULL",
        "55s_d%&fAB, Bob, John, 48|Again Road|Melbourne|Victoria|Australia, NULL",
        "75s_d%&fAB, Sharon, Parm, 62|OnceMore Drive|Melbourne|Victoria|Australia, NULL"
    })
    void testUpdatePersonalDetails_ValidInputs(String id, String firstName, String lastName, String address, String birthdate) throws Exception {

        assertTrue(older18.updatePersonalDetails(id, firstName, lastName, address, birthdate,"Update_CHANGED.txt"));
        //Make sure function returns true as all inputs are valid

        List<String> lines = Files.readAllLines(CHANGED_VALID_OUTPUT_PATH);
        String lastLine = lines.get(lines.size() - 1);
        //read the last line of the output file and check it against the expectedLine

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

        //Make sure function returns false
        assertFalse(younger18.updatePersonalDetails(id, firstName, lastName, address, birthdate, "Update_SAME.txt"));

    }

    @ParameterizedTest
    @CsvSource({
        "35s_d%&fAB, NULL, NULL, NULL, 15-11-1990",
        "NULL, Sam, NULL, NULL, 13-10-1980",
        "NULL, NULL, Parm, NULL, 23-12-1992"
    })
    void testUpdatePersonalDetails_ValidBirthdayValidDetails(String id, String firstName, String lastName, String address, String birthdate) throws Exception {

        //Make sure function returns false
        assertFalse(younger18.updatePersonalDetails(id, firstName, lastName, address, birthdate, "Update_SAME.txt"));

    }

    @ParameterizedTest
    @CsvSource({
        "35s_d%&fAB, NULL, NULL, NULL, NULL",
        "55s_d%&fAB, NULL, NULL, NULL, NULL",
        "75s_d%&fAB, NULL, NULL, NULL, NULL"
    })
    void testUpdatePersonalDetails_ValidIdEvenCurrId(String id, String firstName, String lastName, String address, String birthdate) throws Exception {

        //Make sure function returns false
        assertFalse(evenPerson.updatePersonalDetails(id, firstName, lastName, address, birthdate, "Update_SAME_EVEN.txt"));
    }

    @ParameterizedTest
    @CsvSource({
        "NULL, NULL, NULL, NULL, 15/11/1990",
        "NULL, NULL, NULL, NULL, 13-16-1980",
        "NULL, NULL, NULL, NULL, 23.12.1992"
    })
    void testUpdatePersonalDetails_InvalidBirthday(String id, String firstName, String lastName, String address, String birthdate) throws Exception {

        //Make sure function returns false
        assertFalse(younger18.updatePersonalDetails(id, firstName, lastName, address, birthdate, "Update_SAME.txt"));
    }

   

}
