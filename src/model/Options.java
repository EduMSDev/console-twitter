package model;

public enum Options {
    POST("post"),
    READ("read"),
    CREATE_USER("create"),
    FOLLOW("follow"),
    WALL("wall"),
    CHANGE_USER("change"),
    UNFOLLOW("unfollow"),
    EXIT("exit");

    private final String nameOption;

    Options(String nameOption) {
        this.nameOption = nameOption;
    }

    public static Options getOptions(String constant) {
        for (Options option : Options.values()) {
            if (option.getNameOption().equalsIgnoreCase(constant)) {
                return option;
            }
        }
        return null;
    }

    public String getNameOption() {
        return nameOption;
    }
}
