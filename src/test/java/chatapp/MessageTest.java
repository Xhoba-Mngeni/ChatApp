package chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        Message.resetCounters();
    }

    @Test
    public void testMessageLengthSuccess() {
        Message message = new Message();
        String msg = "Hi Mike, can you join us for dinner tonight?";
        assertEquals("Message ready to send.", message.checkMessageLength(msg));
    }

    @Test
    public void testMessageLengthFailure() {
        Message message = new Message();

        String longMsg = "A".repeat(260);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", message.checkMessageLength(longMsg));
    }

    @Test
    public void testRecipientNumberCorrectlyFormatted() {
        Message message = new Message();
        assertEquals("Cell phone number successfully captured.", message.checkRecipientCell("+27718693002"));
    }

    @Test
    public void testRecipientNumberIncorrectlyFormatted() {
        Message message = new Message();
        String expected = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        assertEquals(expected, message.checkRecipientCell("08575975889"));
    }

    @Test
    public void testMessageHashIsCorrect() {
      
        Message message = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("00:0:HITONIGHT", message.createMessageHash());
    }

    @Test
    public void testMessageHashesInLoop() {
        String[] ids = {"0012345678", "0098765432"};
        String[] recipients = {"+27718693002", "08575975889"};
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?"
        };
        String[] expectedHashes = {
            "00:0:HITONIGHT",
            "00:1:HIKEEGANPAYMENT".replace("KEEGAN", "") 
        };

        for (int i = 0; i < messages.length; i++) {
            Message msg = new Message(ids[i], i, recipients[i], messages[i]);
            assertEquals(expectedHashes[i], msg.createMessageHash());
        }
    }

    @Test
    public void testMessageIDIsCreated() {
        Message message = new Message();
        assertTrue(message.checkMessageID());
        assertEquals("Message ID generated: " + message.getMessageID(), message.returnMessageIDStatus());
    }

    @Test
    public void testMessageSentOptions() {
      
        Message msg1 = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully sent.", msg1.SentMessage(1));

  
        Message msg2 = new Message("0098765432", 1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Press 0 to delete the message.", msg2.SentMessage(2));

 
        Message msg3 = new Message("0055555555", 2, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message successfully stored.", msg3.SentMessage(3));

        assertEquals(1, msg1.returnTotalMessagess());
    }
}