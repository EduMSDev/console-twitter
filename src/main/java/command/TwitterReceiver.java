package command;

import lombok.extern.slf4j.Slf4j;
import model.Tweet;
import model.User;
import singleton.ScannerSingleton;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TwitterReceiver {

    private final ArrayList<User> users = new ArrayList<>();
    private User userLogged;

    private Optional<User> findUser(String namePerson, List<User> listUser) {
        return listUser.stream().filter(user -> user.getName().equalsIgnoreCase(namePerson)).findFirst();
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
            Optional<User> user = findUser(name, users);
            if (user.isPresent()) {
                userLogged = user.get();
                System.out.printf("Welcome %s %n", user.get().getName());
            } else {
                System.err.printf("The user %s does not exist %n", name);
            }
        } else {
            System.err.println("There is only one user, more than one user is needed for the change.");
        }
    }

    public void createUser() {
        System.out.printf("%s  type the name of the user you want to include: ", userLogged.getName());
        String name = ScannerSingleton.getInstance().nextLine();
        Optional<User> user = findUser(name, users);
        User userToAdd = null;
        if (user.isEmpty()) {
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
        Optional<User> userExists = findUser(namePerson, users);
        if (userExists.isPresent()) {
            userLogged.addFriendToList(userExists.get());
            String followed = String.format("%s ha seguido a %s", userLogged.getName(), userExists.get().getName());
            Tweet tweet = Tweet.builder().message(followed).time(new Date().getTime()).build();
            userLogged.addTweet(tweet);
            System.out.printf("%s follows  %s %n", userLogged.getName(), namePerson);
        } else {
            System.err.printf("User not found", namePerson);
        }
        boolean userAdded = userExists.isPresent();
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
        Optional<User> friendToRemove = this.findUser(name, userLogged.getFriends());
        friendToRemove.ifPresent(userLogged::removeFriend);
        if (friendToRemove.isPresent()) {
            String tweet = String.format("%s has unfollowed %s %n", userLogged.getName(), name);
            Tweet unfollowedUser = Tweet.builder().message(tweet).time(new Date().getTime()).build();
            userLogged.addTweet(unfollowedUser);
            System.out.printf(tweet);
        } else {
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
