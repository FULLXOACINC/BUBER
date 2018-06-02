package by.zhuk.buber.command;

import java.util.Optional;

public class PostCommandFactory {

    public static Optional<Command> findCommand(String commandName) {
        try {
            PostCommandType type = PostCommandType.valueOf(commandName.toUpperCase());
            return Optional.of(type.getCommand());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }


}