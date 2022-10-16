package command.concrete;

import base.TwitterTestBase;
import command.TwitterReceiver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginCommandTest extends TwitterTestBase {

    private TwitterReceiver twitterReceiver;

    @BeforeEach
    protected void initTest() {
        twitterReceiver = new TwitterReceiver();
    }

    @Test
    void doLoginTest() throws Exception {
        AtomicReference<String> messagePrinted = new AtomicReference<>();
        withTextFromSystemIn("Edu").execute(() -> messagePrinted.set(tapSystemOut(() -> new LoginCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(messagePrinted.get());
        assertEquals("Edu writes to the world what you want to say", lastMessageFromConsole);
    }
}