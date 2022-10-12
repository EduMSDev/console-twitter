import service.factory.TwitterServiceFactoryImp;
import controller.Twitter;

public class Main {

    public static void main(String[] args) {
        Twitter twitter = new Twitter(new TwitterServiceFactoryImp());
        twitter.launchApp();
    }
}
