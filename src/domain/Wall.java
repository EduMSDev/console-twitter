package domain;

import java.util.ArrayList;
import java.util.Comparator;

public class Wall {

    private final ArrayList<Tweet> tweets = new ArrayList<>();

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public void showWall() {
        tweets.stream().sorted(Comparator.comparing(Tweet::getTime)).forEach(tweet -> System.out.println(tweet.getTime() + tweet.getMessage()));
    }

}
