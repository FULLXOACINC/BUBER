package by.zhuk.buber.command;

public enum CommandType {
    LANG(new LangChangeCommand()),
    OAUTH(new OAuthCommand()),
    SIGN_UP_ACCEPT(new SignUpAcceptCommand()),
    SIGN_OUT(new SignOutCommand()),
    VIEW_UNACCEPTED_COMPLAINTS(new ViewUnacceptedComplaintsCommand()),
    FIND_DRIVER(new FindDriverCommand()),
    OAUTH_ACCEPT(new OAuthAcceptCommand()),
    SWITCH_ADMIN_STATUS(new SwitchAdminStatusCommand()),
    VIEW_USER(new ViewUserCommand()),
    SWITCH_BAN(new SwitchBanCommand());


    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}