package assignment4;

import static org.junit.jupiter.api.Assertions.*;


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


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestAddPerson {

    private static final Path VALID_OUTPUT_PATH = Paths.get("Person.txt");
    private static final Path EXPECTED_OUTPUT_PATH = Paths.get("Person_expected.txt");
    
    @BeforeAll
    public static void clearValidOutput() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Person.txt", false))) {
            writer.print("");
        }
    }

    @AfterAll
    public static void verifyPersonFileOutput() throws IOException {
        // Read actual output
        List<String> actualLines = Files.readAllLines(VALID_OUTPUT_PATH);

        // Read expected output
        List<String> expectedLines = Files.readAllLines(EXPECTED_OUTPUT_PATH);

        // Assert equality
        Assertions.assertEquals(expectedLines, actualLines, "Final output in Person.txt does not match expected output.");
    }

    @ParameterizedTest
    @CsvSource({
        "56s_d%&fAB, Max, Walker, 32|Highland Street|Melbourne|Victoria|Australia, 15-11-1990",
        "78AA@@BBCC, Jane, Smith, 45|Main Avenue|Sydney|Victoria|Australia, 01-01-1985",
        "98ZZ##YYXX, John, Doe, 12|Park Road|Brisbane|Victoria|Australia, 31-12-2000"
    })
    void testAddPerson_ValidInputs(String id, String firstName, String lastName, String address, String birthdate) throws IOException {
        Person person = new Person();
        assertTrue(person.addPerson(id, firstName, lastName, address, birthdate));

        // Read the last line of the file
        List<String> lines = Files.readAllLines(VALID_OUTPUT_PATH);
        String lastLine = lines.get(lines.size() - 1);

        // Build the expected line format 
        String expectedLine = String.format("%s %s %s %s %s", id, firstName, lastName, address, birthdate);

        assertEquals(expectedLine, lastLine, "Written line does not match expected output.");

    }

    @ParameterizedTest
    @CsvSource({
        "12s_d%&fAB, Max, Walker, 32|Highland Street|Melbourne|Victoria|Australia, 15-11-1990",
        "AAs_d%&fAB, Max, Walker, 32|Highland Street|Melbourne|Victoria|Australia, 15-11-1990",
        "!!s_d%&fAB, Max, Walker, 32|Highland Street|Melbourne|Victoria|Australia, 15-11-1990",
    })
    void testAddPerson_InvalidID(String id, String firstName, String lastName, String address, String birthdate) {
        Person person = new Person();
        assertFalse(person.addPerson(id, firstName, lastName, address, birthdate));

    }

    @ParameterizedTest
    @CsvSource({
        "56s_d%&fAB, Max, Walker, AZ|Highland Street|Melbourne|Victoria|Australia, 15-11-1990",
        "56s_d%&fAB, Max, Walker, 32|H12345|Melbourne|Victoria|Australia, 15-11-1990",
        "56s_d%&fAB, Max, Walker, 32|Highland Street|Melb2ourne|Victoria|Australia, 15-11-1990",
        "56s_d%&fAB, Max, Walker, 32|Highland Street|Melbourne|Tasmania|Australia, 15-11-1990",
        "56s_d%&fAB, Max, Walker, 32|Highland Street|Melbourne|Victoria|France, 15-11-1990",
    })
    void testAddPerson_InvalidAddress(String id, String firstName, String lastName, String address, String birthdate) {
        Person person = new Person();
        assertFalse(person.addPerson(id, firstName, lastName, address, birthdate));

    }

    @ParameterizedTest
    @CsvSource({
        "56s_d%&fAB, Max, Walker, 32|Highland Street|Melbourne|Victoria|Australia, 15/11/1990",
        "78AA@@BBCC, Jane, Smith, 45|Main Avenue|Sydney|Victoria|Australia, 00-45-1985",
        "98ZZ##YYXX, John, Doe, 12|Park Road|Brisbane|Victoria|Australia, 31-December-2000"
    })
    void testAddPerson_InvalidDate(String id, String firstName, String lastName, String address, String birthdate) {
        Person person = new Person();
        assertFalse(person.addPerson(id, firstName, lastName, address, birthdate));

    }

    @ParameterizedTest
    @CsvSource({
        "11s_d%&fAB, Max, Walker, 1A|Highland St.|Melb|Vic|Aus, 00/11/1990",
        "78AA@@BBCCZ, Jane, Smith, A45|Main' Avenue|Sydney|VIC|Australia, 00-45-1985",
        "98ZZ, John, Doe, Six|Park Rd.|Brisbane|3078|Australia, 31-December-2000"
    })
    void testAddPerson_InvalidInputs(String id, String firstName, String lastName, String address, String birthdate) {
        Person person = new Person();
        assertFalse(person.addPerson(id, firstName, lastName, address, birthdate));


    }

}
