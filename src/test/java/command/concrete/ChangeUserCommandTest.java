package command.concrete;

import command.TwitterReceiver;
import exception.UserNotFoundException;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErr;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChangeUserCommandTest {
    private static final TwitterReceiver twitterReceiver = new TwitterReceiver();

    @BeforeAll
    public static void init() throws Exception {
        withTextFromSystemIn("Edu").execute(() -> new LoginCommand(twitterReceiver).execute());
    }


    @Test
    @Order(1)
    void onlyExistUniqueUserTest() throws Exception {
        String s = tapSystemErr(() -> new ChangeUserCommand(twitterReceiver).execute());
        assertEquals("There is only one user, more than one user is needed for the change.\n", s);
    }

    @Test

    void userNotExistExceptionTest() throws Exception {
        withTextFromSystemIn("Juan").execute(() -> new CreateUserCommand(twitterReceiver).execute());
        withTextFromSystemIn("Pedro").execute(() -> new CreateUserCommand(twitterReceiver).execute());

        withTextFromSystemIn("Alberto").andExceptionThrownOnInputEnd(new UserNotFoundException()).execute(() -> new ChangeUserCommand(twitterReceiver).execute());
    }

}