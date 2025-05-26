package assignment4;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.Period;
// import java.time.format.DateTimeFormatter;



public class InputValidator {
    
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
    
        // String [] addressArray = address.split("\\|");

        // if (addressArray.length != 5) {
        //     return false;
        // }

        // for (int i = 0; i < addressArray[0].length(); ++i) {
        //     char ch = addressArray[0].charAt(i);
        //     if (!Character.isDigit(ch)) {
        //         return false;
        //     }
        // }

        // for (int i = 0; i < addressArray[1].length(); ++i) {
        //     char ch = addressArray[1].charAt(i);
        //     if (Character.isDigit(ch)) {
        //         return false;
        //     }
        // }

        // for (int i = 0; i < addressArray[2].length(); ++i) {
        //     char ch = addressArray[2].charAt(i);
        //     if (Character.isDigit(ch)) {
        //         return false;
        //     }
        // }

        // if (!addressArray[3].equals("Victoria") || !addressArray[4].equals("Australia")) {
        //     return false;
        // }

        // return true;
    }

    // Simple function to assess if demerits are within bounds 1-6
    public boolean isValidPoints(int demerit) {
        if (demerit >= 1 && demerit <= 6) {
            return true;
        }
        else return false;
    }

    public boolean isUnder18(String birthday){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate birthdate = LocalDate.parse(birthday, formatter);

            LocalDate today = LocalDate.now();

            Period age = Period.between(birthdate, today);

            return age.getYears() < 18;
        } catch (Exception e) {
            return false;
        }
    }


}
