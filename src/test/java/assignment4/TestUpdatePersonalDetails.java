package assignment4;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestUpdatePersonalDetails {
    public void createYounger18() throws Exception {
        Person younger18 = new Person();
        younger18.setPersonID("36s_d#@fAJ");
        younger18.setFirstName("Ajay");
        younger18.setLastName("Peeris");
        younger18.setAddress("26|YeahNah Road|Melbourne|Victoria|Australia");
        younger18.setBirthdate("26-02-2014");
        }

    public void createOlder18() throws Exception {
        Person older18 = new Person();
        older18.setPersonID("25s_d%&fAB");
        older18.setFirstName("Paul");
        older18.setLastName("Vennat");
        older18.setAddress("17|Landscape Drive|Melbourne|Victoria|Australia");
        older18.setBirthdate("10-09-1999");
        }
    
    @BeforeAll
    public static void clearValidOutput() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("Demerit.txt", false))) {
            writer.print("");
        }
    }

    





}
