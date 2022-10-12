package model;

import java.util.Arrays;

public enum Options {
    POST("post"),
    READ("read"),
    CREATE_USER("create"),
    FOLLOW("follow"),
    WALL("wall"),
    CHANGE_USER("change"),
    UNFOLLOW("unfollow"),
    UNKNOW("unkown"),
    EXIT("exit");

    private final String nameOption;

    Options(String nameOption) {
        this.nameOption = nameOption;
    }

    public static Options getOptions(String constant) {
        return Arrays.stream(Options.values()).filter(options -> options.getNameOption().equalsIgnoreCase(constant)).findFirst().orElse(UNKNOW);
    }

    public String getNameOption() {
        return nameOption;
    }
}
