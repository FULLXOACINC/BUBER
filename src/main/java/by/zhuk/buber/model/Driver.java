package by.zhuk.buber.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Driver extends User {
    private String carNumber;
    private String documentId;
    private CarMark carMark;
    private float currentLatCoordinate;
    private float currentLngCoordinate;
    private boolean isWorking;
    private int positiveMark;
    private int negativeMark;
    private BigDecimal tariff;

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


    public BigDecimal getTariff() {
        return tariff;
    }

    public void setTariff(BigDecimal tariff) {
        this.tariff = tariff;
    }

    public int getPositiveMark() {
        return positiveMark;
    }

    public void setPositiveMark(int positiveMark) {
        this.positiveMark = positiveMark;
    }

    public int getNegativeMark() {
        return negativeMark;
    }

    public void setNegativeMark(int negativeMark) {
        this.negativeMark = negativeMark;
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
                positiveMark == driver.positiveMark &&
                negativeMark == driver.negativeMark &&
                Objects.equals(carNumber, driver.carNumber) &&
                Objects.equals(documentId, driver.documentId) &&
                Objects.equals(carMark, driver.carMark) &&
                Objects.equals(tariff, driver.tariff);
    }

    @Override
    public int hashCode() {

        return Objects.hash(carNumber, documentId, carMark, currentLatCoordinate, currentLngCoordinate, isWorking, positiveMark, negativeMark, tariff);
    }

    @Override
    public String toString() {
        return "Driver{" +
                ", carNumber='" + carNumber + '\'' +
                ", documentId='" + documentId + '\'' +
                ", carMark=" + carMark +
                ", currentLatCoordinate=" + currentLatCoordinate +
                ", currentLngCoordinate=" + currentLngCoordinate +
                ", isWorking=" + isWorking +
                ", positiveMark=" + positiveMark +
                ", negativeMark=" + negativeMark +
                ", tariff=" + tariff +
                '}';
    }
}