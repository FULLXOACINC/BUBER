package by.zhuk.buber.command.ajax;

public enum AJAXCommandType {
    FIND_USERS(new FindUsersCommand()),
    SIGN_UP_USER(new SignUpUserCommand()),
    SIGN_IN(new SignInCommand()),
    ACCEPT_COMPLAINT(new AcceptComplaintCommand()),
    SIGN_UP_DRIVER(new SignUpDriverCommand()),
    FILL_UP_BALANCE(new FillUpBalanceCommand()),
    UPDATE_DRIVER(new UpdateDriverCommand()),
    GEO_DECODE_RIDE_START_END(new GeoDecodeRideStartEndCommand()),
    FIND_DISTANCE_INFO(new FindDistanceInfoCommand()),
    FIND_SUITABLE_DRIVERS(new FindSuitableDriversCommand()),
    GEO_DECODE_ADDRESS(new GeoDecodeAddressCommand()),
    UPDATE_CURRENT_DRIVER_COORDINATE(new UpdateCurrentDriverCoordinateCommand()),
    FIND_USER_COMPLAINTS(new FindUserComplaintsCommand()),
    CREATE_RIDE(new CreateRideCommand()),
    FIND_RIDE_INFO_PASSENGER(new FindRideInfoPassengerCommand()),
    CHANGE_DISCOUNT(new ChangeDiscountCommand());

    private AJAXCommand command;

    AJAXCommandType(AJAXCommand command) {
        this.command = command;
    }

    public AJAXCommand getCommand() {
        return command;
    }
}