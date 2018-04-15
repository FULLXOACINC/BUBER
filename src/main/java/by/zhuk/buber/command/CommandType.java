package by.zhuk.buber.command;

public enum CommandType {
    LANG(new LangChangeCommand()),
    OAUTH(new OAuthCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_UP_USER(new SignUpUserCommand()),
    SIGN_UP_ACCEPT(new SignUpAcceptCommand()),
    SIGN_OUT(new SignOutCommand()),
    FIND_USERS(new FindUsersCommand()),
    OAUTH_ACCEPT(new OAuthAcceptCommand()),
    SWITCH_ADMIN_STATUS(new SwitchAdminStatusCommand()),
    SIGN_UP_DRIVER(new SignUpDriverCommand()),
    SWITCH_BAN(new SwitchBanCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}