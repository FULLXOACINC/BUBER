package by.zhuk.buber.model;

import java.util.Objects;

public class DistanceInfo {
    private int distance;
    private String duration;

    public DistanceInfo() {
    }


    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DistanceInfo that = (DistanceInfo) o;
        return distance == that.distance &&
                Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {

        return Objects.hash(distance, duration);
    }

    @Override
    public String toString() {
        return "DistanceInfo{" +
                "distance=" + distance +
                ", duration='" + duration + '\'' +
                '}';
    }
}