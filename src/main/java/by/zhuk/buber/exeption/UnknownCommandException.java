package by.zhuk.buber.exeption;

public class UnknownCommandException extends Exception {
    public UnknownCommandException(String commandName) {
        super(commandName);
    }
}