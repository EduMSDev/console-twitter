package command.concrete;

import command.Command;
import command.TwitterReceiver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Tweet;

import java.util.ArrayList;
import java.util.Comparator;

@Slf4j
@AllArgsConstructor
public class WallCommand extends Command {

    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        System.out.printf("%s your mural will now be displayed:", twitterReceiver.getUserLogged().getName());
        ArrayList<Tweet> wall = new ArrayList<>();
        twitterReceiver.getUserLogged().getFriends().forEach(friend -> wall.addAll(friend.getTweets()));
        wall.addAll(twitterReceiver.getUserLogged().getTweets());
        if (!wall.isEmpty()) {
            wall.stream().sorted(Comparator.comparing(Tweet::getTime)).forEach(twitterReceiver::calculateTime);
            System.out.println("End of the wall");
        } else {
            System.err.println("No tweets have been posted!");
        }
    }

}
