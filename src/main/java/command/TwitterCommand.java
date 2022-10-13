package command;

import lombok.extern.slf4j.Slf4j;
import model.Tweet;
import model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class TwitterCommand {

    protected final ArrayList<User> users = new ArrayList<>();
    protected Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
    protected User userLogged;

    abstract void execute();

    protected Optional<User> findUser(String namePerson, List<User> listUser) {
        return listUser.stream().filter(user -> user.getName().equalsIgnoreCase(namePerson)).findFirst();
    }

    protected void calculateTime(Tweet tweet) {
        long time = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - tweet.getTime());
        String measure;
        if (time < 60) {
            measure = " seconds ago";
        } else {
            time = TimeUnit.MILLISECONDS.toMinutes(new Date().getTime() - tweet.getTime());
            measure = " minute(s) ago";
        }
        log.info("{} ({} {})", tweet.getMessage(), time, measure);

    }
}
