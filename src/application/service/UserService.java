package application.service;

public interface UserService {

    void wall();

    boolean createUser(String name);

    String postTweet(String messageToPost);

    String followUser(String username);

    boolean changeUser(String name);

    void login(String namePerson);

    boolean canChangeUser();

    void showUsers();
}
