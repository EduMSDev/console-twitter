package model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Builder
@Getter
public class User {

    private final String name;
    private final ArrayList<User> friends = new ArrayList<>();
    private final ArrayList<Tweet> tweets = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public void addFriendToList(User user) {
        this.friends.add(user);
    }

    public void addTweet(Tweet tweet) {
        this.tweets.add(tweet);
    }

    public void removeFriend(User user) {
        this.friends.remove(user);
    }
}
