package command.concrete;

import command.Command;
import command.TwitterReceiver;
import exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Tweet;
import model.User;

import java.util.Date;

@Slf4j
@AllArgsConstructor
public class UnfollowCommand extends Command {

    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        System.out.printf("%s write the username: ", twitterReceiver.getUserLogged().getName());
        String name = scanner.nextLine();
        try {
            User friendToRemove = twitterReceiver.findUser(name);
            twitterReceiver.getUserLogged().removeFriend(friendToRemove);
            String tweet = String.format("%s has unfollowed %s %n", twitterReceiver.getUserLogged().getName(), name);
            Tweet unfollowedUser = Tweet.builder().message(tweet).time(new Date().getTime()).build();
            twitterReceiver.getUserLogged().addTweet(unfollowedUser);
            System.out.printf(tweet);
        } catch (UserNotFoundException e) {
            System.err.printf("%s is not in your friends list  %n", name);
        }
    }
}
