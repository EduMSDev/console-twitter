package command.concrete;

import command.Command;
import command.TwitterReceiver;
import exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Tweet;
import model.User;

import java.util.Date;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class UnfollowCommand extends Command {

    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        System.out.printf("%s write your friend's name:%n", twitterReceiver.getUserLogged().getName());
        twitterReceiver.getUserLogged().getFriends().forEach(friend-> System.out.println(friend.getName()));
        String name = scanner.nextLine();
        try {
            User user = twitterReceiver.findUser(name);
            Optional<User> friendToRemove = twitterReceiver.findFriend(user.getName());
            if (friendToRemove.isPresent()) {
                twitterReceiver.getUserLogged().removeFriend(friendToRemove.get());
                String tweet = String.format("%s has unfollowed %s%n", twitterReceiver.getUserLogged().getName(),
                        name);
                Tweet unfollowedUser = Tweet.builder().message(tweet).time(new Date().getTime()).build();
                twitterReceiver.getUserLogged().addTweet(unfollowedUser);
                System.out.printf(tweet);
            } else {
                System.err.printf("%s is not in your friendslist!%n", user.getName());
            }
        } catch (UserNotFoundException e) {
            System.err.printf(e.getMessage());
        }

    }
}
