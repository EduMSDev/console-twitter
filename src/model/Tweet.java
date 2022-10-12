package model;

import java.time.LocalDateTime;


public class Tweet {
    private String message;
    private LocalDateTime time;

    public Tweet(String message) {
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }

}
