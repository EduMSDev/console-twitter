package command.concrete;

import command.Command;
import command.TwitterReceiver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ChangeUserCommand implements Command {

    private TwitterReceiver twitterReceiver;

    @Override
    public void execute() {
        twitterReceiver.changeUser();
    }
}
