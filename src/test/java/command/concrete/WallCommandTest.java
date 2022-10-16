package command.concrete;

import base.TwitterTestBase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WallCommandTest extends TwitterTestBase {


    @Test
    void notFriendFollowedTest() throws Exception {
        AtomicReference<String> errorPrinted = new AtomicReference<>();
        withTextFromSystemIn("Juan").execute(() -> errorPrinted.set(tapSystemErr(() -> new WallCommand(twitterReceiver).execute())));

        assertEquals("You don't follow anyone. If you want to see your own tweets, choose the read option\n",
                errorPrinted.get());
    }

    @Test
    void wallTest() throws Exception {
        String messageToPost = "Hello!";
        withTextFromSystemIn("Juan").execute(() -> new CreateUserCommand(twitterReceiver).execute());
        withTextFromSystemIn("Juan").execute(() -> new FollowCommand(twitterReceiver).execute());
        withTextFromSystemIn("Juan").execute(() -> new ChangeUserCommand(twitterReceiver).execute());
        withTextFromSystemIn(messageToPost).execute(() -> new PostCommand(twitterReceiver).execute());
        withTextFromSystemIn("Edu").execute(() -> new ChangeUserCommand(twitterReceiver).execute());

        AtomicReference<String> messagePrinted = new AtomicReference<>();
        withTextFromSystemIn("Juan").execute(() -> messagePrinted.set(tapSystemOut(() -> new WallCommand(twitterReceiver).execute())));

        boolean followedMessageIsPosted =
                getMessagesFromConsole(messagePrinted.get()).anyMatch(outputConsole -> outputConsole.equalsIgnoreCase("Edu follows Juan (0  seconds ago)"));
        assertTrue(followedMessageIsPosted);
        boolean tweetIsPosted =
                getMessagesFromConsole(messagePrinted.get()).anyMatch(outputConsole -> outputConsole.equalsIgnoreCase("Juan - " + messageToPost + " (0  seconds ago)"));
        assertTrue(tweetIsPosted);

    }
}