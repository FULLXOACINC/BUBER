package by.zhuk.buber.command.ajax;

public enum AJAXCommandType {
    FIND_USERS(new FindUsersCommand()),
    SIGN_UP_USER(new SignUpUserCommand()),
    SIGN_IN(new SignInCommand()),
    SIGN_UP_DRIVER(new SignUpDriverCommand()),
    FILL_UP_BALANCE(new FillUpBalanceCommand()),
    UPDATE_DRIVER(new UpdateDriverCommand()),
    CHANGE_DISCOUNT(new ChangeDiscountCommand());

    private AJAXCommand command;

    AJAXCommandType(AJAXCommand command) {
        this.command = command;
    }

    public AJAXCommand getCommand() {
        return command;
    }
}