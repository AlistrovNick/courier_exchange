package by.epam.courierexchange.entity;

public enum UserStatusType {
    ACTIVE("active"),
    BLOCKED("blocked"),
    DELETED("deleted");

    private String status;

    UserStatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
