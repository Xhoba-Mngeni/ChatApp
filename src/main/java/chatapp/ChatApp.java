package chatapp;

import java.util.Scanner;

public class ChatApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(" Welcome to the Chat App ");
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        Login login = new Login(firstName, lastName);

        System.out.println("\n Registration ");
        System.out.print("Create a username (must contain '_' and <= 5 chars): ");
        String username = scanner.nextLine();

        System.out.print("Create a password (min 8 chars, 1 cap, 1 num, 1 special): ");
        String password = scanner.nextLine();

        System.out.print("Enter your South African cell phone number (e.g., +27838968976): ");
        String cellPhone = scanner.nextLine();

        if (login.checkCellPhoneNumber(cellPhone)) {
            System.out.println("Cell phone number successfully added.");
        } else {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
        }

        String regStatus = login.registerUser(username, password);
        System.out.println(regStatus);

        if (!regStatus.contains("successfully")) {
            System.out.println("Registration failed. Exiting application.");
            scanner.close();
            return;
        }

       
        System.out.println("\n System Login ");
        System.out.print("Enter username to login: ");
        String loginUser = scanner.nextLine();

        System.out.print("Enter password to login: ");
        String loginPass = scanner.nextLine();

        boolean isSuccess = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(isSuccess));

  
        if (!isSuccess) {
            scanner.close();
            return;
        }

        
        System.out.println("\nWelcome to QuickChat.");


        System.out.print("How many messages do you wish to enter? ");
        int maxMessages = 0;
        while (true) {
            try {
                maxMessages = Integer.parseInt(scanner.nextLine().trim());
                if (maxMessages > 0) break;
                System.out.print("Please enter a positive number: ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a numeric value: ");
            }
        }

        int messagesEntered = 0;
        boolean running = true;

  
        while (running) {
            System.out.println("\n QuickChat Menu ");
            System.out.println("Option 1) Send Messages");
            System.out.println("Option 2) Show recently sent messages");
            System.out.println("Option 3) Quit");
            System.out.print("Select an option (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (messagesEntered >= maxMessages) {
                        System.out.println("You have already reached your limit of " + maxMessages + " messages.");
                        break;
                    }

                    while (messagesEntered < maxMessages) {
                        System.out.println("\n Entering Message " + (messagesEntered + 1) + " of " + maxMessages + " ---");
                        String generatedID = Message.generateRandomMessageID();

                       
                        String recipient;
                        Message tempValidator = new Message();
                        while (true) {
                            System.out.print("Enter recipient cell number (with international code, e.g., +27718693002): ");
                            recipient = scanner.nextLine().trim();
                            String cellCheck = tempValidator.checkRecipientCell(recipient);
                            System.out.println(cellCheck);
                            if (cellCheck.equals("Cell phone number successfully captured.")) {
                                break;
                            }
                        }

                        
                        String msgText;
                        while (true) {
                            System.out.print("Enter your message (max 250 characters): ");
                            msgText = scanner.nextLine();
                            if (msgText.length() > 250) {
                                System.out.println("Please enter a message of less than 250 characters.");
                                System.out.println(tempValidator.checkMessageLength(msgText));
                            } else {
                                System.out.println("Message ready to send.");
                                break;
                            }
                        }

                  
                        Message currentMessage = new Message(generatedID, messagesEntered, recipient, msgText);
                        System.out.println(currentMessage.returnMessageIDStatus());

      
                        System.out.println("\nChoose an action for this message:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message to send later");
                        System.out.print("Enter choice (1-3): ");

                        int actionChoice = 1;
                        try {
                            actionChoice = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            actionChoice = 1;
                        }

                        String actionResult = currentMessage.SentMessage(actionChoice);
                        System.out.println(actionResult);

                 
                        if (actionChoice == 1) {
                            System.out.println("Message sent");
                            System.out.println("\n Sent Message Details ");
                            System.out.println(currentMessage.formatMessageDetails());
                        }

                        messagesEntered++;
                    }

                    // 8. Display total number of messages sent once all messages have been processed
                    Message summaryMsg = new Message();
                    System.out.println("\nTotal number of messages sent: " + summaryMsg.returnTotalMessagess());
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("Exiting QuickChat. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }

        scanner.close();
    }
}