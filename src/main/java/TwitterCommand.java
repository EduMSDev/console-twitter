import lombok.extern.slf4j.Slf4j;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
public abstract class TwitterCommand {

    protected Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

    protected final ArrayList<User> users = new ArrayList<>();

    protected User userLogged;

    abstract void execute();

    protected Optional<User> findUser(String namePerson, List<User> listUser) {
        return listUser.stream().filter(user -> user.getName().equalsIgnoreCase(namePerson)).findFirst();
    }
}
