package assignment4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestAddDemeritPoints {
    
    // Function for adding demerit points looks like this
    // person.addDemeritPoints(String date, int demeritPoints)

    // Creation of a new person object for testing purposes
    private Person person;

    // Paths to txt file's used for testing
    private static final Path VALID_OUTPUT_PATH = Paths.get("Demerit.txt");
    private static final Path EXPECTED_OUTPUT_PATH = Paths.get("Demerit_expected.txt");


    // Initialising a under 21 year old for testing
    public void createPersonUnder21() throws Exception {
        person = new Person();
        person.setPersonID("56s_d%&fAB");
        person.setFirstName("Anthony");
        person.setLastName("Duiker");
        person.setAddress("29|Landscape Drive|Melbourne|Victoria|Australia");
        person.setBirthdate("10-09-2007");
        person.setDemeritPoints(new HashMap<>());
        person.setSuspended(false);
        }

    // Initialising a over 21 year old for testing
    public void createPersonOver21() throws Exception {
        person = new Person();
        person.setPersonID("56s_d%&fAB");
        person.setFirstName("Anthony");
        person.setLastName("Duiker");
        person.setAddress("29|Landscape Drive|Melbourne|Victoria|Australia");
        person.setBirthdate("10-09-2001");
        person.setDemeritPoints(new HashMap<>());
        person.setSuspended(false);
        }

    // Clears output file before the test
    @BeforeAll
    public static void clearValidOutput() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Demerit.txt", false))) {
            writer.print("");
        }
    }

    // Verifies output data
    @AfterAll
    public static void verifyDemeritFileOutput() throws IOException {
        // Read actual output
        List<String> actualLines = Files.readAllLines(VALID_OUTPUT_PATH);

        // Read expected output
        List<String> expectedLines = Files.readAllLines(EXPECTED_OUTPUT_PATH);

        // Assert equality
        Assertions.assertEquals(expectedLines, actualLines, "Final output in Demerit.txt does not match expected output.");
    }
    

    // TESTS START HERE
    @ParameterizedTest
    @CsvSource({
        "14-01-2024, 1",
        "23-05-2025, 3",
        "10-09-2005, 6"
    })

    // Testing some valid inputs
    public void testDemeritValidInput(String date, int demeritPoints) throws Exception {
        // Create person for test case
        createPersonUnder21();

        // Run the function with input data
        String outcome = person.addDemeritPoints(date, demeritPoints);
        assertEquals("Success", outcome);

        // Read file lines
        List<String> lines = Files.readAllLines(VALID_OUTPUT_PATH);
        List<String> expectedLines = Files.readAllLines(EXPECTED_OUTPUT_PATH);

        // Compare file lines
        assertEquals(expectedLines, lines, "Demerit output does not match expected output.");
    }


    @ParameterizedTest
    @CsvSource({
        "10.09.2001, 3",
        "10/09/01, 3",
        "10/may/2001, 3",
        "101/09/2001, 3"
    })

    // Testing for invalid date inputs
    public void testDemeritInvalidDates(String date, int demeritPoints) throws Exception {
        // Create person for test case 
        createPersonOver21();

        // Run the function with input data
        String outcome = person.addDemeritPoints(date, demeritPoints);
        // Nothing should be written to a file so this should do the testing
        assertEquals("Failed", outcome);
    }


    @ParameterizedTest
    @CsvSource({
        "10/09/2001, 0",
        "10/09/2001, 7",
        "10/09/2001, -1",
    })

    // Testing for invalid demerit points
    public void testDemeritInavalidInteger(String date, int demeritPoints) throws Exception {
        createPersonOver21();
        String outcome = person.addDemeritPoints(date, demeritPoints);
        assertEquals("Failed", outcome);
    }

    // Test over 21 suspensions


    // Test under 21 suspensions
    


}
