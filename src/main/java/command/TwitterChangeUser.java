package command;

import lombok.extern.slf4j.Slf4j;
import model.User;

import java.util.Optional;

@Slf4j
public class TwitterChangeUser extends TwitterCommand {

    @Override
    void execute() {
        boolean canChangeUser = users.size() > 1;
        if (canChangeUser) {
            log.info("Escribe el nombre del usuario al que quieras cambiar: ");
            users.forEach(user -> log.info(user.getName()));
            String name = scanner.next();
            Optional<User> user = findUser(name, users);
            if (user.isPresent()) {
                userLogged = user.get();
                log.info("Bienvenido {} %n", user.get().getName());
            } else {
                log.error("El usuario {} no existe %n", name);
            }
        } else {
            log.error("Solo hay un usuario, es necesario mas de uno para el cambio");
        }
    }
}
