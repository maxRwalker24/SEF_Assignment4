package assignment4;

import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestAddPerson {

    @ParameterizedTest
    @CsvSource({
        "56s_d%&fAB, Max, Walker, 32|Highland Street|Melbourne|Victoria|Australia, 15-11-1990",
        "78AA@@BBCC, Jane, Smith, 45|Main Avenue|Sydney|Victoria|Australia, 01-01-1985",
        "98ZZ##YYXX, John, Doe, 12|Park Road|Brisbane|Victoria|Australia, 31-12-2000"
    })
    void testAddPerson_ValidInputs(String id, String firstName, String lastName, String address, String birthdate) {
        Person person = new Person();
        assertTrue(person.addPerson(id, firstName, lastName, address, birthdate));
    }
    
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
