package assignment4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestAddPerson {
    Person person = new Person();
    @Test

    public void addPersonTest() {
        boolean result = person.addPerson();
        assertTrue(result, "addPerson should return true for valid inputs");
    }
    
}
