package command.concrete;

import base.TwitterTestBase;
import command.TwitterReceiver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.atomic.AtomicReference;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUserCommandTest extends TwitterTestBase {

    @Test
    void checkAddUserTest() throws Exception {
        AtomicReference<String> messagePrinted = new AtomicReference<>();
        withTextFromSystemIn("Pedro").execute(() -> messagePrinted.set(tapSystemOut(() -> new CreateUserCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(messagePrinted.get());
        assertEquals("Edu has added user Pedro to EduSocial", lastMessageFromConsole);
    }

    @Test
    void userCantAddedBecauseExistTest() throws Exception {
        AtomicReference<String> messagePrinted = new AtomicReference<>();
        withTextFromSystemIn("Edu").execute(() -> messagePrinted.set(tapSystemErr(() -> new CreateUserCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(messagePrinted.get());
        assertEquals("The user with the name Edu already exists", lastMessageFromConsole);
    }
}