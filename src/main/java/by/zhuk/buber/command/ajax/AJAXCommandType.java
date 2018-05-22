package by.zhuk.buber.command.ajax;

public enum AJAXCommandType {
    FIND_USERS(new FindUsersCommand()),
    SIGN_UP_USER(new SignUpUserCommand()),
    SIGN_IN(new SignInCommand()),
    ACCEPT_COMPLAINT(new AcceptComplaintCommand()),
    SIGN_UP_DRIVER(new SignUpDriverCommand()),
    FILL_UP_BALANCE(new FillUpBalanceCommand()),
    UPDATE_DRIVER(new UpdateDriverCommand()),
    GEO_DECODE_RAID_START_END(new GeoDecodeRaidStartEndCommand()),
    FIND_DISTANCE_INFO(new FindDistanceInfoCommand()),

    FIND_USER_COMPLAINTS(new FindUserComplaintsCommand()),
    CHANGE_DISCOUNT(new ChangeDiscountCommand());

    private AJAXCommand command;

    AJAXCommandType(AJAXCommand command) {
        this.command = command;
    }

    public AJAXCommand getCommand() {
        return command;
    }
}