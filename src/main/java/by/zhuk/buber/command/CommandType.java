package by.zhuk.buber.command;

public enum CommandType {
    LANG(new LangChangeCommand()),
    OAUTH(new OAuthCommand()),
    SIGN_UP_ACCEPT(new SignUpAcceptCommand()),
    SIGN_OUT(new SignOutCommand()),
    FIND_DRIVER_UPDATE(new FindDriverUpdateCommand()),
    OAUTH_ACCEPT(new OAuthAcceptCommand()),
    SWITCH_ADMIN_STATUS(new SwitchAdminStatusCommand()),
    FIND_USER(new FindUserCommand()),
    FIND_USER_PROFILE(new FindUserProfileCommand()),
    FIND_DRIVER_PROFILE(new FindDriverProfileCommand()),
    FIND_USER_UPDATE(new FindUserUpdateCommand()),
    RESTORE_PASSWORD_ACCEPT(new RestorePasswordAcceptCommand()),
    SWITCH_BAN(new SwitchBanCommand());

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}