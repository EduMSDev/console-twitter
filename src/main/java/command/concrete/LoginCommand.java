package command.concrete;

import command.Command;
import command.TwitterReceiver;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.User;

@Slf4j

@RequiredArgsConstructor
public class LoginCommand extends Command {

    @NonNull
    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        System.out.println("Welcome to EduSocial, your trusted social network ;). Please enter your name: ");
        String namePerson = scanner.nextLine();
        User user = User.builder().name(namePerson).build();
        twitterReceiver.setUserLogged(user);
        twitterReceiver.getUsers().add(user);
        System.out.printf("%s writes to the world what you want to say %n", namePerson);
    }
}
