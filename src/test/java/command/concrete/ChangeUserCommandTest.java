package command.concrete;

import base.TwitterTestBase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeUserCommandTest extends TwitterTestBase {

    @Test
    void onlyExistUniqueUserTest() throws Exception {
        String errorPrinted = tapSystemErr(() -> new ChangeUserCommand(twitterReceiver).execute());

        assertEquals("There is only one user, more than one user is needed for the change.\n", errorPrinted);
    }

    @Test
    void userNotExistTest() throws Exception {
        withTextFromSystemIn("Juan").execute(() -> new CreateUserCommand(twitterReceiver).execute());

        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Alberto").execute(() -> errorPrinted.set(tapSystemErr(() -> new ChangeUserCommand(twitterReceiver).execute())));

        assertEquals("User Alberto not found\n", errorPrinted.get());
    }

    @Test
    void canChangeToUserTest() throws Exception {
        withTextFromSystemIn("Lucas").execute(() -> new CreateUserCommand(twitterReceiver).execute());
        withTextFromSystemIn("Alberto").execute(() -> new CreateUserCommand(twitterReceiver).execute());
        AtomicReference<String> messagePrinted = new AtomicReference<>();
        withTextFromSystemIn("Alberto").execute(() -> messagePrinted.set(tapSystemOut(() -> new ChangeUserCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = super.getLastMessageFromConsole(messagePrinted.get());
        assertEquals("Welcome Alberto", lastMessageFromConsole);
    }
}