package command.concrete;

import base.TwitterTestBase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UnfollowCommandTest extends TwitterTestBase {

    @Test
    void userIsNotYourFriendTest() throws Exception {
        withTextFromSystemIn("Lucas").execute(() -> new CreateUserCommand(twitterReceiver).execute());

        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Lucas").execute(() -> errorPrinted.set(tapSystemErr(() -> new UnfollowCommand(twitterReceiver).execute())));

        String getLastMessageFromConsole = getLastMessageFromConsole(errorPrinted.get());
        assertEquals("Lucas is not in your friendslist!", getLastMessageFromConsole);
    }

    @Test
    void unfollowFriendTest() throws Exception {
        withTextFromSystemIn("Judas").execute(() -> new CreateUserCommand(twitterReceiver).execute());
        withTextFromSystemIn("Judas").execute(() -> new FollowCommand(twitterReceiver).execute());

        AtomicReference<String> messagePrinted = new AtomicReference<>();
        withTextFromSystemIn("Judas").execute(() -> messagePrinted.set(tapSystemOut(() -> new UnfollowCommand(twitterReceiver).execute())));

        String getLastMessageFromConsole = getLastMessageFromConsole(messagePrinted.get());
        assertEquals("Edu has unfollowed Judas", getLastMessageFromConsole);
    }

    @Test
    void userNotExistTest() throws Exception {
        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Manuel").execute(() -> errorPrinted.set(tapSystemErr(() -> new UnfollowCommand(twitterReceiver).execute())));

        String getLastMessageFromConsole = getLastMessageFromConsole(errorPrinted.get());
        assertEquals("User Manuel not found", getLastMessageFromConsole);
    }

}