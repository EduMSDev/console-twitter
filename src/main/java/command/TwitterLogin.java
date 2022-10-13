package command;

import lombok.extern.slf4j.Slf4j;
import model.User;

@Slf4j
public class TwitterLogin extends TwitterCommand {

    @Override
    void execute() {
        log.info("Bienvenido a EduSocial, tu red social de confianza ;). Por favor,  escribe tu nombre: ");
        String namePerson = this.scanner.nextLine();
        User user = User.builder().name(namePerson).build();
        userLogged = user;
        users.add(user);
        log.info("{} escribe al mundo lo que quieras %n", namePerson);
    }
}
