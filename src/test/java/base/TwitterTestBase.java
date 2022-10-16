package base;

import command.TwitterReceiver;
import command.concrete.LoginCommand;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;

public abstract class TwitterTestBase {

    protected TwitterReceiver twitterReceiver;

    @BeforeEach
    protected void initTest() throws Exception{
        twitterReceiver = new TwitterReceiver();
        withTextFromSystemIn("Edu").execute(() -> new LoginCommand(twitterReceiver).execute());
    }

    protected String getLastMessageFromConsole(String consoleOutput) {
        return this.getMessagesFromConsole(consoleOutput).reduce((first, second) -> second).orElse(null);

    }

    protected Stream<String> getMessagesFromConsole(String consoleOutput) {
        return Arrays.stream(consoleOutput.split("\n"));
    }
}
