package app;

import command.TwitterReceiver;
import command.concrete.PostCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Scanner;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class TwitterMenuTest {


    private TwitterMenu twitterMenu;

    private TwitterReceiver twitterReceiver;

    @BeforeEach
    public void init() throws Exception {
        twitterMenu = new TwitterMenu();
        twitterReceiver = new TwitterReceiver();
    }


    @Test
    void chooseOptionTest() throws Exception {


    }
}
