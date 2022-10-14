package app;

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

import java.util.Arrays;
import java.util.Scanner;

public class TwitterMenu {
    Scanner scanner = new Scanner(System.in);

    public void launchApp() {
        TwitterReceiver twitterReceiver = new TwitterReceiver();
        new LoginCommand(twitterReceiver).execute();
        Options options = showMenu();

        while (options != Options.EXIT) {
            switch (options) {
                case POST:
                    new PostCommand(twitterReceiver).execute();
                    break;
                case READ:
                    new ReadCommand(twitterReceiver).execute();
                    break;
                case CREATE_USER:
                    new CreateUserCommand(twitterReceiver).execute();
                    break;
                case FOLLOW:
                    new FollowCommand(twitterReceiver).execute();
                    break;
                case WALL:
                    new WallCommand(twitterReceiver).execute();
                    break;
                case CHANGE_USER:
                    new ChangeUserCommand(twitterReceiver).execute();
                    break;
                case UNFOLLOW:
                    new UnfollowCommand(twitterReceiver).execute();
                    break;
                default:
                    System.out.println("Unknown option.");
            }
            options = showMenu();
        }
    }

    private Options showMenu() {
        System.out.println("Choose from the following options:");
        Arrays.stream(Options.values()).forEach(option -> System.out.println(option.getNameOption()));
        String option = scanner.nextLine();
        return Options.getOptions(option);
    }
}
