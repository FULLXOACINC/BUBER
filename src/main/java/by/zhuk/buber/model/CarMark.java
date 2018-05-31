package by.zhuk.buber.model;

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
    public String toString() {
        return "CarMark{" +
                "index=" + index +
                ", markName='" + markName + '\'' +
                '}';
    }
}