package command.concrete;

import command.Command;
import command.TwitterReceiver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ReadCommand extends Command {

    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        if (!twitterReceiver.getUserLogged().getTweets().isEmpty()) {
            System.out.printf("%s this is your personal wall:%n", twitterReceiver.getUserLogged().getName());
            twitterReceiver.getUserLogged().getTweets().forEach(twitterReceiver::calculateTime);
        } else {
            System.err.printf("%s you have not published anything!%n", twitterReceiver.getUserLogged().getName());
        }
    }
}
