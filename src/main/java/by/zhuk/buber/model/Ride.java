package by.zhuk.buber.model;

public class Ride {
    private int rideId;
    private Driver driver;
    private User passenger;
    private Coordinate startCoordinate;
    private Coordinate endCoordinate;
    private boolean isDriverAcceptStart;
    private boolean isPassengerAcceptStart;
    private boolean isDriverAcceptEnd;
    private boolean isPassengerAcceptEnd;

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public Driver getDriver() {
        return driver;
    }

    public Coordinate getStartCoordinate() {
        return startCoordinate;
    }

    public Coordinate getEndCoordinate() {
        return endCoordinate;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public void setStartCoordinate(Coordinate startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public void setEndCoordinate(Coordinate endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    public boolean isDriverAcceptStart() {
        return isDriverAcceptStart;
    }

    public void setDriverAcceptStart(boolean driverAcceptStart) {
        isDriverAcceptStart = driverAcceptStart;
    }

    public boolean isPassengerAcceptStart() {
        return isPassengerAcceptStart;
    }

    public void setPassengerAcceptStart(boolean passengerAcceptStart) {
        isPassengerAcceptStart = passengerAcceptStart;
    }

    public boolean isDriverAcceptEnd() {
        return isDriverAcceptEnd;
    }

    public void setDriverAcceptEnd(boolean driverAcceptEnd) {
        isDriverAcceptEnd = driverAcceptEnd;
    }

    public boolean isPassengerAcceptEnd() {
        return isPassengerAcceptEnd;
    }

    public void setPassengerAcceptEnd(boolean passengerAcceptEnd) {
        isPassengerAcceptEnd = passengerAcceptEnd;
    }
}