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
class FollowCommandTest extends TwitterTestBase {

    private static final TwitterReceiver twitterReceiver = new TwitterReceiver();

    @BeforeAll
    static void setUp() throws Exception {
        doLogin(twitterReceiver);
    }

    @Test
    @Order(1)
    void userNotFoundTest() throws Exception {
        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Alberto").execute(() -> errorPrinted.set(tapSystemErr(() -> new FollowCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(errorPrinted);
        assertEquals("User Alberto not found", lastMessageFromConsole);
    }

    @Test
    @Order(2)
    void userAlreadyFollowedTest() throws Exception {
        withTextFromSystemIn("Juan").execute(() -> new CreateUserCommand(twitterReceiver).execute());

        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Juan").execute(() -> errorPrinted.set(tapSystemErr(() -> new FollowCommand(twitterReceiver).execute())));
        withTextFromSystemIn("Juan").execute(() -> errorPrinted.set(tapSystemErr(() -> new FollowCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(errorPrinted);
        assertEquals("Edu you already follow the user Juan", lastMessageFromConsole);
    }

    @Test
    void followedUserTest() throws Exception {
        withTextFromSystemIn("Alberto").execute(() -> new CreateUserCommand(twitterReceiver).execute());

        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Alberto").execute(() -> errorPrinted.set(tapSystemOut(() -> new FollowCommand(twitterReceiver).execute())));

        String lastMessageFromConsole = getLastMessageFromConsole(errorPrinted);
        assertEquals("Edu follows Alberto", lastMessageFromConsole);
    }
}