package command;

import exception.UserNotFoundException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TwitterReceiverTest {

    private TwitterReceiver twitterReceiver;

    @BeforeEach
    public void init() {
        twitterReceiver = new TwitterReceiver();
    }


    @Test
    void findUserTest() {
        User user = User.builder().name("Juan").build();
        twitterReceiver.getUsers().add(user);

        User userFound = twitterReceiver.findUser("Juan");

        assertEquals(user.getName(), userFound.getName());
    }

    @Test
    void notFindUserThrowsUserNotFoundExceptionTest() {
        UserNotFoundException notFoundException = assertThrows(UserNotFoundException.class,
                () -> twitterReceiver.findUser("Juan"));

        assertEquals("User Juan not found\n", notFoundException.getMessage());
    }

}