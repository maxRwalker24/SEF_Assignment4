package assignment4;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.Period;




public class InputValidator {
    
    // Checks to see if date is formatted correctly
    public boolean isValidDate(String input) {
        try {
            LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public boolean isValidID(String input) {
        if (input.length() != 10) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[2-9]{2}.{6}[A-Z]{2}$");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {  // Use matches() to match entire string
            return false;
        }

        int count = 0;
        for (int i = 2; i < 8; i++) {
            char ch = input.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                count++;
            }
        }

        // At least 2 special chars in the middle 6 characters
        if (count < 2) {
            return false;
        }

        return true;
    }


    public boolean isValidAddress(String address) {
        String regex = "^\\d+\\|[A-Za-z ]+\\|[A-Za-z ]+\\|Victoria\\|Australia$";
        return address.matches(regex);
    
    }

    // Simple function to assess if demerits are within bounds 1-6
    public boolean isValidPoints(int demerit) {
        if (demerit >= 1 && demerit <= 6) {
            return true;
        }
        else return false;
    }
    // Function to check for under 18
    public boolean isUnder18(String birthday){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate birthdate = LocalDate.parse(birthday, formatter);

            LocalDate today = LocalDate.now();

            // Compare the date of birth to now to get user age
            Period age = Period.between(birthdate, today);

            return age.getYears() < 18;
        } catch (Exception e) {
            return false;
        }
    }


}
