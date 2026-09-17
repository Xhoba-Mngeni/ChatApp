package chatapp;
import java.util.Scanner;

public class ChatApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Welcome to the Chat App ---");
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        // Initialize the Login object
        Login login = new Login(firstName, lastName);

        System.out.println("\n--- Registration ---");
        System.out.print("Create a username (must contain '_' and <= 5 chars): ");
        String username = scanner.nextLine();

        System.out.print("Create a password (min 8 chars, 1 cap, 1 num, 1 special): ");
        String password = scanner.nextLine();

        // Process Registration
        System.out.println("\n--- Registration Status ---");
        String regStatus = login.registerUser(username, password);
        System.out.println(regStatus);

        // Only proceed to login if registration was successful
        if (regStatus.contains("successfully")) {
            System.out.println("\n--- System Login ---");
            System.out.print("Enter username to login: ");
            String loginUser = scanner.nextLine();

            System.out.print("Enter password to login: ");
            String loginPass = scanner.nextLine();

            boolean isSuccess = login.loginUser(loginUser, loginPass);
            System.out.println("\n" + login.returnLoginStatus(isSuccess));
        } else {
            System.out.println("\nRegistration failed. Please run the application again.");
        }
        
        scanner.close();
    }
}