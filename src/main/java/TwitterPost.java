import lombok.extern.slf4j.Slf4j;
import model.Tweet;
import model.User;

import java.util.Date;

@Slf4j
public class TwitterPost extends TwitterCommand {

    @Override
    public void execute() {
        log.info("Escribe el mensaje que quieres postear en tu Wall:");
        String status = scanner.nextLine();
        String message = String.format("%s - %s", userLogged.getName(), status);
        Tweet tweet = Tweet.builder().message(message).time(new Date().getTime()).build();
        userLogged.addTweet(tweet);
        log.info("Tweet posteado!");
    }
}
