package command.concrete;

import base.TwitterTestBase;
import org.junit.jupiter.api.Test;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReadCommandTest extends TwitterTestBase {


    @Test
    void nothingToShowTest() throws Exception {
        String messagePrinted = tapSystemErr(() -> new ReadCommand(twitterReceiver).execute());

        String lastMessageFromConsole = getLastMessageFromConsole(messagePrinted);
        assertEquals("Edu you have not published anything!", lastMessageFromConsole);
    }

    @Test
    void showingTweetsTest() throws Exception {
        String messageToPost = "Hello there! I am Edu";
        withTextFromSystemIn(messageToPost).execute(() -> tapSystemOut(() -> new PostCommand(twitterReceiver).execute()));

        String messagePrinted = tapSystemOut(() -> new ReadCommand(twitterReceiver).execute());

        String lastMessageFromConsole = getLastMessageFromConsole(messagePrinted);
        assertTrue(lastMessageFromConsole.contains(messageToPost));
    }
}