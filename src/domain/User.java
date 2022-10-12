package domain;

import java.util.ArrayList;
import java.util.Comparator;

public class User {

    private String name;
    private ArrayList<User> friends = new ArrayList<>();
    private ArrayList<Tweet> tweets = new ArrayList<>();

    private Wall wall = new Wall();

    public User(String name) {
        this.name = name;
    }

    public Wall getTimeLine() {
        return wall;
    }

    public void setTimeLine(Wall wall) {
        this.wall = wall;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(ArrayList<Tweet> tweets) {
        this.tweets = tweets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void addFriendToList(User user) {
        this.friends.add(user);
    }

    public void addTweet(Tweet tweet) {
        this.tweets.add(tweet);
    }

    public void showWall() {
        ArrayList<Tweet> wall = new ArrayList<>();
        friends.forEach(friend -> wall.addAll(friend.getTweets()));
        wall.addAll(this.getTweets());
        wall.stream().sorted(Comparator.comparing(Tweet::getTime)).forEach(tweet -> System.out.println(tweet.getTime() + tweet.getMessage()));
    }
}
