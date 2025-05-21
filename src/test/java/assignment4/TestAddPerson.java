package assignment4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestAddPerson {
    Person person = new Person();
    
    @Test

    public void testAddPerson_ValidData() {
        String personID = "56s_d%&fAB";
        String firstName = "Max";
        String lastName = "Walker";
        String address = "32|Highland Street|Melbourne|Victoria|Australia";
        String birthdate = "15-11-1990";

        boolean result = person.addPerson(personID, firstName, lastName, address, birthdate);
        assertTrue(result, "addPerson should return true for valid inputs");
    }
    
}
