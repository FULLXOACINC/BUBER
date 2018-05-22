package by.zhuk.buber.model;

import java.util.Objects;

public class Coordinate {
    private float lat;
    private float lng;

    public Coordinate() {
    }

    public Coordinate(float lat, float lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return Float.compare(that.lat, lat) == 0 &&
                Float.compare(that.lng, lng) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(lat, lng);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}