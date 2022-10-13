package command;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TwitterRead extends TwitterCommand {

    @Override
    void execute() {
        log.info("{} este es tu muro personal: ");
        if (!userLogged.getTweets().isEmpty()) {
            userLogged.getTweets().forEach(this::calculateTime);
            log.info("Fin del muro");
        } else {
            log.error("{} no has publicado nada!", userLogged.getName());
        }
    }
}
