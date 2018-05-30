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
    FIND_USER_COMPLAINT(new FindUserComplaintCommand()),
    CREATE_RIDE(new CreateRideCommand()),
    ACCEPT_START_RIDE_PASSENGER(new AcceptStartRidePassengerCommand()),
    FIND_RIDE_INFO_PASSENGER(new FindRideInfoPassengerCommand()),
    FIND_RIDE_INFO_DRIVER(new FindRideInfoDriverCommand()),
    ACCEPT_END_RIDE_PASSENGER(new AcceptEndRidePassengerCommand()),
    ACCEPT_START_RIDE_DRIVER(new AcceptStartRideDriverCommand()),
    ACCEPT_END_RIDE_DRIVER(new AcceptEndRideDriverCommand()),
    EVALUATION_DRIVER(new EvaluationDriverCommand()),
    COMPLAINT_PASSENGER(new ComplaintPassengerCommand()),
    COMPLAINT_DRIVER(new ComplaintDriverCommand()),
    REFUSE_RIDE_PASSENGER(new RefuseRidePassengerCommand()),
    REFUSE_RIDE_DRIVER(new RefuseRideDiverCommand()),
    DRIVER_SET_NOT_WORKING_STATUS(new DriverSetNotWorkingStatusCommand()),
    DRIVER_SET_WORKING_STATUS(new DriverSetWorkingStatusCommand()),
    FIND_UNACCEPTED_COMPLAINT(new FindUnacceptedComplaintCommand()),
    CHANGE_DISCOUNT(new ChangeDiscountCommand());


    private AJAXCommand command;

    AJAXCommandType(AJAXCommand command) {
        this.command = command;
    }

    public AJAXCommand getCommand() {
        return command;
    }
}