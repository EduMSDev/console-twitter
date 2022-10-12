package service.factory;

import service.TwitterService;
import service.TwitterServiceImp;

public class TwitterServiceFactoryImp implements TwitterServiceFactory {

    @Override
    public TwitterService getTwitterService() {
        return new TwitterServiceImp();
    }
}
