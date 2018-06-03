package by.zhuk.buber.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Ride {
    private int rideId;
    private Driver driver;
    private User passenger;
    private BigDecimal price;
    private LocalDate date;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ride ride = (Ride) o;
        return rideId == ride.rideId &&
                isDriverAcceptStart == ride.isDriverAcceptStart &&
                isPassengerAcceptStart == ride.isPassengerAcceptStart &&
                isDriverAcceptEnd == ride.isDriverAcceptEnd &&
                isPassengerAcceptEnd == ride.isPassengerAcceptEnd &&
                Objects.equals(driver, ride.driver) &&
                Objects.equals(passenger, ride.passenger) &&
                Objects.equals(price, ride.price) &&
                Objects.equals(date, ride.date) &&
                Objects.equals(startCoordinate, ride.startCoordinate) &&
                Objects.equals(endCoordinate, ride.endCoordinate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(rideId, driver, passenger, price, date, startCoordinate, endCoordinate, isDriverAcceptStart, isPassengerAcceptStart, isDriverAcceptEnd, isPassengerAcceptEnd);
    }

    @Override
    public String toString() {
        return "Ride{" +
                "rideId=" + rideId +
                ", driver=" + driver +
                ", passenger=" + passenger +
                ", price=" + price +
                ", date=" + date +
                ", startCoordinate=" + startCoordinate +
                ", endCoordinate=" + endCoordinate +
                ", isDriverAcceptStart=" + isDriverAcceptStart +
                ", isPassengerAcceptStart=" + isPassengerAcceptStart +
                ", isDriverAcceptEnd=" + isDriverAcceptEnd +
                ", isPassengerAcceptEnd=" + isPassengerAcceptEnd +
                '}';
    }
}