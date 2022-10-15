package base;

import command.TwitterReceiver;
import command.concrete.LoginCommand;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;

public abstract class TwitterTestBase {

    protected static void doLogin(TwitterReceiver twitterReceiver) throws Exception {
        withTextFromSystemIn("Edu").execute(() -> new LoginCommand(twitterReceiver).execute());
    }

    protected String getLastMessageFromConsole(AtomicReference<String> consoleOutput) {
        Stream<String> messageStream = Arrays.stream(consoleOutput.get().split("\n"));
        return messageStream.reduce((first, second) -> second).orElse(null);
    }
}
