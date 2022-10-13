package command;


import lombok.extern.slf4j.Slf4j;
import model.Options;
import service.TwitterService;
import service.factory.TwitterServiceFactory;

import java.util.Arrays;
import java.util.Scanner;

@Slf4j
public class TwitterController {

    private final TwitterService twitterService;
    Scanner scanner = new Scanner(System.in);

    public TwitterController(TwitterServiceFactory factory) {
        this.twitterService = factory.getTwitterService();
    }

    public void launchApp() {
        login();
        Options options = showMenu();

        while (options != Options.EXIT) {
            switch (options) {
                case POST:
                    posting();
                    break;
                case READ:
//                    reading();
                    break;
                case CREATE_USER:
                    createUser();
                    break;
                case FOLLOW:
                    following();
                    break;
                case WALL:
                    wall();
                    break;
                case CHANGE_USER:
                    changeUser();
                    break;
                case UNFOLLOW:
                    unfollowUser();
                    break;
                case EXIT:
                    return;
                default:
                    System.out.println("Opcion desconocida.");
            }
            options = showMenu();
        }
    }

    private Options showMenu() {
        log.info("Escoge una de las siguientes opciones:");
        Arrays.stream(Options.values()).forEach(option -> System.out.println(option.getNameOption()));
        String option = scanner.nextLine();
        return Options.getOptions(option);
    }

    private void login() {
        log.info("Bienvenido a EduSocial, tu red social de confianza ;). Por favor,  escribe tu nombre: ");
        String namePerson = this.scanner.nextLine();
        twitterService.login(namePerson);
        log.info("{} escribe al mundo lo que quieras %n", namePerson);
    }

    private void posting() {
        log.info("Escribe el mensaje que quieres postear en tu Wall:");
        String status = scanner.nextLine();
        String messageFinal = twitterService.postTweet(status);
        log.info(messageFinal);
        log.info("Tweet posteado!");

    }

    private void reading() {

    }

    private void createUser() {

    }

    private void following() {
        log.info("Escribe el nombre del usuario que quieres seguir: ");
        String username = scanner.next();
        String messageFinal = twitterService.followUser(username);
        log.info(messageFinal);
    }

    private void wall() {
        log.info("A continuacion se va a mostrar el mural de las personas que sigues y la tuya:");
        boolean isEmpty = twitterService.wall();
        String messageFinal = isEmpty ? "No se ha posteado ningun tweet. Nada que mostrar" : "Fin del muro";
        log.info(messageFinal);
    }

    private void changeUser() {
        boolean canChangeUser = twitterService.canChangeUser();
        String messageFinal;
        if (canChangeUser) {
            log.info("Escribe el nombre del usuario: ");
            twitterService.showUsers();
            String name = scanner.next();
            boolean userExists = twitterService.changeUser(name);
            messageFinal = userExists ? "Bienvenido {} %n" : "El usuario {} no existe %n";
            log.info(messageFinal, name);
        } else {
            log.error("Solo hay un usuario, es necesario mas de uno para el cambio");
        }
    }

    private void unfollowUser() {
        log.info("Escribe el nombre del usuario: ");
        String name = scanner.next();
        boolean isFriendRemoved = twitterService.unfollowUser(name);
        String messageFinal = isFriendRemoved ? "Has dejado de seguir a %s %n" : "%s no esta en tu lista de amigos %n";
        log.info(String.format(messageFinal, name));
    }
}
