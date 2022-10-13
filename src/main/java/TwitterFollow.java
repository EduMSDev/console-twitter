import lombok.extern.slf4j.Slf4j;
import model.Tweet;
import model.User;

import java.util.Date;
import java.util.Optional;

@Slf4j
public class TwitterFollow extends TwitterCommand{

    @Override
    void execute() {
        log.info("Introduce el  nombre del usuario: ");
        String namePerson = scanner.nextLine();
        Optional<User> userExists = findUser(namePerson, users);
        if (userExists.isPresent()) {
            userLogged.addFriendToList(userExists.get());
            String followed = String.format("%s ha seguido a %s", userLogged.getName(), userExists.get().getName());
            Tweet tweet = Tweet.builder().message(followed).time(new Date().getTime()).build();
            userLogged.addTweet(tweet);
            log.info("{} ahora sigue a  {} %n", userLogged.getName(), namePerson);
        } else {
            log.error("No se ha encontrado al usuario {} %n", namePerson);
        }
        boolean userAdded = userExists.isPresent();
        String message = userAdded ? "Usuario {} incluido %n" : "El usuario con nombre {} ya existe %n";
        log.info(message, namePerson);
    }
}
