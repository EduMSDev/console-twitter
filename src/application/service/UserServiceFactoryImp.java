package application.service;

public class UserServiceFactoryImp implements UserServiceFactory {

    @Override
    public UserService getUserService() {
        return new UserServiceImp();
    }
}
