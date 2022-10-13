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
            System.out.printf("%s escribe el nombre del usuario al que quieras cambiar: ", userLogged.getName());
            users.forEach(user -> System.out.printf(user.getName()));
            String name = ScannerSingleton.getInstance().next();
            Optional<User> user = findUser(name, users);
            if (user.isPresent()) {
                userLogged = user.get();
                System.out.printf("Bienvenido %s %n", user.get().getName());
            } else {
                System.err.printf("El usuario %s no existe %n", name);
            }
        } else {
            System.err.println("Solo hay un usuario, es necesario mas de uno para el cambio");
        }
    }

    public void createUser() {
        System.out.printf("%s escribe el nombre del usuario al que quieras incluir: ", userLogged.getName());
        String name = ScannerSingleton.getInstance().nextLine();
        Optional<User> user = findUser(name, users);
        User userToAdd = null;
        if (user.isEmpty()) {
            userToAdd = new User(name);
            users.add(userToAdd);
        }
        if (userToAdd != null) {
            System.out.printf("%s ha incluido al usuario %s a EduSocial", userLogged.getName(), userToAdd.getName());
        } else {
            System.err.printf("El usuario con nombre %s ya existe %n", name);
        }
    }

    public void followUser() {
        System.out.printf("%s escribe el nombre del usuario que quieres seguir: ", userLogged.getName());
        String namePerson = ScannerSingleton.getInstance().nextLine();
        Optional<User> userExists = findUser(namePerson, users);
        if (userExists.isPresent()) {
            userLogged.addFriendToList(userExists.get());
            String followed = String.format("%s ha seguido a %s", userLogged.getName(), userExists.get().getName());
            Tweet tweet = Tweet.builder().message(followed).time(new Date().getTime()).build();
            userLogged.addTweet(tweet);
            System.out.printf("%s ahora sigue a  %s %n", userLogged.getName(), namePerson);
        } else {
            System.err.printf("No se ha encontrado al usuario %s %n", namePerson);
        }
        boolean userAdded = userExists.isPresent();
        String message = userAdded ? "Usuario %s incluido %n" : "El usuario con nombre %s ya existe %n";
        System.out.printf(message, namePerson);
    }

    public void login() {
        System.out.println("Bienvenido a EduSocial, tu red social de confianza ;). Por favor,  escribe tu nombre: ");
        String namePerson = ScannerSingleton.getInstance().nextLine();
        User user = User.builder().name(namePerson).build();
        userLogged = user;
        users.add(user);
        System.out.printf("%s escribe al mundo lo que quieras %n", namePerson);
    }

    public void post() {
        System.out.printf("%s escribe el mensaje que quieres postear en tu WallCommand:", userLogged.getName());
        String status = ScannerSingleton.getInstance().nextLine();
        String message = String.format("%s - %s", userLogged.getName(), status);
        Tweet tweet = Tweet.builder().message(message).time(new Date().getTime()).build();
        userLogged.addTweet(tweet);
        System.out.println("Tweet posteado!");
    }

    public void read() {
        System.out.printf("%s este es tu muro personal: ", userLogged.getName());
        if (!userLogged.getTweets().isEmpty()) {
            userLogged.getTweets().forEach(this::calculateTime);
            System.out.println("Fin del muro");
        } else {
            System.err.printf("%s no has publicado nada!", userLogged.getName());
        }
    }

    public void unfollow() {
        System.out.printf("%s escribe el nombre del usuario: ", userLogged.getName());
        String name = ScannerSingleton.getInstance().next();
        Optional<User> friendToRemove = this.findUser(name, userLogged.getFriends());
        friendToRemove.ifPresent(userLogged::removeFriend);
        if (friendToRemove.isPresent()) {
            String tweet = String.format("%s ha dejado de seguir a %s %n", userLogged.getName(), name);
            Tweet unfollowedUser = Tweet.builder().message(tweet).time(new Date().getTime()).build();
            userLogged.addTweet(unfollowedUser);
            System.out.printf(tweet);
        } else {
            System.err.printf("%s no esta en tu lista de amigos %n", name);
        }
    }

    public void wall() {
        System.out.printf("%s a continuacion se va a mostrar tu mural:", userLogged.getName());
        ArrayList<Tweet> wall = new ArrayList<>();
        userLogged.getFriends().forEach(friend -> wall.addAll(friend.getTweets()));
        wall.addAll(userLogged.getTweets());
        if (!wall.isEmpty()) {
            wall.stream().sorted(Comparator.comparing(Tweet::getTime)).forEach(this::calculateTime);
            System.out.println("Fin del muro");
        } else {
            System.err.println("No se ha posteado ningun tweet. Nada que mostrar");
        }
    }


}
