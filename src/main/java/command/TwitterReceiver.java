package command;

import exception.UserNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.Tweet;
import model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Setter
@Getter
public class TwitterReceiver {
    private ArrayList<User> users = new ArrayList<>();
    private User userLogged;

    public User findUser(String namePerson) {
        return users.stream().filter(user -> user.getName().equalsIgnoreCase(namePerson)).findFirst().orElseThrow(UserNotFoundException::new);
    }

    public void calculateTime(Tweet tweet) {
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

    public void showUsers() {
        this.users.stream().filter(user -> !user.getName().equalsIgnoreCase(userLogged.getName()))
                .forEach(user -> System.out.printf("* %s%n", user.getName()));
    }
}
