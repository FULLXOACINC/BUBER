package by.zhuk.buber.command;

import by.zhuk.buber.exeption.UnknownCommandException;

public class CommandFactory {

    public static Command createCommand(String commandName) throws UnknownCommandException {
        Command command;
        switch (commandName) {
            case "lang": {
                command = new LangChangeCommand();
                break;
            }
            case "oauth": {
                command = new OAuthCommand();
                break;
            }
            case "logOut": {
                command = new LogOutCommand();
                break;
            }
            default: {
                throw new UnknownCommandException(commandName);
            }


        }
        return command;
    }

}