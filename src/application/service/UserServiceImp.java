package application.service;

import domain.Tweet;
import domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class UserServiceImp implements UserService {

    private final ArrayList<User> users = new ArrayList<>();
    private User userLogged;

    @Override
    public boolean createUser(String name) {
        boolean userPresent = findUser(name).isPresent();
        if (userPresent) {
            User user = new User(name);
            users.add(user);
        }
        return userPresent;
    }

    @Override
    public String postTweet(String messageToPost) {
        String message = String.format("%s: '%s'", userLogged.getName(), messageToPost);
        Tweet tweet = new Tweet(message, LocalDate.now());
        userLogged.addTweet(tweet);
        return message;
    }

    @Override
    public String followUser(String username) {
        String messageFinal = "";
        Optional<User> userExists = findUser(username);
        if (userExists.isPresent()) {
            User foundUser = userExists.get();
            userLogged.addFriendToList(foundUser);
            String message = userLogged.getName() + " ha seguido a " + userExists.get().getName();
            Tweet tweet = new Tweet(message, LocalDate.now());
            userLogged.addTweet(tweet);
            messageFinal = String.format("%s ahora sigue a  %s", userLogged.getName(), username);
        } else {
            messageFinal = String.format("No se ha encontrado al usuario %s", username);
        }
        return messageFinal;
    }

    @Override
    public boolean changeUser(String name) {
        Optional<User> user = findUser(name);
        if (user.isPresent()) {
            userLogged = user.get();
        } else {
        }
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
        return users.size() > 2;
    }

    @Override
    public void showUsers() {
        users.forEach(user -> System.out.println(user.getName()));
    }

    private Optional<User> findUser(String namePerson) {
        return users.stream().filter(user -> user.getName().equals(namePerson)).findFirst();
    }

    @Override
    public void wall() {
        userLogged.showWall();
    }


}
