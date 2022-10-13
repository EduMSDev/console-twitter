package command;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class Invoker {

    private Command command;

    public Invoker setCommand(Command command) {
        this.command = command;
        return this;
    }

    public void run() {
        command.execute();
    }

}
