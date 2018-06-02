package by.zhuk.buber.command;

public enum GetCommandType {
    OAUTH(new OAuthCommand()),
    SIGN_UP_ACCEPT(new SignUpAcceptCommand()),
    FIND_DRIVER_UPDATE(new FindDriverUpdateCommand()),
    OAUTH_ACCEPT(new OAuthAcceptCommand()),
    FIND_USER(new FindUserCommand()),
    FIND_USER_PROFILE(new FindUserProfileCommand()),
    FIND_DRIVER_PROFILE(new FindDriverProfileCommand()),
    FIND_USER_UPDATE(new FindUserUpdateCommand()),
    RESTORE_PASSWORD_ACCEPT(new RestorePasswordAcceptCommand());

    private Command command;

    GetCommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}