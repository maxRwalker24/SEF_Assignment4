package assignment4;

import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import java.io.PrintWriter;
// import java.io.FileOutputStream;
// import java.io.IOException;

public class Person {

    private String personID;
    private String firstName;
    private String lastName;
    private String address;
    private String birthdate;
    private HashMap<Date, Integer> demeritPoints; // A variable that holds the demerit points with the offense day
    private boolean isSuspended;

    // public Person(String personID, String firstName, String lastName, String address, String birthdate) {
    //     this.personID = personID;
    //     this.firstName = firstName;
    //     this.lastName = lastName;
    //     this.address = address;
    //     this.birthdate = birthdate;
    //     this.demeritPoints = new HashMap<>(); 
    //     this.isSuspended = false; // default value
    // }

    // Max to implement
    public boolean addPerson (String personID, String firstName, String lastName, String address, String birthdate) {

        InputValidator iv = new InputValidator();

        if (iv.isValidDate(birthdate) && iv.isValidID(personID) && iv.idValidAddress(address)) {

            Person person = new Person();
            person.personID = personID;
            person.firstName = firstName;
            person.lastName = lastName;
            person.address = address;
            person.birthdate = birthdate;

            try {
                FileOutputStream fileStream = new FileOutputStream("Person.txt", true);
                PrintWriter outFS = new PrintWriter(fileStream);
                outFS.println(personID + " " + firstName + " " + lastName + " " + address + " " + birthdate);
                outFS.close();
            } catch (FileNotFoundException e) {
                System.out.println("File could not be created or opened: " + e.getMessage());
            }

            return true;
        }
        
        return false;
    
    }

    
    public boolean updatePersonalDetails() {
    //TODO: This method allows updating a given person's ID, firstName, lastName, address and birthday in a TXT file.
    //Changing personal details will not affect their demerit points or the suspension status.
    // All relevant conditions discussed for the addPerson function also need to be considered and checked in the updaterson function.
    //Condition 1: If a person is under 18, their address cannot be changed.
    //Condition 2: If a person's birthday is going to be changed, then no other personal detail (i.e, person's ID, firstName, lastName, address) can be changed.
    //Condition 3: If the first character/digit of a person's ID is an even number, then their ID cannot be changed.
    //Instruction: If the Person's updated information meets the above conditions and any other conditions you may want to consider,
    //the Person's information should be updated in the TXT file with the updated information, and the updatePersonalDetails function should return true.
    //Otherwise, the Person's updated information should not be updated in the TXT file, and the updatePersonalDetails function should return false.





    return true;
    }
    
    
    public String addDemeritPoints () {
    //TODO: This method adds demerit points for a given person in a TXT file.
    //Condition 1: The format of the date of the offense should follow the following format: DD-MM-YYYY. Example: 15-11-1990
    //Condition 2: The demerit points must be a whole number between 1-6
    //Condition 3: If the person is under 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 6.
    //If the person is over 21, the isSuspended variable should be set to true if the total demerit points within two years exceed 12.
    //Instruction: If the above conditions and any other conditions you may want to consider are met, the demerit points for a person should be inserted into the TXT file,
    //and the addDemeritPoints function should return "Sucess". Otherwise, the addDemeritPoints function should return "Failed"





    return "Sucess";
    }

    // Getters
    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public HashMap<Date, Integer> getDemeritPoints() {
        return demeritPoints;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    // Setters
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setDemeritPoints(HashMap<Date, Integer> demeritPoints) {
        this.demeritPoints = demeritPoints;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }



}