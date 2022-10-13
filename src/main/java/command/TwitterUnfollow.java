package command;

import lombok.extern.slf4j.Slf4j;
import model.Tweet;
import model.User;

import java.util.Date;
import java.util.Optional;

@Slf4j
public class TwitterUnfollow extends TwitterCommand {


    @Override
    void execute() {
        log.info("Escribe el nombre del usuario: ");
        String name = scanner.next();
        Optional<User> friendToRemove = this.findUser(name, userLogged.getFriends());
        friendToRemove.ifPresent(userLogged::removeFriend);
        if (friendToRemove.isPresent()) {
            String tweet = String.format("%s ha dejado de seguir a %s %n", userLogged.getName(), name);
            Tweet unfollowedUser = Tweet.builder().message(tweet).time(new Date().getTime()).build();
            userLogged.addTweet(unfollowedUser);
            log.info(tweet);
        } else {
            log.error("{} no esta en tu lista de amigos %n", name);
        }
    }
}
