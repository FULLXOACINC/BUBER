package by.zhuk.buber.model;

import java.util.Objects;

public class Complaint {
    private int complaintId;
    private String complaintPersonLogin;
    private int raidId;
    private String complaintText;
    private boolean isAccept;

    public Complaint() {
    }

    public Complaint(int complaintId, String complaintPersonLogin, int raidId, String complaintText, boolean isAccept) {
        this.complaintId = complaintId;
        this.complaintPersonLogin = complaintPersonLogin;
        this.raidId = raidId;
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

    public int getRaidId() {
        return raidId;
    }

    public void setRaidId(int raidId) {
        this.raidId = raidId;
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
                raidId == complaint.raidId &&
                isAccept == complaint.isAccept &&
                Objects.equals(complaintPersonLogin, complaint.complaintPersonLogin) &&
                Objects.equals(complaintText, complaint.complaintText);
    }

    @Override
    public int hashCode() {

        return Objects.hash(complaintId, complaintPersonLogin, raidId, complaintText, isAccept);
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId=" + complaintId +
                ", complaintPersonLogin='" + complaintPersonLogin + '\'' +
                ", raidId=" + raidId +
                ", complaintText='" + complaintText + '\'' +
                ", isAccept=" + isAccept +
                '}';
    }
}