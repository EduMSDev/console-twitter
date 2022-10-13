package command;

import lombok.extern.slf4j.Slf4j;
import model.Tweet;

import java.util.ArrayList;
import java.util.Comparator;

@Slf4j
public class TwitterWall extends TwitterCommand {

    @Override
    void execute() {
        log.info("A continuacion se va a mostrar el mural:");
        ArrayList<Tweet> wall = new ArrayList<>();
        userLogged.getFriends().forEach(friend -> wall.addAll(friend.getTweets()));
        wall.addAll(userLogged.getTweets());
        if (!wall.isEmpty()) {
            wall.stream().sorted(Comparator.comparing(Tweet::getTime)).forEach(this::calculateTime);
            log.info("Fin del muro");
        } else {
            log.error("No se ha posteado ningun tweet. Nada que mostrar");
        }
    }

}
