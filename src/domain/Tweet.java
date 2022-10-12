package domain;

import java.time.LocalDate;


public class Tweet {
    private String message;
    private LocalDate time;

    public Tweet(String message, LocalDate time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }
}
