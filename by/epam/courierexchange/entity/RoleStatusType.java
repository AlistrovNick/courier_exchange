package by.epam.courierexchange.entity;

public enum RoleStatusType {
    ACTIVE("active"),
    BLOCKED("blocked"),
    DELETED("deleted");

    private String status;

    RoleStatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
