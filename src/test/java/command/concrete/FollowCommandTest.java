package command.concrete;

import base.TwitterTestBase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FollowCommandTest extends TwitterTestBase {

    @Test
    void userNotFoundTest() throws Exception {
        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Alberto").execute(() -> errorPrinted.set(tapSystemErr(() -> new FollowCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(errorPrinted.get());
        assertEquals("User Alberto not found", lastMessageFromConsole);
    }

    @Test
    void userAlreadyFollowedTest() throws Exception {
        withTextFromSystemIn("Juan").execute(() -> new CreateUserCommand(twitterReceiver).execute());

        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Juan").execute(() -> errorPrinted.set(tapSystemErr(() -> new FollowCommand(twitterReceiver).execute())));
        withTextFromSystemIn("Juan").execute(() -> errorPrinted.set(tapSystemErr(() -> new FollowCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(errorPrinted.get());
        assertEquals("Edu you already follow the user Juan", lastMessageFromConsole);
    }

    @Test
    void followedUserTest() throws Exception {
        withTextFromSystemIn("Alberto").execute(() -> new CreateUserCommand(twitterReceiver).execute());

        AtomicReference<String> messagePrinted = new AtomicReference<>();
        withTextFromSystemIn("Alberto").execute(() -> messagePrinted.set(tapSystemOut(() -> new FollowCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(messagePrinted.get());
        assertEquals("Edu follows Alberto", lastMessageFromConsole);
    }
}