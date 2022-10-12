package infraestructure;

import application.service.UserService;
import application.service.UserServiceFactory;

import java.util.Scanner;

public class Twitter {

    private final UserService userService;
    Scanner scanner = new Scanner(System.in);

    public Twitter(UserServiceFactory factory) {
        this.userService = factory.getUserService();
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
                default:
                    System.out.println("Sorry did you mean one of these commands?");
            }
            options = scanner.next();
        }
    }

    private String showMenu() {
        System.out.println("timeline,tweet ,find ,follow , , ,  quit");
        String options = scanner.nextLine();
        return options;
    }

    private void login() {
        System.out.println("Bienvenido a EduSocial, tu red social de confianza ;)");
        String namePerson = this.scanner.nextLine();
        userService.login(namePerson);
        System.out.printf("%s escribe al mundo lo que quieras", namePerson)
    }

    private void posting() {
        System.out.println("Escribe el mensaje que quieres postear en tu Wall:");
        String status = scanner.next();
        String messageFinal = userService.postTweet(status);
        System.out.println(messageFinal);
    }

    private void reading() {

    }

    private void createUser() {
        System.out.println("Introduce el  nombre del usuario: ");
        String nameperson = scanner.nextLine();
        boolean userCreated = userService.createUser(nameperson);
        String messageFinal = userCreated ? "Usuario  %s incluido con exito" : "El usuario %s no existe";
        System.out.printf(messageFinal, nameperson);
    }

    private void following() {
        System.out.println("Escribe el nombre del usuario que quieres seguir: ");
        String username = scanner.next();
        String messageFinal = userService.followUser(username);
        System.out.print(messageFinal);
    }

    private void wall() {
        System.out.println("A continuacion se va a mostrar el mural de las personas que sigues y la tuya:");
        userService.wall();
        System.out.println("Fin del mural");
    }

    private void changeUser() {
        boolean canChangeUser = userService.canChangeUser();
        if (canChangeUser) {
            System.out.print("Escribe el nombre del usuario: ");
            userService.showUsers();
            String name = scanner.next();
            userService.changeUser(name);
        } else {
            System.out.print("Solo hay un usuario");
        }
    }
}
