package by.zhuk.buber.model;

import java.util.Objects;

public class DriverAchieve {
    private int fastAndFurious;
    private int narrator;
    private int clearCar;
    private int badDriver;

    public DriverAchieve() {

    }

    public DriverAchieve(int fastAndFurious, int narrator, int clearCar, int badDriver) {
        this.fastAndFurious = fastAndFurious;
        this.narrator = narrator;
        this.clearCar = clearCar;
        this.badDriver = badDriver;
    }

    public int getFastAndFurious() {
        return fastAndFurious;
    }

    public void setFastAndFurious(int fastAndFurious) {
        this.fastAndFurious = fastAndFurious;
    }

    public int getNarrator() {
        return narrator;
    }

    public void setNarrator(int narrator) {
        this.narrator = narrator;
    }

    public int getClearCar() {
        return clearCar;
    }

    public void setClearCar(int clearCar) {
        this.clearCar = clearCar;
    }

    public int getBadDriver() {
        return badDriver;
    }

    public void setBadDriver(int badDriver) {
        this.badDriver = badDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DriverAchieve that = (DriverAchieve) o;
        return fastAndFurious == that.fastAndFurious &&
                narrator == that.narrator &&
                clearCar == that.clearCar &&
                badDriver == that.badDriver;
    }

    @Override
    public int hashCode() {

        return Objects.hash(fastAndFurious, narrator, clearCar, badDriver);
    }

    @Override
    public String toString() {
        return "DriverAchieve{" +
                "fastAndFurious=" + fastAndFurious +
                ", narrator=" + narrator +
                ", clearCar=" + clearCar +
                ", badDriver=" + badDriver +
                '}';
    }
}