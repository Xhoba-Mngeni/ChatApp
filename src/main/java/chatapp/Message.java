package chatapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    private String sender;

    private static final List<String> sentMessagesList = new ArrayList<>();
    private static int totalMessagesSent = 0;

    public static String[] sentMessages = new String[0];
    public static String[] disregardedMessages = new String[0];
    public static String[] storedMessages = new String[0];
    public static String[] messageHashes = new String[0];
    public static String[] messageIDs = new String[0];

    public static String[] allMessages = new String[0];
    public static String[] allRecipients = new String[0];
    public static String[] allSenders = new String[0];
    public static String[] allFlags = new String[0];

    public Message() {
        this.messageID = generateRandomMessageID();
        this.sender = "System User";
    }

    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this(messageID, messageNumber, recipient, messageText, "System User");
    }

    public Message(String messageID, int messageNumber, String recipient, String messageText, String sender) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.sender = sender;
        this.messageHash = createMessageHash();
    }

    private static String[] appendToArray(String[] array, String value) {
        String[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = value;
        return newArray;
    }

    private static String[] removeFromArray(String[] array, int index) {
        if (index < 0 || index >= array.length) {
            return array;
        }
        String[] newArray = new String[array.length - 1];
        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index) {
                continue;
            }
            newArray[k++] = array[i];
        }
        return newArray;
    }

    private static String[] removeValueFromArray(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(value)) {
                return removeFromArray(array, i);
            }
        }
        return array;
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
        if (this.messageHash == null || this.messageHash.isEmpty()) {
            this.messageHash = createMessageHash();
        }

        switch (option) {
            case 1:
                totalMessagesSent++;
                String details = formatMessageDetails();
                sentMessagesList.add(details);

                sentMessages = appendToArray(sentMessages, this.messageText);
                recordCommonArrays("Sent");
                return "Message successfully sent.";
            case 2:
                disregardedMessages = appendToArray(disregardedMessages, this.messageText);
                recordCommonArrays("Disregard");
                return "Press 0 to delete the message.";
            case 3:
                storedMessages = appendToArray(storedMessages, this.messageText);
                recordCommonArrays("Stored");
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option selected.";
        }
    }

    public String SentMessage() {
        return SentMessage(1);
    }

    private void recordCommonArrays(String flag) {
        messageIDs = appendToArray(messageIDs, this.messageID);
        messageHashes = appendToArray(messageHashes, this.messageHash);
        allMessages = appendToArray(allMessages, this.messageText);
        allRecipients = appendToArray(allRecipients, this.recipient);
        allSenders = appendToArray(allSenders, this.sender != null ? this.sender : "System User");
        allFlags = appendToArray(allFlags, flag);
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
        sentMessages = new String[0];
        disregardedMessages = new String[0];
        storedMessages = new String[0];
        messageHashes = new String[0];
        messageIDs = new String[0];
        allMessages = new String[0];
        allRecipients = new String[0];
        allSenders = new String[0];
        allFlags = new String[0];
    }


    public void storeMessage() {
        String jsonEntry = "{\n"
                + "  \"messageID\": \"" + escapeJson(this.messageID) + "\",\n"
                + "  \"messageHash\": \"" + escapeJson(this.messageHash) + "\",\n"
                + "  \"sender\": \"" + escapeJson(this.sender) + "\",\n"
                + "  \"recipient\": \"" + escapeJson(this.recipient) + "\",\n"
                + "  \"message\": \"" + escapeJson(this.messageText) + "\"\n"
                + "}\n";

        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(jsonEntry);
        } catch (IOException e) {
            System.out.println("Error storing message to JSON file: " + e.getMessage());
        }
    }


    public static String[] readStoredMessagesFromJSON() {
        File file = new File("messages.json");
        if (!file.exists()) {
            return storedMessages;
        }
        List<String> loaded = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"message\":")) {
                    int firstQuote = line.indexOf('"', 10);
                    int lastQuote = line.lastIndexOf('"');
                    if (firstQuote != -1 && lastQuote > firstQuote) {
                        String extracted = line.substring(firstQuote + 1, lastQuote)
                                .replace("\\\"", "\"")
                                .replace("\\\\", "\\");
                        loaded.add(extracted);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
        if (!loaded.isEmpty()) {
            storedMessages = loaded.toArray(new String[0]);
        }
        return storedMessages;
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\").replace("\"", "\\\"");
    }


    public static String displaySenderAndRecipient() {
        if (allMessages.length == 0) {
            return "No messages available.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allMessages.length; i++) {
            if ("Sent".equalsIgnoreCase(allFlags[i]) || "Stored".equalsIgnoreCase(allFlags[i])) {
                if (sb.length() > 0) sb.append("\n");
                sb.append("Sender: ").append(allSenders[i])
                  .append(" | Recipient: ").append(allRecipients[i]);
            }
        }
        return sb.length() > 0 ? sb.toString() : "No sent or stored messages available.";
    }

    public static String getLongestMessage() {
        String longest = "";
        for (String msg : allMessages) {
            if (msg != null && msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest;
    }

    public static String searchByMessageID(String searchID) {
        if (searchID == null) return "Message ID not found.";
        for (int i = 0; i < messageIDs.length; i++) {
            if (searchID.equals(messageIDs[i]) || searchID.equals(allRecipients[i])) {
                return allMessages[i];
            }
        }
        return "Message ID not found.";
    }

    public static String searchByMessageIDWithDetails(String searchID) {
        if (searchID == null) return "Message ID not found.";
        for (int i = 0; i < messageIDs.length; i++) {
            if (searchID.equals(messageIDs[i]) || searchID.equals(allRecipients[i])) {
                return "Recipient: " + allRecipients[i] + "\nMessage: " + allMessages[i];
            }
        }
        return "Message ID not found.";
    }

    public static String searchMessagesByRecipient(String targetRecipient) {
        if (targetRecipient == null) return "No messages found for recipient.";
        List<String> matches = new ArrayList<>();
        for (int i = 0; i < allRecipients.length; i++) {
            if (targetRecipient.equals(allRecipients[i])
                    && ("Sent".equalsIgnoreCase(allFlags[i]) || "Stored".equalsIgnoreCase(allFlags[i]))) {
                matches.add(allMessages[i]);
            }
        }
        if (matches.isEmpty()) {
            return "No messages found for recipient: " + targetRecipient;
        }
        return String.join("\n", matches);
    }

    public static String deleteMessageByHash(String hash) {
        if (hash == null) return "Message hash not found.";
        for (int i = 0; i < messageHashes.length; i++) {
            if (hash.equalsIgnoreCase(messageHashes[i])) {
                String deletedText = allMessages[i];

                sentMessages = removeValueFromArray(sentMessages, deletedText);
                storedMessages = removeValueFromArray(storedMessages, deletedText);
                disregardedMessages = removeValueFromArray(disregardedMessages, deletedText);

                messageIDs = removeFromArray(messageIDs, i);
                messageHashes = removeFromArray(messageHashes, i);
                allMessages = removeFromArray(allMessages, i);
                allRecipients = removeFromArray(allRecipients, i);
                allSenders = removeFromArray(allSenders, i);
                allFlags = removeFromArray(allFlags, i);

                String formattedText = deletedText.endsWith(".")
                        ? deletedText.substring(0, deletedText.length() - 1)
                        : deletedText;

                return "Message: \"" + formattedText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    public static String displayReport() {
        if (allMessages.length == 0) {
            return "No messages to report.";
        }
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < allMessages.length; i++) {
            if ("Sent".equalsIgnoreCase(allFlags[i]) || "Stored".equalsIgnoreCase(allFlags[i])) {
                if (report.length() > 0) {
                    report.append("\n\n");
                }
                report.append("Message ID: ").append(messageIDs[i]).append("\n")
                      .append("Message Hash: ").append(messageHashes[i]).append("\n")
                      .append("Recipient: ").append(allRecipients[i]).append("\n")
                      .append("Message: ").append(allMessages[i]);
            }
        }
        return report.length() > 0 ? report.toString() : "No messages to report.";
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
}