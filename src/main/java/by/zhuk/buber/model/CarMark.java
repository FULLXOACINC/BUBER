package by.zhuk.buber.model;

import java.util.Objects;

public class CarMark {
    private int index;
    private String markName;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarMark carMark = (CarMark) o;
        return index == carMark.index &&
                Objects.equals(markName, carMark.markName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(index, markName);
    }

    @Override
    public String toString() {
        return "CarMark{" +
                "index=" + index +
                ", markName='" + markName + '\'' +
                '}';
    }
}