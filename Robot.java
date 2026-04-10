public class Robot {
    private String robotId;
    private String robotName;
    private String contactStatus;

    public Robot(String robotId, String robotName) {
        this.robotId = robotId;
        this.robotName = robotName;
        this.contactStatus = "Safe";
    }

    public String getRobotId() {
        return robotId;
    }

    public String getRobotName() {
        return robotName;
    }

    public String getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }
}
