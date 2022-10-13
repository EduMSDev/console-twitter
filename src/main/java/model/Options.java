package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Options {
    POST("post"),
    READ("read"),
    CREATE_USER("create"),
    FOLLOW("follow"),
    WALL("wall"),
    CHANGE_USER("change"),
    UNFOLLOW("unfollow"),
    UNKNOWN("unknown"),
    EXIT("exit");

    private final String nameOption;

    public static Options getOptions(String constant) {
        return Arrays.stream(Options.values()).filter(options -> options.getNameOption().equalsIgnoreCase(constant)).findFirst().orElse(UNKNOWN);
    }
}
