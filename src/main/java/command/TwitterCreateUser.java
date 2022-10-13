package command;

import lombok.extern.slf4j.Slf4j;
import model.User;

import java.util.Optional;

@Slf4j
public class TwitterCreateUser extends TwitterCommand {

    @Override
    void execute() {
        log.info("Escribe el nombre del usuario al que quieras incluir: ");
        String name = scanner.nextLine();
        Optional<User> user = findUser(name, users);
        User userToAdd = null;
        if (user.isEmpty()) {
            userToAdd = new User(name);
            users.add(userToAdd);
        }
        if (userToAdd != null) {
            log.info("{} ha incluido al usuario {} a EduSocial", userLogged.getName(), userToAdd.getName());
        } else {
            log.error("El usuario con nombre {} ya existe %n", name);
        }
    }
}
