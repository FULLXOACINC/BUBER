package by.zhuk.buber.command;

public enum PostCommandType {
    LANG(new LangChangeCommand()),
    SIGN_OUT(new SignOutCommand()),
    SWITCH_ADMIN_STATUS(new SwitchAdminStatusCommand()),
    SWITCH_BAN(new SwitchBanCommand());

    private Command command;

    PostCommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}