package by.zhuk.buber.command.ajax;

import java.util.Optional;

public class AJAXCommandFactory {


    public static Optional<AJAXCommand> findCommand(String commandName) {
        try {
            AJAXCommandType type = AJAXCommandType.valueOf(commandName.toUpperCase());
            return Optional.of(type.getCommand());
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }


}