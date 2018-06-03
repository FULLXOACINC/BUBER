package by.zhuk.buber.exception;

/**
 * Class Exception created on business logic level
 */
public class ReceiverException extends Exception {
    public ReceiverException() {
    }

    public ReceiverException(String s) {
        super(s);
    }

    public ReceiverException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ReceiverException(Throwable throwable) {
        super(throwable);
    }
}