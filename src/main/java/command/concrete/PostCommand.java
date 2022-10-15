package command.concrete;

import command.Command;
import command.TwitterReceiver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Tweet;

import java.util.Date;

@Slf4j
@AllArgsConstructor
public class PostCommand extends Command {

    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        System.out.printf("%s  write the message you want to post on your Wall:",
                twitterReceiver.getUserLogged().getName());
        String status = scanner.nextLine();
        String message = String.format("%s - %s", twitterReceiver.getUserLogged().getName(), status);
        Tweet tweet = Tweet.builder().message(message).time(new Date().getTime()).build();
        twitterReceiver.getUserLogged().addTweet(tweet);
        System.out.println("Tweet posted!");
    }
}
