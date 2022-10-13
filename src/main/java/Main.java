import command.TwitterController;
import service.factory.TwitterServiceFactoryImp;

public class Main {

    public static void main(String[] args) {
        TwitterController twitterController = new TwitterController(new TwitterServiceFactoryImp());
        twitterController.launchApp();
    }
}
