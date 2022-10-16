package command.concrete;

import command.Command;
import command.TwitterReceiver;
import exception.UserNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ChangeUserCommand extends Command {

    @NonNull
    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        boolean canChangeUser = twitterReceiver.getUsers().size() > 1;
        if (canChangeUser) {
            System.out.printf("%s enter the name of the user you want to change:%n",
                    twitterReceiver.getUserLogged().getName());
            twitterReceiver.showUsers();
            String name = super.scanner.nextLine();
            try {
                twitterReceiver.setUserLogged(twitterReceiver.findUser(name));
                System.out.printf("Welcome %s%n", twitterReceiver.getUserLogged().getName());
            } catch (UserNotFoundException e) {
                System.err.printf(e.getMessage());
            }
        } else {
            System.err.println("There is only one user, more than one user is needed for the change.");
        }
    }
}
