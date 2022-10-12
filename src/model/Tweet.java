package model;

import java.util.Date;


public class Tweet {
    private final String message;
    private final Long time;

    public Tweet(String message) {
        this.message = message;
        this.time = new Date().getTime();
    }

    public String getMessage() {
        return message;
    }

    public Long getTime() {
        return time;
    }

}
