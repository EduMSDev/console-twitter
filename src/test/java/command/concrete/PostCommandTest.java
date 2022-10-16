package command.concrete;

import base.TwitterTestBase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PostCommandTest extends TwitterTestBase {

    @Test
    void checkIfTweetIsPostedTest() throws Exception {
        String messageToPost = "Hello world! i am Edu";
        AtomicReference<String> messagePrinted = new AtomicReference<>();
        withTextFromSystemIn(messageToPost).execute(() -> messagePrinted.set(tapSystemOut(() -> new PostCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(messagePrinted.get());
        assertEquals(lastMessageFromConsole, "Edu - " + messageToPost);
    }
}