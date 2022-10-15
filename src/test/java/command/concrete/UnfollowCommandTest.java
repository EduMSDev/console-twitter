package command.concrete;

import base.TwitterTestBase;
import command.TwitterReceiver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class UnfollowCommandTest extends TwitterTestBase {

    private static TwitterReceiver twitterReceiver = new TwitterReceiver();

    @BeforeAll
    static void setUp() throws Exception{
        doLogin(twitterReceiver);
    }
}