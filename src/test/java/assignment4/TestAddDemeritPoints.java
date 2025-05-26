package assignment4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// import java.util.HashMap;
import java.util.List;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;

public class TestAddDemeritPoints {
    
    // Function for adding demerit points looks like this
    // person.addDemeritPoints(String date, int demeritPoints)

    // Creation of a new person object for testing purposes
    private Person person;

    // Paths to txt file's used for testing
    private static final Path VALID_OUTPUT_PATH = Paths.get("Demerit.txt");
    // private static final Path EXPECTED_OUTPUT_PATH = Paths.get("Demerit_expected.txt");


    // Initialising a under 21 year old for testing
    public Person createPersonUnder21() throws Exception {
        person = new Person();
        person.addPerson("56s_d%&fAB", "Anthony", "Duiker", "29|Landscape Drive|Melbourne|Victoria|Australia", "10-09-2007");
        return person;
        }

    // Initialising a over 21 year old for testing
    public Person createPersonOver21() throws Exception {
        person = new Person();
        person.addPerson("56s_d%&fAB", "Anthony", "Duiker", "29|Landscape Drive|Melbourne|Victoria|Australia", "10-09-2001");
        return person;
        }

    // Clears output file before the test
    @BeforeAll
    public static void clearValidOutput() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Demerit.txt", false))) {
            writer.print("");
        }
    }

    /* 
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
        */
    

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
        person = createPersonUnder21();

        // Run the function with input data
        String outcome = person.addDemeritPoints(date, demeritPoints);
        assertEquals("Success", outcome);

        // Read file lines
        List<String> lines = Files.readAllLines(VALID_OUTPUT_PATH);
        String lastLine = lines.get(lines.size() - 1);
        String expectedLine = person.getPersonID() + " " + date + " " + demeritPoints;

        // Compare file lines
        assertEquals(expectedLine, lastLine, "Demerit output does not match expected output.");
    }


    @ParameterizedTest
    @CsvSource({
        "10.09.2001, 3",
        "10-09-01, 3",
        "10-may-2001, 3",
        "101-09-2001, 3"
    })

    // Testing for invalid date inputs
    public void testDemeritInvalidDates(String date, int demeritPoints) throws Exception {
        // Create person for test case 
        person = createPersonOver21();

        // Run the function with input data
        String outcome = person.addDemeritPoints(date, demeritPoints);
        // Nothing should be written to a file so this should do the testing
        assertEquals("Failed", outcome);
    }


    @ParameterizedTest
    @CsvSource({
        "10-09-2001, 0",
        "10-09-2001, 7",
        "10-09-2001, -1",
    })

    // Testing for invalid demerit points
    public void testDemeritInavalidInteger(String date, int demeritPoints) throws Exception {
        person = createPersonOver21();
        String outcome = person.addDemeritPoints(date, demeritPoints);
        assertEquals("Failed", outcome);
    }

    // Test over 21 suspensions
    @Test
    public void testOver21Suspensions() throws Exception {
        // Clear output file
        Files.write(Paths.get("Demerit.txt"), new byte[0]);
        person = createPersonOver21();

        // Manually enter each point to test stacking demerits
        // Should not be suspended yet
        person.addDemeritPoints("10-09-2024", 4);
        assertEquals(false, person.isSuspended());

        // Should not be suspended yet
        person.addDemeritPoints("10-10-2024",5);
        assertEquals(false, person.isSuspended());

        // Should be suspended
        String result = person.addDemeritPoints("10-11-2024", 5);
        assertEquals("Success", result);
        assertEquals(true, person.isSuspended());
    }

    // Test under 21 suspensions
    @Test
    public void testUnder21Suspensions() throws Exception {
        // Clear output file
        Files.write(Paths.get("Demerit.txt"), new byte[0]);
        person = createPersonUnder21();

        // Manually enter each point to test stacking demerits
        // Should not be suspended yet
        person.addDemeritPoints("10-09-2024", 2);
        assertEquals(false, person.isSuspended());

        // Should not be suspended yet
        person.addDemeritPoints("10-10-2024",2);
        assertEquals(false, person.isSuspended());

        // Should be suspended
        String result = person.addDemeritPoints("10-11-2024", 3);
        assertEquals("Success", result);
        assertEquals(true, person.isSuspended());
    }
}
