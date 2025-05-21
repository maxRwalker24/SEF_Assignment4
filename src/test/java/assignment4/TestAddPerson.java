package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

//import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestAddPerson {

    private static final Path OUTPUT_PATH = Paths.get("Person.txt");
    
    @BeforeEach
    public void clearFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Person.txt", false))) {
            writer.print("");
        }
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
        List<String> lines = Files.readAllLines(OUTPUT_PATH);
        String lastLine = lines.get(lines.size() - 1);

        // Build the expected line format (adjust if your method formats differently)
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


        // Assert: file is still empty
        File file = new File("Person.txt");
        assertEquals(0, file.length(), "File should be empty after invalid input");
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

        // Assert: file is still empty
        File file = new File("Person.txt");
        assertEquals(0, file.length(), "File should be empty after invalid input");
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

        // Assert: file is still empty
        File file = new File("Person.txt");
        assertEquals(0, file.length(), "File should be empty after invalid input");
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

        // Assert: file is still empty
        File file = new File("Person.txt");
        assertEquals(0, file.length(), "File should be empty after invalid input");
    }

    // private void assertFileIsEmpty(String fileName) {
    // File file = new File(fileName);
    // if (file.exists()) {
    //     assertEquals(0, file.length(), "Expected file to be empty, but it wasn't.");
    // } else {
    //     // If the file doesn't exist, it's considered empty
    //     assertTrue(true);
    // }

    
    // @Test

    // public void testAddPerson_ValidData() {
    //     String personID = "56s_d%&fAB";
    //     String firstName = "Max";
    //     String lastName = "Walker";
    //     String address = "32|Highland Street|Melbourne|Victoria|Australia";
    //     String birthdate = "15-11-1990";

    //     boolean result = person.addPerson(personID, firstName, lastName, address, birthdate);
    //     assertTrue(result, "addPerson should return true for valid inputs");
    // }
    
}
