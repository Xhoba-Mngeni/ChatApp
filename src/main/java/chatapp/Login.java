package chatapp;

public class Login {
    private String registeredUsername;
    private String registeredPassword;
    private String firstName;
    private String lastName;

    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasCapital = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = !password.matches("[A-Za-z0-9]*"); 
        return hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        return phoneNumber.matches("^\\+\\d{1,3}\\d{1,10}$");
    }

    public String registerUser(String username, String password) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        this.registeredUsername = username;
        this.registeredPassword = password;
        return "The two above conditions have been met, and the user has been registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        if (username == null || password == null) return false;
        return username.equals(this.registeredUsername) && password.equals(this.registeredPassword);
    }

    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + this.firstName + ", " + this.lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}