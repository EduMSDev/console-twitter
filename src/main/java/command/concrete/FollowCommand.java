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
public class FollowCommand extends Command {

    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        System.out.printf("%s  enter the name of the user you want to follow: ",  twitterReceiver.getUserLogged().getName());
        twitterReceiver.showUsers();
        String namePerson = scanner.nextLine();
        try {
            User userExists = twitterReceiver.findUser(namePerson);
             twitterReceiver.getUserLogged().addFriendToList(userExists);
            String followed = String.format("%s follows %s %n",  twitterReceiver.getUserLogged().getName(), userExists.getName());
            Tweet tweet = Tweet.builder().message(followed).time(new Date().getTime()).build();
             twitterReceiver.getUserLogged().addTweet(tweet);
            System.out.printf(followed);
        } catch (UserNotFoundException e) {
            System.err.printf("User %s not found", namePerson);
        }
        boolean userAdded = twitterReceiver.getUsers().stream().anyMatch(user -> user.getName().equalsIgnoreCase(namePerson));
        String message = userAdded ? "User %s added %n" : "The user with name %s already exists %n";
        System.out.printf(message, namePerson);
    }
}
