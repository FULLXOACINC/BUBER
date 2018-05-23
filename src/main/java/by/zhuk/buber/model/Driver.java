package by.zhuk.buber.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Driver {
    private String login;
    private String carNumber;
    private String documentId;
    private CarMark carMark;
    private float currentLatCoordinate;
    private float currentLngCoordinate;
    private boolean isWorking;
    private DriverAchieve achieve;
    private BigDecimal tariff;


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

    public DriverAchieve getAchieve() {
        return achieve;
    }

    public void setAchieve(DriverAchieve achieve) {
        this.achieve = achieve;
    }

    public BigDecimal getTariff() {
        return tariff;
    }

    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
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
        return Float.compare(driver.currentLatCoordinate, currentLatCoordinate) == 0 &&
                Float.compare(driver.currentLngCoordinate, currentLngCoordinate) == 0 &&
                isWorking == driver.isWorking &&
                Objects.equals(login, driver.login) &&
                Objects.equals(carNumber, driver.carNumber) &&
                Objects.equals(documentId, driver.documentId) &&
                Objects.equals(carMark, driver.carMark) &&
                Objects.equals(achieve, driver.achieve) &&
                Objects.equals(tariff, driver.tariff);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, carNumber, documentId, carMark, currentLatCoordinate, currentLngCoordinate, isWorking, achieve, tariff);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "login='" + login + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", documentId='" + documentId + '\'' +
                ", carMark=" + carMark +
                ", currentLatCoordinate=" + currentLatCoordinate +
                ", currentLngCoordinate=" + currentLngCoordinate +
                ", isWorking=" + isWorking +
                ", achieve=" + achieve +
                ", tariff=" + tariff +
                '}';
    }
}