import command.Invoker;
import command.TwitterReceiver;
import command.concrete.ChangeUserCommand;
import command.concrete.CreateUserCommand;
import command.concrete.FollowCommand;
import command.concrete.LoginCommand;
import command.concrete.PostCommand;
import command.concrete.ReadCommand;
import command.concrete.UnfollowCommand;
import command.concrete.WallCommand;
import model.Enum.Options;
import singleton.ScannerSingleton;

import java.util.Arrays;

public class Main {

    public static void main(String... args ) {
        TwitterReceiver twitterReceiver = new TwitterReceiver();
        Invoker invoker = new Invoker();

        invoker.setCommand(new LoginCommand(twitterReceiver)).run();
        Options options = showMenu();

        while (options != Options.EXIT) {
            switch (options) {
                case POST:
                    invoker.setCommand(new PostCommand(twitterReceiver)).run();
                    break;
                case READ:
                    invoker.setCommand(new ReadCommand(twitterReceiver)).run();
                    break;
                case CREATE_USER:
                    invoker.setCommand(new CreateUserCommand(twitterReceiver)).run();
                    break;
                case FOLLOW:
                    invoker.setCommand(new FollowCommand(twitterReceiver)).run();
                    break;
                case WALL:
                    invoker.setCommand(new WallCommand(twitterReceiver)).run();
                    break;
                case CHANGE_USER:
                    invoker.setCommand(new ChangeUserCommand(twitterReceiver)).run();
                    break;
                case UNFOLLOW:
                    invoker.setCommand(new UnfollowCommand(twitterReceiver)).run();
                    break;
                default:
                    System.out.println("Opcion desconocida.");
            }
            options = showMenu();
        }
    }

    private static Options showMenu() {
        System.out.println("Escoge una de las siguientes opciones:");
        Arrays.stream(Options.values()).forEach(option -> System.out.println(option.getNameOption()));
        String option = ScannerSingleton.getInstance().nextLine();
        return Options.getOptions(option);
    }
}
