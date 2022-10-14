package command;

import exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import model.Tweet;
import model.User;
import singleton.ScannerSingleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TwitterReceiver {

    private final ArrayList<User> users = new ArrayList<>();
    private User userLogged;

    private User findUser(String namePerson) {
        return users.stream().filter(user -> user.getName().equalsIgnoreCase(namePerson)).findFirst().orElseThrow(UserNotFoundException::new);
    }

    private void calculateTime(Tweet tweet) {
        long time = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - tweet.getTime());
        String measure;
        if (time < 60) {
            measure = " seconds ago";
        } else {
            time = TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - tweet.getTime());
            measure = " minute(s) ago";
        }
        System.out.printf("%s (%s %s)", tweet.getMessage(), time, measure);
    }

    public void changeUser() {
        boolean canChangeUser = users.size() > 1;
        if (canChangeUser) {
            System.out.printf("%s enter the name of the user you want to change:: ", userLogged.getName());
            users.forEach(user -> System.out.printf(user.getName()));
            String name = ScannerSingleton.getInstance().next();
            try {
                userLogged = findUser(name);
                System.out.printf("Welcome %s %n", userLogged.getName());
            } catch (UserNotFoundException e) {
                System.err.printf("The user %s does not exist %n", name);
            }
        } else {
            System.err.println("There is only one user, more than one user is needed for the change.");
        }
    }

    public void createUser() {
        System.out.printf("%s  type the name of the user you want to include: ", userLogged.getName());
        String name = ScannerSingleton.getInstance().nextLine();
        boolean userExist = users.stream().noneMatch(user -> user.getName().equalsIgnoreCase(name));
        User userToAdd = null;
        if (userExist) {
            userToAdd = new User(name);
            users.add(userToAdd);
        }
        if (userToAdd != null) {
            System.out.printf("%s has added user %s to EduSocial", userLogged.getName(), userToAdd.getName());
        } else {
            System.err.printf("The user with the name %s already exists %n", name);
        }
    }

    public void followUser() {
        System.out.printf("%s  enter the name of the user you want to follow: ", userLogged.getName());
        String namePerson = ScannerSingleton.getInstance().nextLine();
        try {
            User userExists = findUser(namePerson);
            userLogged.addFriendToList(userExists);
            String followed = String.format("%s follows %s %n", userLogged.getName(), userExists.getName());
            Tweet tweet = Tweet.builder().message(followed).time(new Date().getTime()).build();
            userLogged.addTweet(tweet);
            System.out.printf(followed);
        } catch (UserNotFoundException e) {
            System.err.printf("User %s not found", namePerson);
        }
        boolean userAdded = users.stream().anyMatch(user -> user.getName().equalsIgnoreCase(namePerson));
        String message = userAdded ? "User %s added %n" : "The user with name %s already exists %n";
        System.out.printf(message, namePerson);
    }

    public void login() {
        System.out.println("Welcome to EduSocial, your trusted social network ;). Please enter your name: ");
        String namePerson = ScannerSingleton.getInstance().nextLine();
        User user = User.builder().name(namePerson).build();
        userLogged = user;
        users.add(user);
        System.out.printf("%s writes to the world what you want to say %n", namePerson);
    }

    public void post() {
        System.out.printf("%s  write the message you want to post on your Wall:", userLogged.getName());
        String status = ScannerSingleton.getInstance().nextLine();
        String message = String.format("%s - %s", userLogged.getName(), status);
        Tweet tweet = Tweet.builder().message(message).time(new Date().getTime()).build();
        userLogged.addTweet(tweet);
        System.out.println("Tweet posted!");
    }

    public void read() {
        System.out.printf("%s this is your personal wall: ", userLogged.getName());
        if (!userLogged.getTweets().isEmpty()) {
            userLogged.getTweets().forEach(this::calculateTime);
            System.out.println("End of the wall");
        } else {
            System.err.printf("%s you have not published anything!", userLogged.getName());
        }
    }

    public void unfollow() {
        System.out.printf("%s write the username: ", userLogged.getName());
        String name = ScannerSingleton.getInstance().next();
        try {
            User friendToRemove = this.findUser(name);
            userLogged.removeFriend(friendToRemove);
            String tweet = String.format("%s has unfollowed %s %n", userLogged.getName(), name);
            Tweet unfollowedUser = Tweet.builder().message(tweet).time(new Date().getTime()).build();
            userLogged.addTweet(unfollowedUser);
            System.out.printf(tweet);
        } catch (UserNotFoundException e) {
            System.err.printf("%s is not in your friends list  %n", name);
        }
    }

    public void wall() {
        System.out.printf("%s your mural will now be displayed:", userLogged.getName());
        ArrayList<Tweet> wall = new ArrayList<>();
        userLogged.getFriends().forEach(friend -> wall.addAll(friend.getTweets()));
        wall.addAll(userLogged.getTweets());
        if (!wall.isEmpty()) {
            wall.stream().sorted(Comparator.comparing(Tweet::getTime)).forEach(this::calculateTime);
            System.out.println("End of the wall");
        } else {
            System.err.println("No tweets have been posted!");
        }
    }


}
