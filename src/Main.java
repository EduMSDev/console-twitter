import application.service.UserServiceFactoryImp;
import infraestructure.Twitter;

public class Main {

    public static void main(String[] args) {
        Twitter twitter = new Twitter(new UserServiceFactoryImp());
        twitter.launchApp();
    }
}
