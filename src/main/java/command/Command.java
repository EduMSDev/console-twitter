package command;

import lombok.NoArgsConstructor;

import java.util.Scanner;

@NoArgsConstructor
public abstract class Command {

    protected final Scanner scanner = new Scanner(System.in);

    protected abstract void execute();
}
