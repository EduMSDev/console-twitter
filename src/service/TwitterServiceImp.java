package service;

import model.Tweet;
import model.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TwitterServiceImp implements TwitterService {

    private final ArrayList<User> users = new ArrayList<>();
    private User userLogged;

    @Override
    public boolean createUser(String name) {
        boolean userExist = findUser(name, users).isPresent();
        if (!userExist) {
            User user = new User(name);
            users.add(user);
        }
        return userExist;
    }

    @Override
    public String postTweet(String messageToPost) {
        String message = String.format("%s: '%s'", userLogged.getName(), messageToPost);
        Tweet tweet = new Tweet(message);
        userLogged.addTweet(tweet);
        return message;
    }

    @Override
    public String followUser(String username) {
        String messageFinal;
        Optional<User> userExists = findUser(username, users);
        if (userExists.isPresent()) {
            User foundUser = userExists.get();
            userLogged.addFriendToList(foundUser);
            String message = String.format("%s ha seguido a %s", userLogged.getName(), userExists.get().getName());
            Tweet tweet = new Tweet(message);
            userLogged.addTweet(tweet);
            messageFinal = String.format("%s ahora sigue a  %s %n", userLogged.getName(), username);
        } else {
            messageFinal = String.format("No se ha encontrado al usuario %s %n", username);
        }
        return messageFinal;
    }

    @Override
    public boolean changeUser(String name) {
        Optional<User> user = findUser(name, users);
        user.ifPresent(value -> userLogged = value);
        return user.isPresent();
    }

    @Override
    public void login(String namePerson) {
        User user = new User(namePerson);
        userLogged = user;
        users.add(user);
    }

    @Override
    public boolean canChangeUser() {
        return users.size() > 1;
    }

    @Override
    public void showUsers() {
        users.forEach(user -> System.out.println(user.getName()));
    }

    @Override
    public boolean unfollowUser(String username) {
        Optional<User> friendToRemove = this.findUser(username, userLogged.getFriends());
        friendToRemove.ifPresent(value -> userLogged.removeFriend(value));
        return friendToRemove.isPresent();
    }

    private Optional<User> findUser(String namePerson, List<User> listUser) {
        return listUser.stream().filter(user -> user.getName().equals(namePerson)).findFirst();
    }

    @Override
    public void wall() {
        ArrayList<Tweet> wall = new ArrayList<>();
        userLogged.getFriends().forEach(friend -> wall.addAll(friend.getTweets()));
        wall.addAll(userLogged.getTweets());
        wall.stream().sorted(Comparator.comparing(Tweet::getTime)).forEach(tweet -> System.out.println(tweet.getMessage() + " " + tweet.getTime()));
    }
}
