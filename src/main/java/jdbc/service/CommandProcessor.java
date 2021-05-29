package jdbc.service;

import jdbc.util.CommandUtil;
import view.View;

public class CommandProcessor {
    CommandUtil commandUtil;
    View view;

    public CommandProcessor(CommandUtil commandUtil, View view) {
        this.commandUtil = commandUtil;
        this.view = view;
    }

    private static String GREETING_MESSAGE = "Wellcome to our console application";
    private static String CHOICE_MESSAGE = " Please type the letter of one of the command:\n"
            + "s - to get the list of salaries for concrete project\n"
            + "d - to get the list of developers for concrete project\n"
            + "j - to get the list of all java developers\n"
            + "m - to get the list of all middle developers\n"
            + "p - to get the list of all projects\n"
            + "q - to exit the application\n"
            + "l - to get the list of the command\n";


    public void process() {
        view.write(GREETING_MESSAGE);
        boolean isNotExit = true;
        boolean isChosen = false;
        while (isNotExit) {
            view.write(CHOICE_MESSAGE);
            String input = view.read();
            if (input.equalsIgnoreCase("q")) {
                view.write("You are leaving our application. Good Bye.");
                isNotExit = false;
                isChosen = true;
            }

            while (!isChosen) {
                isChosen = chooseCommand(input);
            }
            isChosen = false;
        }
    }

    private boolean chooseCommand(String message) {
        int id;
        switch (message.toLowerCase()) {
            case "s":
                id = getProjectIdFromUser();
                view.write("The hole sum of salaries per this project is " + commandUtil.getAllSalariesForProject(id) + "$");
                view.write("");
                return true;
            case "d":
                id = getProjectIdFromUser();
                view.write("The list of developers per this project:");
                commandUtil.getListOfDevelopersForProject(id).forEach(System.out::println);
                view.write("");
                return true;
            case "j":
                view.write("The list of all java developers: ");
                commandUtil.getListOfJavaDevelopers().forEach(System.out::println);
                view.write("");
                return true;
            case "m":
                view.write("The list of all middle developers");
                commandUtil.getListOfMiddleDevelopers().forEach(System.out::println);
                view.write("");
                return true;
            case "p":
                view.write("The list of all projects:");
                commandUtil.getShortDescriptionOfAllProjects().forEach(System.out::println);
                view.write("");
                return true;
            case"l":
                view.write(CHOICE_MESSAGE);
                return true;
            default:
                view.write("You entered the invalid command, please try again");
                return true;
        }
    }

    private int getProjectIdFromUser() {
        view.write("Please enter the id of project");
        return getIntegerFromConsole(view.read());
    }

    private int getIntegerFromConsole(String input) {
        String errorMessage = "It's not a correct number, please try again";
        int number = 0;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                number = Integer.parseInt(input);
                if (number <= 0) {
                    view.write(errorMessage);
                } else {
                    isFieldBlank = false;
                }
            } catch (Exception e) {
                view.write(errorMessage);
            }
        }
        return number;
    }
}
