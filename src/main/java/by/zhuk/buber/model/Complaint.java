package by.zhuk.buber.model;

import java.util.Objects;

public class Complaint {
    private int complaintId;
    private String complaintPersonLogin;
    private int rideId;
    private String complaintText;
    private boolean isAccept;

    public Complaint() {
    }

    public Complaint(int complaintId, String complaintPersonLogin, int rideId, String complaintText, boolean isAccept) {
        this.complaintId = complaintId;
        this.complaintPersonLogin = complaintPersonLogin;
        this.rideId = rideId;
        this.complaintText = complaintText;
        this.isAccept = isAccept;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public String getComplaintPersonLogin() {
        return complaintPersonLogin;
    }

    public void setComplaintPersonLogin(String complaintPersonLogin) {
        this.complaintPersonLogin = complaintPersonLogin;
    }

    public int getRideId() {
        return rideId;
    }

    public void setRideId(int rideId) {
        this.rideId = rideId;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Complaint complaint = (Complaint) o;
        return complaintId == complaint.complaintId &&
                rideId == complaint.rideId &&
                isAccept == complaint.isAccept &&
                Objects.equals(complaintPersonLogin, complaint.complaintPersonLogin) &&
                Objects.equals(complaintText, complaint.complaintText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(complaintId, complaintPersonLogin, rideId, complaintText, isAccept);
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId=" + complaintId +
                ", complaintPersonLogin='" + complaintPersonLogin + '\'' +
                ", rideId=" + rideId +
                ", complaintText='" + complaintText + '\'' +
                ", isAccept=" + isAccept +
                '}';
    }
}