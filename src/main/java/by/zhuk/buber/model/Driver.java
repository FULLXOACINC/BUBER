package by.zhuk.buber.model;

import java.util.Objects;

public class Driver {
    private String login;
    private String carNumber;
    private String documentId;
    private CarMark carMark;
    private float currentLatCoordinate;
    private float currentLngCoordinate;
    private boolean isWorking;
    private DriverAchieve achive;

    public Driver() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public CarMark getCarMark() {
        return carMark;
    }

    public void setCarMark(CarMark carMark) {
        this.carMark = carMark;
    }

    public float getCurrentLatCoordinate() {
        return currentLatCoordinate;
    }

    public void setCurrentLatCoordinate(float currentLatCoordinate) {
        this.currentLatCoordinate = currentLatCoordinate;
    }

    public float getCurrentLngCoordinate() {
        return currentLngCoordinate;
    }

    public void setCurrentLngCoordinate(float currentLngCoordinate) {
        this.currentLngCoordinate = currentLngCoordinate;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        return isWorking == driver.isWorking &&
                Objects.equals(login, driver.login) &&
                Objects.equals(carNumber, driver.carNumber) &&
                Objects.equals(documentId, driver.documentId) &&
                Objects.equals(carMark, driver.carMark) &&
                Objects.equals(currentLatCoordinate, driver.currentLatCoordinate) &&
                Objects.equals(currentLngCoordinate, driver.currentLngCoordinate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, carNumber, documentId, carMark, currentLatCoordinate, currentLngCoordinate, isWorking);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "login='" + login + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", documentId='" + documentId + '\'' +
                ", carMark='" + carMark + '\'' +
                ", currentLatCoordinate='" + currentLatCoordinate + '\'' +
                ", currentLngCoordinate='" + currentLngCoordinate + '\'' +
                ", isWorking=" + isWorking +
                '}';
    }
}