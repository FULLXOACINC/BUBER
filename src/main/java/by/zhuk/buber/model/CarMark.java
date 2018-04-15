package by.zhuk.buber.model;

public class CarMark {
    private int index;
    private String markName;

    public CarMark() {
    }

    public CarMark(int index, String markName) {
        this.index = index;
        this.markName = markName;
    }

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
}