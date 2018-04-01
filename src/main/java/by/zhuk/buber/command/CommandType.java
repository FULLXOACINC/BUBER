package by.zhuk.buber.command;

public enum CommandType {
    LANG(new LangChangeCommand()),
    OAUTH(new OAuthCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_UP_USER(new SignUpUserCommand()),
    SIGN_OUT(new SignOutCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}