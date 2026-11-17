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

        String fullName = firstName + " " + lastName;
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
                if (maxMessages > 0) {
                    break;
                }
                System.out.print("Please enter a positive number: ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a numeric value: ");
            }
        }

        int messagesEntered = 0;
        boolean running = true;

        while (running) {
            System.out.println("\n QuickChat Main Menu");
            System.out.println("Option 1) Send Messages");
            System.out.println("Option 2) Show recently sent messages");
            System.out.println("Option 3) Quit");
            System.out.println("Option 4) Stored Messages & Task Report");
            System.out.print("Select an option (1-4): ");

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
                            System.out.print("Enter recipient cell number (e.g., +27834557896): ");
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

                        Message currentMessage = new Message(generatedID, messagesEntered, recipient, msgText, fullName);
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

                    Message summaryMsg = new Message();
                    System.out.println("\nTotal number of messages sent: " + summaryMsg.returnTotalMessagess());
                    break;

                case "2":
                    Message printer = new Message();
                    System.out.println("\n Recently Sent Messages ");
                    System.out.println(printer.printMessages());
                    break;

                case "3":
                    System.out.println("Exiting QuickChat. Goodbye!");
                    running = false;
                    break;

                case "4":
                    handleStoredMessagesMenu(scanner);
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
            }
        }

        scanner.close();
    }

    private static void handleStoredMessagesMenu(Scanner scanner) {
        boolean subMenuRunning = true;
        while (subMenuRunning) {
            System.out.println("\n Option 4: Stored Messages & Report Menu ");
            System.out.println("a) Display the sender and recipient of all stored/sent messages");
            System.out.println("b) Display the longest message");
            System.out.println("c) Search for a message ID and display recipient and message");
            System.out.println("d) Search for all messages sent or stored for a particular recipient");
            System.out.println("e) Delete a message using the message hash");
            System.out.println("f) Display a report listing the full details of all messages");
            System.out.println("g) Read stored messages from JSON file into array");
            System.out.println("x) Return to Main Menu");
            System.out.print("Select an option (a-g or x): ");

            String subChoice = scanner.nextLine().trim().toLowerCase();

            switch (subChoice) {
                case "a":
                    System.out.println("\n Senders and Recipients ");
                    System.out.println(Message.displaySenderAndRecipient());
                    break;
                case "b":
                    System.out.println("\n Longest Message ");
                    System.out.println(Message.getLongestMessage());
                    break;
                case "c":
                    System.out.print("Enter Message ID to search: ");
                    String searchID = scanner.nextLine().trim();
                    System.out.println(Message.searchByMessageIDWithDetails(searchID));
                    break;
                case "d":
                    System.out.print("Enter Recipient cell number to search (e.g., +27838884567): ");
                    String searchRec = scanner.nextLine().trim();
                    System.out.println(Message.searchMessagesByRecipient(searchRec));
                    break;
                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    String deleteHash = scanner.nextLine().trim();
                    System.out.println(Message.deleteMessageByHash(deleteHash));
                    break;
                case "f":
                    System.out.println("\n Full Message Report ");
                    System.out.println(Message.displayReport());
                    break;
                case "g":
                    String[] loaded = Message.readStoredMessagesFromJSON();
                    System.out.println("Loaded " + loaded.length + " stored message(s) from messages.json:");
                    for (String msg : loaded) {
                        System.out.println("- " + msg);
                    }
                    break;
                case "x":
                    subMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a-g or x.");
            }
        }
    }
}