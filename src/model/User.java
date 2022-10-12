package model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<Tweet> tweets = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public String getName() {
        return name;
    }

    public List<User> getFriends() {
        return friends;
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
