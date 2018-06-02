package by.zhuk.buber.command;

import java.util.Optional;

public class GetCommandFactory {


    public static Optional<Command> findCommand(String commandName) {
        try {
            GetCommandType type = GetCommandType.valueOf(commandName.toUpperCase());
            return Optional.of(type.getCommand());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }


}