package by.zhuk.buber.model;

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

}