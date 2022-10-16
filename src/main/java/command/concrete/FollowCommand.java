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
public class FollowCommand extends Command {

    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        System.out.printf("%s  enter the name of the user you want to follow:%n ",
                twitterReceiver.getUserLogged().getName());
        twitterReceiver.showUsers();
        String namePerson = scanner.nextLine();
        try {
            User friendToFind = twitterReceiver.findUser(namePerson);
            Optional<User> friendOptional = twitterReceiver.findFriend(namePerson);
            if (friendOptional.isEmpty()) {
                twitterReceiver.getUserLogged().addFriendToList(friendToFind);
                String followed = String.format("%s follows %s", twitterReceiver.getUserLogged().getName(),
                        friendToFind.getName());
                Tweet tweet = Tweet.builder().message(followed).time(new Date().getTime()).build();
                twitterReceiver.getUserLogged().addTweet(tweet);
                System.out.println(followed);
            } else {
                System.err.printf("%s you already follow the user %s%n", twitterReceiver.getUserLogged().getName(),friendOptional.get().getName());
            }
        } catch (UserNotFoundException e) {
            System.err.printf(e.getMessage());
        }
    }
}
