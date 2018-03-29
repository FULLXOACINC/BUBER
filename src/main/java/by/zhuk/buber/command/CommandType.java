package by.zhuk.buber.command;

public enum CommandType {
    LANG(new LangChangeCommand()),
    OAUTH(new OAuthCommand()),
    SIGNIN(new SignInCommand()),
    SIGNOUT(new SignOutCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}