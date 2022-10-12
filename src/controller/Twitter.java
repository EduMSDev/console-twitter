package controller;

import service.TwitterService;
import service.factory.TwitterServiceFactory;

import java.util.Scanner;

public class Twitter {

    private final TwitterService twitterService;
    Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

    public Twitter(TwitterServiceFactory factory) {
        this.twitterService = factory.getTwitterService();
    }

    public void launchApp() {
        login();
        String options = showMenu();

        while (!options.equalsIgnoreCase("quit")) {
            switch (options) {
                case "posting":
                    posting();
                    break;
                case "reading":
//                    reading();
                    break;
                case "createUser":
                    createUser();
                    break;
                case "following":
                    following();
                    break;
                case "wall":
                    wall();
                    break;
                case "changeUser":
                    changeUser();
                    break;
                case "unfollow":
                    unfollowUser();
                    break;
                default:
                    System.out.println("Opcion desconocida.");
            }
            showMenu();
            options = scanner.next();
        }
    }

    private String showMenu() {
        System.out.println("Escoge una de las siguientes opciones:");
        System.out.println("posting\n reading\n createUser\n following\n wall\n changeUser\n quit ");
        return scanner.nextLine();
    }

    private void login() {
        System.out.println("Bienvenido a EduSocial, tu red social de confianza ;). Por favor,  escribe tu nombre: ");
        String namePerson = this.scanner.nextLine();
        twitterService.login(namePerson);
        System.out.printf("%s escribe al mundo lo que quieras %n", namePerson);
    }

    private void posting() {
        System.out.println("Escribe el mensaje que quieres postear en tu Wall:");
        String status = scanner.next();
        String messageFinal = twitterService.postTweet(status);
        System.out.println(messageFinal);
        System.out.println("Tweet posteado!");
    }

    private void reading() {

    }

    private void createUser() {
        System.out.println("Introduce el  nombre del usuario: ");
        String nameperson = scanner.next();
        boolean userAlreadyExists = twitterService.createUser(nameperson);
        String messageFinal = !userAlreadyExists ? "Usuario  %s incluido con exito %n" : "El usuario con nombre %s ya" +
                " existe %n";
        System.out.printf(messageFinal, nameperson);
    }

    private void following() {
        System.out.println("Escribe el nombre del usuario que quieres seguir: ");
        String username = scanner.next();
        String messageFinal = twitterService.followUser(username);
        System.out.print(messageFinal);
    }

    private void wall() {
        System.out.println("A continuacion se va a mostrar el mural de las personas que sigues y la tuya:");
        twitterService.wall();
        System.out.println("Fin del mural");
    }

    private void changeUser() {
        boolean canChangeUser = twitterService.canChangeUser();
        String messageFinal;
        if (canChangeUser) {
            System.out.println("Escribe el nombre del usuario: ");
            twitterService.showUsers();
            String name = scanner.next();
            boolean userExists = twitterService.changeUser(name);
            messageFinal = userExists ? "Bienvenido %s %n" : "El usuario %s no existe %n";
            System.out.printf(messageFinal, name);
        } else {
            System.out.println("Solo hay un usuario, es necesario mas de uno para el cambio");
        }
    }

    private void unfollowUser() {
        System.out.println("Escribe el nombre del usuario: ");
        String name = scanner.next();
        boolean isFriendRemoved = twitterService.unfollowUser(name);
        String messageFinal = isFriendRemoved ? "Has dejado de seguir a %s %n" : "%s no esta en tu lista de amigos %n";
        System.out.printf(messageFinal, name);
    }
}
