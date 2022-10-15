package command.concrete;

import command.Command;
import command.TwitterReceiver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.User;

@Slf4j
@RequiredArgsConstructor
public class CreateUserCommand extends Command {

    @NonNull
    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        System.out.printf("%s type the name of the user you want to include:%n",
                twitterReceiver.getUserLogged().getName());
        String name = scanner.nextLine();
        boolean userExist =
                twitterReceiver.getUsers().stream().noneMatch(user -> user.getName().equalsIgnoreCase(name));
        User userToAdd = null;
        if (userExist) {
            userToAdd = new User(name);
            twitterReceiver.getUsers().add(userToAdd);
        }
        if (userToAdd != null) {
            System.out.printf("%s has added user %s to EduSocial%n", twitterReceiver.getUserLogged().getName(),
                    userToAdd.getName());
        } else {
            System.err.printf("The user with the name %s already exists%n", name);
        }
    }
}
