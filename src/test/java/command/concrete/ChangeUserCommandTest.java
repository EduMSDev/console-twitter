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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChangeUserCommandTest extends TwitterTestBase {
    private static final TwitterReceiver twitterReceiver = new TwitterReceiver();

    @BeforeAll
    public static void init() throws Exception {
        doLogin(twitterReceiver);
    }

    @Test
    @Order(1)
    void onlyExistUniqueUserTest() throws Exception {
        String errorPrinted = tapSystemErr(() -> new ChangeUserCommand(twitterReceiver).execute());

        assertEquals("There is only one user, more than one user is needed for the change.\n", errorPrinted);
    }

    @Test
    @Order(2)
    void userNotExistTest() throws Exception {
        withTextFromSystemIn("Juan").execute(() -> new CreateUserCommand(twitterReceiver).execute());
        withTextFromSystemIn("Pedro").execute(() -> new CreateUserCommand(twitterReceiver).execute());

        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Alberto").execute(() -> errorPrinted.set(tapSystemErr(() -> new ChangeUserCommand(twitterReceiver).execute())));

        assertEquals("User Alberto not found\n", errorPrinted.get());
    }

    @Test
    void canChangeToUserTest() throws Exception {
        AtomicReference<String> messagePrinted = new AtomicReference<>();
        withTextFromSystemIn("Juan").execute(() -> messagePrinted.set(tapSystemOut(() -> new ChangeUserCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = super.getLastMessageFromConsole(messagePrinted);
        assertEquals("Welcome Juan", lastMessageFromConsole);
    }

}