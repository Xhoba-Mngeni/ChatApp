package chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;


    private static final List<String> sentMessagesList = new ArrayList<>();
    private static int totalMessagesSent = 0;

    public Message() {
        this.messageID = generateRandomMessageID();
    }

    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }

    public static String generateRandomMessageID() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public String returnMessageIDStatus() {
        return "Message ID generated: " + this.messageID;
    }

    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    public boolean checkMessageID() {
        return checkMessageID(this.messageID);
    }

    public String checkRecipientCell(String cellNumber) {
        if (cellNumber != null && cellNumber.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkRecipientCell() {
        return checkRecipientCell(this.recipient);
    }

    public String checkMessageLength(String message) {
        if (message == null) {
            return "Please enter a message of less than 250 characters.";
        }
        if (message.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public String createMessageHash(String id, int msgNum, String msgText) {
        if (id == null || id.length() < 2 || msgText == null || msgText.trim().isEmpty()) {
            return "";
        }
        String firstTwoDigits = id.substring(0, 2);
        String cleanText = msgText.trim().replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = cleanText.split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;

        this.messageHash = (firstTwoDigits + ":" + msgNum + ":" + firstWord + lastWord).toUpperCase();
        return this.messageHash;
    }

    public String createMessageHash() {
        return createMessageHash(this.messageID, this.messageNumber, this.messageText);
    }

    public String SentMessage(int option) {
        switch (option) {
            case 1:
                totalMessagesSent++;
                String details = formatMessageDetails();
                sentMessagesList.add(details);
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public String SentMessage() {
        return SentMessage(1);
    }

    public String formatMessageDetails() {
        return "Message ID: " + this.messageID + "\n"
             + "Message Hash: " + this.messageHash + "\n"
             + "Recipient: " + this.recipient + "\n"
             + "Message: " + this.messageText;
    }

    public String printMessages() {
        if (sentMessagesList.isEmpty()) {
            return "No messages have been sent.";
        }
        return String.join("\n\n", sentMessagesList);
    }

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public static void resetCounters() {
        sentMessagesList.clear();
        totalMessagesSent = 0;
    }

    public void storeMessage() {
        String jsonEntry = "{\n"
                + "  \"messageID\": \"" + escapeJson(this.messageID) + "\",\n"
                + "  \"messageHash\": \"" + escapeJson(this.messageHash) + "\",\n"
                + "  \"recipient\": \"" + escapeJson(this.recipient) + "\",\n"
                + "  \"message\": \"" + escapeJson(this.messageText) + "\"\n"
                + "}\n";

        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(jsonEntry);
        } catch (IOException e) {
            System.out.println("Error storing message to JSON file: " + e.getMessage());
        }
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\").replace("\"", "\\\"");
    }


    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}