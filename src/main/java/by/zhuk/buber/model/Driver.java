package by.zhuk.buber.model;

import java.math.BigDecimal;

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
    private BigDecimal earnedMoney;

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

    public BigDecimal getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(BigDecimal earnedMoney) {
        this.earnedMoney = earnedMoney;
    }
}